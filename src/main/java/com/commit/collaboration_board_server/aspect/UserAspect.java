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

import java.lang.reflect.Method;

@Component
@Aspect
public class UserAspect {

    private static final Logger logger = LoggerFactory.getLogger(UserAspect.class);

    @Pointcut("@annotation(com.commit.collaboration_board_server.aspect.CheckLoginStatus)")
    public void checkLoginMethods() {}

    @Before("checkLoginMethods()")
    public <HttpSession> void checkLoginStatus(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }


        // @CheckLoginStatus 어노테이션 정보 가져오기
        Method method = getMethodFromJoinPoint(joinPoint);
        CheckLoginStatus annotation = method.getAnnotation(CheckLoginStatus.class);

        HttpServletRequest request = attributes.getRequest();
        HttpSession session = (HttpSession) request.getSession(false);

        User loggedInUser = SessionUtil.getLoggedInUser((jakarta.servlet.http.HttpSession) session);

        if (loggedInUser == null) {
            logger.warn("Unauthorized access detected.");
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        logger.info("User '{}' is authenticated.", loggedInUser.getUserId());
    }

    // JoinPoint를 사용하여 현재 메서드 정보를 가져오는 방법
    private Method getMethodFromJoinPoint(JoinPoint joinPoint) {
        // 실행 중인 메서드의 이름과 파라미터 타입으로 Method 객체를 추출
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = joinPoint.getTarget().getClass();

        // 메서드 이름과 파라미터 타입을 통해 Method 객체 얻기
        try {
            Class<?>[] parameterTypes = getParameterTypes(joinPoint);
            return targetClass.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("메서드 정보를 가져오는 데 실패했습니다.", e);
        }
    }

    // JoinPoint에서 파라미터 타입을 추출하는 메서드
    private Class<?>[] getParameterTypes(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Class<?>[] parameterTypes = new Class<?>[args.length];

        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
        }

        return parameterTypes;
    }
}