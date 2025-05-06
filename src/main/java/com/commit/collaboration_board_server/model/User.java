package com.commit.collaboration_board_server.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    private Integer isAdmin;
    private Integer vacationCount;
    private Map<String, Object> additionalInfo;
    public Integer getIsAdmin() {
        return isAdmin != null ? isAdmin : 0;
    }

}
