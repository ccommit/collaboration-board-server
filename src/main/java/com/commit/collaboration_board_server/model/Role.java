package com.commit.collaboration_board_server.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role {
    private Long id;
    private String roleName;
    private Integer addVacationCount;
}