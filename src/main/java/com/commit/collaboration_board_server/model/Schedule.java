package com.commit.collaboration_board_server.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Schedule {
    private Long id;
    private Long userNo; // DB와 맞추기 위해 String -> Long으로 변경
    private List<String> invitedEmails;
    private String title;
    private Boolean isRegular;
    private String regularType;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}