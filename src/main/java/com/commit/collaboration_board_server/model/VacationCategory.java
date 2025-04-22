package com.commit.collaboration_board_server.model;

import lombok.Data;

@Data
public class VacationCategory {
    private int id;     // 카테고리 ID (PK)
    private String name; // 카테고리 이름 (ex: 연차, 반차, 병가 등)
}