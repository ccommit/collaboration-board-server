package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.model.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface RoleMapper {
    @Insert("INSERT INTO role (id,roleName, addVacationCount) VALUES (#{id},#{roleName}, #{addVacationCount})")
    void insertRole(Role role);
}