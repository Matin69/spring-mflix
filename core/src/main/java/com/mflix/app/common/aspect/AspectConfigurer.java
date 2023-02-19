package com.mflix.app.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectConfigurer {

    private final ApisResponseConverter apisResponseConverter;

    public AspectConfigurer(ApisResponseConverter apisResponseConverter) {
        this.apisResponseConverter = apisResponseConverter;
    }

    @Around(value = "@within(org.springframework.web.bind.annotation.RestController)")
    public ResponseEntity<?> afterReturningResourcesAdvice(ProceedingJoinPoint point) throws Throwable {
        ResponseEntity<?> responseEntity = (ResponseEntity<?>) point.proceed(point.getArgs());
        return apisResponseConverter.convert(responseEntity);
    }
}
