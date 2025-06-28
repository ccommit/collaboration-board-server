package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ArticleMapper {
    void insertArticle(Article article);

    Article selectArticleById(@Param("id") int id);

    List<Article> selectAllArticles();

    void updateArticle(Article article);

    void deleteArticle(@Param("id") int id);

    void insertArticleCategory(Article article);
}