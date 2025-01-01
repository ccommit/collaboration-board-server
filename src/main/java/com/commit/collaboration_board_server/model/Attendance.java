package com.commit.collaboration_board_server.model;

import java.util.Date;
import lombok.Data;

@Data
public class Attendance {
    private Long id;
    private String userId;
    private String workDate;
    private String workStartTime;
    private String workEndTime;
    private String workTime;
}