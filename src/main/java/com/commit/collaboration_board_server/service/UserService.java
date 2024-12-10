package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
    User findByUserId(String userId);
}