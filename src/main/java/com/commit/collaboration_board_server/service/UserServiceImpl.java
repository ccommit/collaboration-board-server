package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.mapper.UserMapper;
import com.commit.collaboration_board_server.model.User;
import com.commit.collaboration_board_server.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public void createUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }


    @Override
    public User findByUserId(String userId) {
        return userMapper.findByUserId(userId);
    }

    @Override
    public boolean authenticate(User loginRequest) {
        User user = userMapper.findByUserId(loginRequest.getUserId());
        return user != null && user.getPassword().equals(loginRequest.getPassword());
    }

    @Override
    public void saveUserSession(HttpSession session, User loginRequest) {
        SessionUtil.saveLoggedInUser(session, loginRequest);
    }

    @Override
    public void removeUserSession(HttpServletRequest request) {
        SessionUtil.invalidateSession(request);
    }
}
