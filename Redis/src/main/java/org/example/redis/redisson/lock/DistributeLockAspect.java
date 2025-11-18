package org.example.redis.redisson.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁切面
 * @author liushaoya
 * @since 2025-10-06 18:26
 */
@Aspect
@Order(Integer.MIN_VALUE + 1)
public class DistributeLockAspect {

    @Autowired
    private RedissonClient redissonClient;

    private static final Logger LOG = LoggerFactory.getLogger(DistributeLockAspect.class);

    @Around("@annotation(org.example.redis.redisson.lock.DistributeLock)")
    public Object process(ProceedingJoinPoint pjp) throws Exception{
        Object response = null;
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        //获取方法上的注解
        DistributeLock distributeLock = method.getAnnotation(DistributeLock.class);

        //获取注解上加锁的key
        String key = distributeLock.key();

        if (DistributeLockConstant.NONE_KEY.equals(key)) {
            if (DistributeLockConstant.NONE_KEY.equals(distributeLock.keyExpression())) {
                throw new DistributeLockException("no lock key found...");
            }
            /**
             * 解析spel表达式
             */
            //设置解析器
            SpelExpressionParser parser = new SpelExpressionParser();
            //可执行的表达式对象
            Expression expression = parser.parseExpression(distributeLock.keyExpression());
            //解析上下文环境
            EvaluationContext context = new StandardEvaluationContext();
            // 获取参数值
            Object[] args = pjp.getArgs();

            //获取参数名称
            StandardReflectionParameterNameDiscoverer discoverer
                    = new StandardReflectionParameterNameDiscoverer();
            String[] paramNames = discoverer.getParameterNames(method);

            //将参数绑定到context中
            if(paramNames != null) {
                for(int i = 0; i < paramNames.length; i++) {
                    context.setVariable(paramNames[i], args[i]);
                }
            }

            //解析表达式，获取结果
            key = String.valueOf(expression.getValue(context));

        }

        String scene = distributeLock.scene();
        String lockKey = scene + "#" + key;

        int expireTime = distributeLock.expireTime();
        int waitTime = distributeLock.waitTime();
        RLock rLock = redissonClient.getLock(lockKey);
        try {
            boolean lockResult = false;
            if (waitTime == DistributeLockConstant.DEFAULT_WAIT_TIME) {
                if (expireTime == DistributeLockConstant.DEFAULT_EXPIRE_TIME) {
                    LOG.info(String.format("lock for key : %s", lockKey));
                    rLock.lock();
                }else {
                    LOG.info(String.format("lock for key : %s , expire : %s", lockKey, expireTime));
                    rLock.lock(expireTime, TimeUnit.MILLISECONDS);
                }
                lockResult = true;
            }else {
                if (expireTime == DistributeLockConstant.DEFAULT_EXPIRE_TIME) {
                    LOG.info(String.format("try lock for key : %s , wait : %s", lockKey, waitTime));
                    lockResult = rLock.tryLock(waitTime, TimeUnit.MILLISECONDS);
                }else {
                    LOG.info(String.format("try lock for key : %s , expire : %s , wait : %s", lockKey, expireTime, waitTime));
                    lockResult = rLock.tryLock(waitTime, expireTime, TimeUnit.MILLISECONDS);
                }
            }
            if(!lockResult) {
                LOG.warn(String.format("lock failed for key : %s , expire : %s", lockKey, expireTime));
                throw new DistributeLockException("acquire lock failed... key : " + lockKey);
            }

            LOG.info(String.format("lock success for key : %s , expire : %s", lockKey, expireTime));
            response = pjp.proceed();
        }catch (Throwable e) {
            throw new Exception(e);
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                rLock.unlock();
                LOG.info(String.format("unlock for key : %s , expire : %s", lockKey, expireTime));
            }
        }

        return response;
    }


}
