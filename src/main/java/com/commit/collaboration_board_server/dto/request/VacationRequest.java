package com.commit.collaboration_board_server.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VacationRequest {
    private int userNo;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int vacationCategoryId;
}
