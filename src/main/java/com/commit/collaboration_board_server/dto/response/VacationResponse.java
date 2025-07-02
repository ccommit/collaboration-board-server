package com.commit.collaboration_board_server.dto.response;

import com.commit.collaboration_board_server.service.VacationStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Builder
@Data
public class VacationResponse {
    private int id;
    private int userNo;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int vacationCategoryId;
    private VacationStatus status;
}
