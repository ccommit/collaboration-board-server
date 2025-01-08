package com.commit.collaboration_board_server.aspect;

import com.commit.collaboration_board_server.model.User;
import com.commit.collaboration_board_server.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class UserAspect {

    private static final Logger logger = LoggerFactory.getLogger(UserAspect.class);

    @Pointcut("@annotation(com.commit.collaboration_board_server.aspect.CheckLoginStatus)")
    public void checkLoginMethods() {}

    @Before("checkLoginMethods()")
    public <HttpSession> void checkLoginStatus() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        HttpServletRequest request = attributes.getRequest();
        HttpSession session = (HttpSession) request.getSession(false);

        User loggedInUser = SessionUtil.getLoggedInUser((jakarta.servlet.http.HttpSession) session);

        if (loggedInUser == null) {
            logger.warn("Unauthorized access detected.");
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        logger.info("User '{}' is authenticated.", loggedInUser.getUserId());
    }
}