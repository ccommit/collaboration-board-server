package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.dto.request.UserCreateRequest;
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
    public List<User> getUsers(Long userNo) {
        return userMapper.findUsers(userNo);
    }


    @Override
    public void createUser(UserCreateRequest user) {
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
    public boolean authenticate(User loginRequest) {
        User user = userMapper.findByUserId(loginRequest.getUserNo());
        return user != null && user.getPassword().equals(loginRequest.getPassword());
    }

    @Override
    public void saveUserSession(HttpSession session, User loginRequest) {
        User userFromDB = userMapper.findByUserId(loginRequest.getUserNo());
        if (userFromDB == null) {
            throw new IllegalStateException("해당 사용자 정보를 찾을 수 없습니다.");
        }
        SessionUtil.saveLoggedInUser(session, userFromDB);
    }

    @Override
    public void removeUserSession(HttpServletRequest request) {
        SessionUtil.invalidateSession(request);
    }
}
