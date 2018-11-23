package com.tc.website.common.db.aspect;

import com.tc.website.common.db.DataSourceHolder;
import com.tc.website.common.db.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * 切面拦截
 */
@Aspect
@Component
public class DataSourceAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 扫描需要切换数据源的service包
     */
    @Pointcut("execution(* com.tc.website.modules.*.service.*.*(..))")
    private void anyMethod(){}

    /**
     * 切换数据源
     * @param point
     * @throws Exception
     */
    @Before("anyMethod()")
    public void before(JoinPoint point) throws Exception {
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
    }

    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                DataSourceHolder.setDataSource(source.value());
                logger.info("before 切换数据源 = " + clazz.getName() + " , DataSource = " + source.value());
            }
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource source = m.getAnnotation(DataSource.class);
                DataSourceHolder.setDataSource(source.value());
                logger.info("before 切换数据源 = " + clazz.getName() + " , DataSource = " + source.value());
            }
        } catch (Exception e) {
            System.out.println(clazz + ":" + e.getMessage());
        }
    }

    /**
     * 恢复数据源
     * @param point
     */
    @After("anyMethod()")
    public void after(JoinPoint point) {
         DataSourceHolder.resetDataSource();
         logger.info("after 还原数据源");
    }
}
