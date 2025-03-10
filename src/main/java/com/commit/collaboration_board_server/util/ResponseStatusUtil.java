package com.commit.collaboration_board_server.util;

import org.springframework.http.HttpStatus;
import java.util.Collections;
import java.util.Map;

public final class ResponseStatusUtil {

    public static final Map<String, Integer> STATUS_CODES;

    static {
        Map<String, Integer> map = Map.of(
                "SUCCESS", HttpStatus.OK.value(), // 200 OK - 요청이 성공적으로 처리됨
                "CREATED", HttpStatus.CREATED.value(), // 201 Created - 리소스가 성공적으로 생성됨
                "BAD_REQUEST", HttpStatus.BAD_REQUEST.value(), // 400 Bad Request - 잘못된 요청
                "UNAUTHORIZED", HttpStatus.UNAUTHORIZED.value(), // 401 Unauthorized - 인증 실패
                "FORBIDDEN", HttpStatus.FORBIDDEN.value(), // 403 Forbidden - 권한이 없는 요청
                "NOT_FOUND", HttpStatus.NOT_FOUND.value(), // 404 Not Found - 리소스를 찾을 수 없음
                "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500 Internal Server Error - 서버 오류
                "SERVICE_UNAVAILABLE", HttpStatus.SERVICE_UNAVAILABLE.value() // 503 Service Unavailable - 서비스 사용 불가
        );
        STATUS_CODES = Collections.unmodifiableMap(map); // 불변 Map으로 설정
    }

    private ResponseStatusUtil() {
    }

    public static Integer getStatus(String key) {
        return STATUS_CODES.get(key);
    }
}
