package com.commit.collaboration_board_server.model;

import lombok.Data;


@Data
public class User {
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