package com.commit.collaboration_board_server.dto.request;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
public class VacationRequest {
    private int userNo;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int vacationCategoryId;
}
