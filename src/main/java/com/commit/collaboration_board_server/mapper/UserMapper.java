package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
    User findById(@Param("id") Long id);
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(@Param("id") Long id);
    User findByUserId(String userId);
}