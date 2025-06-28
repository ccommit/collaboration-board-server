package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.dto.request.UserCreateRequest;
import com.commit.collaboration_board_server.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findUsers(Long userNo);

    void insertUser(UserCreateRequest user);

    void updateUser(User user);

    void deleteUser(@Param("id") Long id);

    User findByUserId(Long userNo);
}