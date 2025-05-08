package com.commit.collaboration_board_server.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Vacation {
    private int id;  // 휴가 ID (PK)
    private int userNo;  // 신청자 (user 테이블의 PK)
    private LocalDateTime startTime;  // 휴가 시작일
    private LocalDateTime endTime;    // 휴가 종료일
    private int vacationCategoryId;   // 휴가 유형 ID (vacationcategory 테이블 참조)
    private String status;  // 휴가 상태: PENDING / APPROVED / REJECTED
}