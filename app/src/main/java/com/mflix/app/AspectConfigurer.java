package com.mflix.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mflix.app.common.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
    public void afterReturningResourcesPointcut() {
    }

    @Pointcut("target(com.mflix.app.common.RestCrudController) && execution(* save*(..))")
    public void afterSavingPointCut() {

    }

    @Around(value = "afterReturningResourcesPointcut()")
    public ResponseEntity<?> afterReturningResourcesAdvice(ProceedingJoinPoint point) throws Throwable {
        ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) point.proceed(point.getArgs());
        RestResponse<Object> restResponse = (RestResponse<Object>) responseEntity.getBody();
        Object body = restResponse.source;
        List<Object> entities = new ArrayList<>();
        if (body instanceof List) {
            entities = (List<Object>) body;
        } else {
            entities.add(body);
        }
        Converter converter = converterRegistry.getConverter(entities.get(0));
        List<Object> convertedEntities = entities.stream()
                .map(converter::convert)
                .collect(Collectors.toList());
        restResponse.source = convertedEntities;
        return ResponseEntity.status(responseEntity.getStatusCode())
                .headers(responseEntity.getHeaders())
                .body(restResponse);
    }

    @AfterReturning(pointcut = "afterSavingPointCut()", returning = "savedObject")
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
}
