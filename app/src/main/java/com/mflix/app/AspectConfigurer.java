package com.mflix.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mflix.app.common.*;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Aspect
@Component
public class AspectConfigurer {

    private final ConverterRegistry converterRegistry;

    private final RabbitTemplate rabbitTemplate;

    public AspectConfigurer(ConverterRegistry converterRegistry, RabbitTemplate rabbitTemplate) {
        this.converterRegistry = converterRegistry;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Pointcut("target(com.mflix.app.common.RestCrudController)")
    public void restControllerMethodsPointCut() {
    }

    @Pointcut("target(com.mflix.app.common.RestCrudController) && execution(* save*(..))")
    public void restControllerSaveMethodPointCut() {

    }

    @Around(value = "restControllerMethodsPointCut()")
    public ResponseEntity<?> afterReturningResourcesAdvice(ProceedingJoinPoint point) throws Throwable {
        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) point.proceed(point.getArgs());
        Object processedResource = responseEntity.getBody();
        List<Object> resourcesCollection = new ArrayList<>();
        if (processedResource instanceof List) {
            resourcesCollection = (List<Object>) processedResource;
        } else {
            resourcesCollection.add(processedResource);
        }
        Converter converter = converterRegistry.getConverter(resourcesCollection.get(0));
        List<Object> convertedEntities = resourcesCollection.stream()
                .map(converter::convert)
                .collect(Collectors.toList());
        HttpServletRequest currenRequest = getCurrentHttpRequest().orElseThrow(RuntimeException::new);
        return ResponseEntity
                .status(responseEntity.getStatusCode())
                .body(
                        new RestResponse<>(
                                true,
                                currenRequest.getMethod(),
                                convertedEntities
                        )
                );
    }

    @AfterReturning(pointcut = "restControllerSaveMethodPointCut()", returning = "savedObject")
    public void afterSavingAdvice(Object savedObject) throws JsonProcessingException {
        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) savedObject;
        RestResponse<Object> restResponse = (RestResponse<Object>) responseEntity.getBody();
        AbstractEntity entity = (AbstractEntity) restResponse.source;
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(entity);
        String collectionName = entity.getCollectionName();
        SyncObject syncObject = new SyncObject(collectionName, json);
        rabbitTemplate.convertAndSend("elastic-sync-exchange", "elastic-sync", syncObject);
    }

    public static Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest);
    }
}
