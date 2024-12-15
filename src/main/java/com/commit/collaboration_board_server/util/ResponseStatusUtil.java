package com.commit.collaboration_board_server.util;

import org.springframework.http.HttpStatus;

public final class ResponseStatusUtil {

    // 200 OK - 요청이 성공적으로 처리됨
    public static final int SUCCESS = HttpStatus.OK.value();

    // 201 Created - 리소스가 성공적으로 생성됨
    public static final int CREATED = HttpStatus.CREATED.value();

    // 400 Bad Request - 잘못된 요청
    public static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();

    // 401 Unauthorized - 인증 실패
    public static final int UNAUTHORIZED = HttpStatus.UNAUTHORIZED.value();

    // 403 Forbidden - 권한이 없는 요청
    public static final int FORBIDDEN = HttpStatus.FORBIDDEN.value();

    // 404 Not Found - 리소스를 찾을 수 없음
    public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();

    // 500 Internal Server Error - 서버 오류
    public static final int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

    // 503 Service Unavailable - 서비스 사용 불가
    public static final int SERVICE_UNAVAILABLE = HttpStatus.SERVICE_UNAVAILABLE.value();

}