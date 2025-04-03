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

    @Transactional
    public void addRole(Role role) {
        roleMapper.insertRole(role);
    }
}