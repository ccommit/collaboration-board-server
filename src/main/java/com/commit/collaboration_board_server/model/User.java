package com.commit.collaboration_board_server.model;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    private Long id;
    private String userId;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private String role;

    //private List<Role> roles;
    //private List<Article> articles;
    //private List<Calendar> calendars;
    // Getters and Setters
}