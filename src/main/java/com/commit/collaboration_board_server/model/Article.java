package com.commit.collaboration_board_server.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Article {
    private Integer id;
    private LocalDateTime createTime;
    private String title;
    private String content;
    private Integer categoryId;
    private String attachmentUrl;
    private Integer viewCount;
    private LocalDateTime modificateTime;
    private Integer likeCount;
    private Integer userId;
}