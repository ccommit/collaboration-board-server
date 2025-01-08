package com.commit.collaboration_board_server.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "monthly_work_date")
public class MonthlyWorkDate {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String userId;
    private int totalWorkTime;
}
