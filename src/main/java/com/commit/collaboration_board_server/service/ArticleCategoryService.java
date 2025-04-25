package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.mapper.ArticleCategoryMapper;
import com.commit.collaboration_board_server.model.ArticleCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleCategoryService {
    private final ArticleCategoryMapper categoryMapper;

    public void createCategory(ArticleCategory category) {
        categoryMapper.insertArticleCategory(category);
    }
}
