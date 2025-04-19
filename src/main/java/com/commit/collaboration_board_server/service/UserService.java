package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserNo(Long id);
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
    User findByUserId(Long userNo);
    boolean authenticate(User loginRequest);
    void saveUserSession(HttpSession request, User loginRequest);
    void removeUserSession(HttpServletRequest request);
}