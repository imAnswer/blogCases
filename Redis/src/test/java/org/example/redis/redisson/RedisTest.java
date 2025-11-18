package org.example.redis.redisson;

import org.aspectj.lang.reflect.MethodSignature;
import org.example.redis.redisson.lock.DistributeLock;
import org.example.redis.redisson.lock.DistributeLockConstant;
import org.example.redis.redisson.lock.DistributeLockException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author liushaoya
 * @since 2025-10-06 20:07
 */
@SpringBootTest
public class RedisTest {

    @Test
    public void standardReflectionParameterNameDiscovererTest() throws NoSuchMethodException{

        Method method = ParameterTest.class.getDeclaredMethod("mockData", String.class, String.class);

        //设置解析器
        SpelExpressionParser parser = new SpelExpressionParser();
        //可执行的表达式对象
        Expression expression = parser.parseExpression("#name");
        //解析上下文环境
        EvaluationContext context = new StandardEvaluationContext();
        // 获取参数值
        Object[] args = {"zhangsan", "1"};

        StandardReflectionParameterNameDiscoverer discoverer = new StandardReflectionParameterNameDiscoverer();
        String[] parameterName = discoverer.getParameterNames(method);
        for(int i = 0; i < parameterName.length; i++) {
            context.setVariable(parameterName[i], args[i]);
        }


        System.out.println(expression.getValue(context));
    }
}
