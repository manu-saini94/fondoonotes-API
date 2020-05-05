//package com.bridgelabz.fundoonotes.aspects;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//import lombok.extern.slf4j.Slf4j;
//
//
//@Aspect
//@Component
//@Slf4j
//public class Logger {
//	
//	
//	@Before("execution(* com.bridgelabz.fundoonotes.serviceimpl.UserServiceImpl.login(*))")
//	public void beforeloginMethod(JoinPoint joinPoint) throws Throwable
//	{
//
//		log.info("Before login");
//	    log.info("method invoked: "+joinPoint.getSignature().getName()+" Logged-in user:"+joinPoint.getArgs()[0]);
//	}
//	
//	
//	@AfterReturning("execution(* com.bridgelabz.fundoonotes.serviceimpl.UserServiceImpl.login(*))")
//	public void afterLoginMethod()
//	{
//		log.info("Login Executed");
//	}
//	
//	
//	@Around("execution(* com.bridgelabz.fundoonotes.*.*.*(*)) && !execution(* com.bridgelabz.fundoonotes.serviceimpl.UserServiceImpl.login(*))")
//	public Object forAllMethods(ProceedingJoinPoint joinPoint) throws Throwable
//	{
//		log.info("Before "+joinPoint.getSignature().getName()+"method of "+joinPoint.getSignature().getDeclaringType().getSimpleName());
//		Object val=joinPoint.proceed();
//		log.info("After "+joinPoint.getSignature().getName()+" method of class"+joinPoint.getSignature().getDeclaringType().getSimpleName());
//		return val;
//			
//	}
//	
//	
//}
//
//
//
