package com.commit.collaboration_board_server.controller;

import com.commit.collaboration_board_server.model.ArticleCategory;
import com.commit.collaboration_board_server.service.ArticleCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article-categories")
@RequiredArgsConstructor
public class ArticleCategoryController {

    private final ArticleCategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody ArticleCategory category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok().build();
    }
}