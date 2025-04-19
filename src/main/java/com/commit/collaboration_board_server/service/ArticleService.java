package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.mapper.ArticleMapper;
import com.commit.collaboration_board_server.model.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleMapper articleMapper;

    public void createArticle(Article article) {
        article.setViewCount(0);
        article.setLikeCount(0);
        article.setModificateTime(LocalDateTime.now());
        articleMapper.insertArticle(article);
    }

    public Article getArticle(int id) {
        return articleMapper.selectArticleById(id);
    }

    public List<Article> getAllArticles() {
        return articleMapper.selectAllArticles();
    }

    public void updateArticle(Article article) {
        article.setModificateTime(LocalDateTime.now());
        articleMapper.updateArticle(article);
    }

    public void deleteArticle(int id) {
        articleMapper.deleteArticle(id);
    }
}