package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.dto.request.RoleUpsertRequest;
import com.commit.collaboration_board_server.mapper.RoleMapper;
import com.commit.collaboration_board_server.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;

    public void insertRole(RoleUpsertRequest role) {
        roleMapper.insertRole(role);
    }

    public Role getRoleById(Long id) {
        return roleMapper.getRoleById(id);
    }
    public void updateRole(Long id, RoleUpsertRequest role) {
        roleMapper.updateRole(id, role);
    }
    public void deleteRoleById(Long id) {
        roleMapper.deleteRoleById(id);
    }


}