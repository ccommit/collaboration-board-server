package com.commit.collaboration_board_server.dto.request;
import lombok.Data;

@Data
public class RoleUpsertRequest {
    private String roleName;
    private Integer addVacationCount;
}
