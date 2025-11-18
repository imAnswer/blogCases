package org.example.spring.AOPAnnotation.Annotation;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.spring.AOPAnnotation.utils.BeanValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.lang.reflect.Method;

/**
 * @author liushaoya
 * @since 2025-10-04 14:25
 */
@Aspect
@Component
@Order(Integer.MIN_VALUE)
@Slf4j
public class FacadeAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(FacadeAspect.class);

    @Around("@annotation(org.example.spring.AOPAnnotation.Annotation.Facade)")
    public Object facade(ProceedingJoinPoint pjp) throws Exception {

        Method method = ((MethodSignature)pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();

        Class returnType = ((MethodSignature) pjp.getSignature()).getMethod().getReturnType();

        //循环遍历所有参数，进行参数校验
        for (Object parameter : args) {
            try {
                BeanValidator.validateObject(parameter);
            } catch (ValidationException e) {

                log.error("参数校验失败：{}", e.getMessage());
            }
        }

        try {
            // 目标方法执行
            Object response = pjp.proceed();
            return response;
        } catch (Throwable throwable) {
            // 如果执行异常，则返回一个失败的response
            return "错误2";
        }

    }

}
