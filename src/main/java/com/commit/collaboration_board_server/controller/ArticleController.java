package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.model.Article;
import com.commit.collaboration_board_server.model.Comment;
import com.commit.collaboration_board_server.service.ArticleService;
import com.commit.collaboration_board_server.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody Article article) {
        articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable int id) {
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable int id, @RequestBody Article article) {
        article.setId(id);
        articleService.updateArticle(article);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable int id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/article-categories")
    public ResponseEntity<?> createCategory(@RequestBody Article article) {
        articleService.createCategory(article);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        commentService.createComment(comment);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{articleId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByArticle(@PathVariable int articleId) {
        List<Comment> comments = commentService.getNestedCommentsByArticleId(articleId);
        return ResponseEntity.ok(comments);
    }

}
