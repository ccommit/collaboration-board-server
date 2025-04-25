package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.model.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleCategoryMapper {
    void insertArticleCategory(ArticleCategory category);
}
