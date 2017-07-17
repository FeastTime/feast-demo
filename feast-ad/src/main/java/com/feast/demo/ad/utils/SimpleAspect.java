package com.feast.demo.ad.utils;

import org.aspectj.lang.JoinPoint;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ggke on 2017/7/15.
 */
public class SimpleAspect {

    /**
     * 前置通知
     * @param joinPoint
     */
    public void beforMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("ValidationAspect this method "+methodName+" begin. param<"+ args+">");
    }

    /**
     * 后置通知（无论方法是否发生异常都会执行,所以访问不到方法的返回值）
     * @param joinPoint
     */
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("ValidationAspect this method "+methodName+" end.");
    }
}
