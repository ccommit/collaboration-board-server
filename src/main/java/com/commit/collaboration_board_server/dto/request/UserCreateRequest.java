package com.commit.collaboration_board_server.dto.request;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String userId;
    private String userName;
    private String password;
    private Integer isAdmin;
}
