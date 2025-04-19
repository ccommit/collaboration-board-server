package com.commit.collaboration_board_server.model;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

@Data
public class Attendance {
    private Long id;
    private String userId;
    private LocalDateTime startTime = LocalDateTime.now();
    private LocalDateTime endTime;
    private Double workHour;
    private Double workNotHour;


    public LocalDateTime getWorkStartTime() {
        return this.startTime;
    }
}