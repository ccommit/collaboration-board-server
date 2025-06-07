package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.dto.request.UserCreateRequest;
import com.commit.collaboration_board_server.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserService {
    List<User> getUsers(Long userNo);
    void createUser(UserCreateRequest user);
    void updateUser(User user);
    void deleteUser(Long id);
    boolean authenticate(User loginRequest);
    void saveUserSession(HttpSession request, User loginRequest);
    void removeUserSession(HttpServletRequest request);
}