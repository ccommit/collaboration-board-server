package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.model.Comment;
import com.commit.collaboration_board_server.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        commentService.createComment(comment);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<Comment>> getCommentsByArticle(@PathVariable int articleId) {
        List<Comment> comments = commentService.getNestedCommentsByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }
}
