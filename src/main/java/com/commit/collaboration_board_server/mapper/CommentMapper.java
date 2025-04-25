package com.commit.collaboration_board_server.mapper;

import com.commit.collaboration_board_server.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    void insertComment(Comment comment);

    List<Comment> selectCommentsByArticleId(@Param("articleId") int articleId);
}