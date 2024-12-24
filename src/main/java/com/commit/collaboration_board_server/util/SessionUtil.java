package com.commit.collaboration_board_server.util;

import com.commit.collaboration_board_server.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class SessionUtil {

    public static void saveLoggedInUser(HttpSession  session , User user) {
        session.setAttribute("loggedInUser", user);
    }

    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public static User getLoggedInUser(HttpSession session) {
        return (session != null) ? (User) session.getAttribute("loggedInUser") : null;
    }
}