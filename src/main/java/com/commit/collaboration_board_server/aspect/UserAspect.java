package com.commit.collaboration_board_server.aspect;

import com.commit.collaboration_board_server.model.User;
import com.commit.collaboration_board_server.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
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

    @Pointcut("@annotation(checkLoginStatus)")
    public void checkLoginMethods(CheckLoginStatus checkLoginStatus) {
        // This method is empty because it only serves as a pointcut definition.
        // The @Pointcut method should only define the pointcut expression and not contain any logic or parameters other than those required for the pointcut.
    }



    public void checkAdminPrivileges(User loggedInUser) {
        if (loggedInUser.getIsAdmin() == null || loggedInUser.getIsAdmin() != 1) {
            logger.warn("User '{}' is not an admin. Access denied.", loggedInUser.getUserId());
            throw new IllegalStateException("어드민만 접근 가능합니다.");
        }
    }

    @Before("checkLoginMethods(checkLoginStatus)")
    public void checkLoginStatus(JoinPoint joinPoint, CheckLoginStatus checkLoginStatus) {
        // HTTP 요청 정보 가져오기
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        HttpServletRequest request = attributes.getRequest();
        jakarta.servlet.http.HttpSession session = request.getSession(false);

        // 로그인한 사용자 정보 가져오기
        User loggedInUser = SessionUtil.getLoggedInUser(session);
        if (loggedInUser == null) {
            logger.warn("Unauthorized access detected.");
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // 어노테이션의 userType 확인
        UserType requiredUserType = checkLoginStatus.userType();

        // ADMIN 권한이 필요한 경우
        if (requiredUserType == UserType.ADMIN) {
            if (loggedInUser.getIsAdmin() == null || loggedInUser.getIsAdmin() != 1) {
                logger.warn("User '{}' is not an admin. Access denied.", loggedInUser.getUserId());
                throw new IllegalStateException("어드민만 접근 가능합니다.");
            }
        }
        // 어드민 여부 체크
        checkAdminPrivileges(loggedInUser);
    }
}