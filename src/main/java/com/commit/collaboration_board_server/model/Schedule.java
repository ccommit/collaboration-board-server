package com.commit.collaboration_board_server.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Schedule {
    private Long id;
    private String userId;
    private String invitedEmails;
    private String title;
    private String scheduleCategory;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}