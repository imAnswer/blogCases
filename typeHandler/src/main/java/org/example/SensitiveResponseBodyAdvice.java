package org.example;

import com.github.houbb.sensitive.core.api.SensitiveUtil;
import org.example.entity.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 脱敏工具类
 * @author liushaoya
 * @since 2025-10-15 9:10
 */
@RestControllerAdvice
public class SensitiveResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //判断当前方法的返回类型是否是 Result 本身，或 Result 的子类 / 实现类。
        //A.class.isAssignableFrom(B.class) 表示 B 是 A 的同类/子类/实现（B → A 可赋值）
        //注意：由于深拷贝的特性，直接处理String类型的数据会报类型转换异常：java.lang.reflect.InaccessibleObjectException: Unable to make field private final byte[] java.lang.String.value accessible: module java.base does not "opens java.lang" to unnamed module @35cabb2a] with root cause
        //因为代码或某个三方库在通过反射访问/修改 String 的私有字段 value，但 Jigsaw 模块系统默认不允许对 JDK 核心包（java.lang）做这种“深反射”
        //因此加上一个包装类Result，这样就不会报错了
        return Result.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if (body != null && body instanceof Result) {
            if(((Result) body).getData() == null) {
                return body;
            }
            ((Result) body).setData(SensitiveUtil.desCopy(((Result) body).getData()));
            return body;
        }

        return body;
    }
}
