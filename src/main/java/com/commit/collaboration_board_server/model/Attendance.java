package com.commit.collaboration_board_server.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Attendance {
    private Long id;
    private Long userNo;
    private LocalDateTime startTime = LocalDateTime.now();
    private LocalDateTime endTime;
    private Double workHour;
    private Double workNotHour;


    public LocalDateTime getWorkStartTime() {
        return this.startTime;
    }
}