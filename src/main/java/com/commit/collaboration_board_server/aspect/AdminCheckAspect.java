package com.commit.collaboration_board_server.aspect;

import com.commit.collaboration_board_server.model.User;
import com.commit.collaboration_board_server.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
public class AdminCheckAspect {

    private static final Logger logger = LoggerFactory.getLogger(AdminCheckAspect.class);

    // @AdminOnly 애너테이션이 붙은 메서드에 적용될 Pointcut
    @Pointcut("@annotation(com.commit.collaboration_board_server.aspect.AdminOnly)")
    public void adminOnlyMethods() {}

    @Before("adminOnlyMethods()")
    public void checkAdminStatus() {
        // 요청 정보 가져오기
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            logger.warn("No request attributes available.");
            throw new IllegalStateException("요청 정보가 없습니다. 로그인이 필요합니다.");
        }

        // HttpServletRequest에서 세션 가져오기
//        HttpServletRequest request = attributes.getRequest();
//        jakarta.servlet.http.HttpSession session = request.getSession(false);


        HttpServletRequest request = attributes.getRequest();
        HttpSession session = (HttpSession) request.getSession(false);
        // 세션이 없으면 예외 발생
        if (session == null) {
            logger.warn("No active session found.");
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // 로그인한 사용자 정보 가져오기
        User loggedInUser = SessionUtil.getLoggedInUser((jakarta.servlet.http.HttpSession) session);
        // 로그인한 사용자가 없으면 예외 발생
        if (loggedInUser == null) {
            logger.warn("Unauthorized access detected - No logged-in user.");
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        // 어드민 여부 체크 (Integer 타입으로 변경된 isAdmin 확인)
        if (loggedInUser.getIsAdmin() == null || loggedInUser.getIsAdmin() != 1) {
            logger.warn("User '{}' is not an admin. Access denied.", loggedInUser.getUserId());
            throw new IllegalStateException("어드민만 접근 가능합니다.");
        }

        logger.info("Admin user '{}' is authenticated and authorized.", loggedInUser.getUserId());
    }
}
