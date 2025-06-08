package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.dto.request.RoleUpsertRequest;
import com.commit.collaboration_board_server.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleMapper {
    void insertRole(RoleUpsertRequest role);

    void updateRole(@Param("id") Long id, @Param("role") RoleUpsertRequest role);

    void deleteRoleById(Long id);

    Role getRoleById(Long id);
}