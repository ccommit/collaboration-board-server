package com.commit.collaboration_board_server.util;

import com.commit.collaboration_board_server.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class SessionUtil {

    // 세션에 사용자 저장
    public static void saveLoggedInUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("loggedInUser", user);
    }

    // 세션에서 사용자 제거
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    // 세션에서 사용자 조회
    public static User getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (User) session.getAttribute("loggedInUser") : null;
    }
}