package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.model.Role;
import com.commit.collaboration_board_server.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public String createRole(@RequestBody Role role) {
        roleService.addRole(role);
        return "Role added successfully!";
    }
}
