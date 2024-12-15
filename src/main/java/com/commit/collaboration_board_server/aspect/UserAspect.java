package com.commit.collaboration_board_server.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserAspect {

    private static final Logger logger = LoggerFactory.getLogger(UserAspect.class);

    // 1. 로그인 메서드에 대한 Pointcut 정의
    @Pointcut("execution(* com.commit.collaboration_board_server.controller.UserController.login(..))")
    public void loginPointcut() {}

    // 2. 로그아웃 메서드에 대한 Pointcut 정의
    @Pointcut("execution(* com.commit.collaboration_board_server.controller.UserController.logout(..))")
    public void logoutPointcut() {}

    // 3. 로그인 성공 후 로직
    @AfterReturning(value = "loginPointcut()", returning = "response")
    public void afterLoginSuccess(Object response) {
        logger.info("Login successful: {}", response);
    }

    // 4. 로그인 실패 시 로직 (예외 발생 시)
    @AfterThrowing(value = "loginPointcut()", throwing = "ex")
    public void afterLoginFailure(Exception ex) {
        logger.error("Login failed: {}", ex.getMessage());
    }

    // 5. 로그아웃 후 로직
    @After("logoutPointcut()")
    public void afterLogout() {
        logger.info("User logged out successfully.");
    }
}