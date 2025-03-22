package com.commit.collaboration_board_server.model;

import lombok.Data;

import java.util.Map;


@Data
public class User {
    private Long id;
    private String userId;
    private String userName;
    private String password;
    private String phoneNumber;
    private String email;
    private String roleId;
    private boolean isAdmin;
    private Integer vacationCount;
    private Map<String, Object> additionalInfo;
}