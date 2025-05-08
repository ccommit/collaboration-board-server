package com.commit.collaboration_board_server.model;

import lombok.Data;

@Data
public class MonthlyWorkDate {
    private Long id;
    private Long userNo;
    private Double totalWorkTime;
}
