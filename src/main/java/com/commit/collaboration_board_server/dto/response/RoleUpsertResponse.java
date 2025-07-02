package com.commit.collaboration_board_server.dto.response;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RoleUpsertResponse {
    private String roleName;
    private Integer addVacationCount;
}
