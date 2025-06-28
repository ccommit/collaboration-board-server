package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.mapper.RoleMapper;
import com.commit.collaboration_board_server.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;

    public void insertRole(Role role) {
        roleMapper.insertRole(role);
    }

    public Role getRoleById(Long id) {
        return roleMapper.getRoleById(id);
    }
    public void updateRole(Role role) {
        roleMapper.updateRole(role);
    }
    public void deleteRoleById(Long id) {
        roleMapper.deleteRoleById(id);
    }


}