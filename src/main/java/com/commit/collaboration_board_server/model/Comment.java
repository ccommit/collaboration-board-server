package com.commit.collaboration_board_server.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Comment {
    private Integer id;
    private String content;
    private LocalDateTime createdDate;
    private String writer;
    private Integer commentId; // 부모 댓글 ID
    private Integer articleId;

    private List<Comment> replies = new ArrayList<>(); // 대댓글 목록
}