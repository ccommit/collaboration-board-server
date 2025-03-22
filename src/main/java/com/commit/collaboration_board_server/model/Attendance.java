package com.commit.collaboration_board_server.model;

import java.util.Date;
import lombok.Data;

@Data
public class Attendance {
    private Long id;
    private String userId;
    private String startTime;
    private String endTime;
    private String workHour;
    private String workNotHour;


    public String getWorkStartTime() {
        return this.startTime;
    }
}