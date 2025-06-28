package com.commit.collaboration_board_server.service;

import com.commit.collaboration_board_server.mapper.CommentMapper;
import com.commit.collaboration_board_server.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;

    public void createComment(Comment comment) {
        commentMapper.insertComment(comment);
    }

    public List<Comment> getNestedCommentsByArticleId(int articleId) {
        List<Comment> comments = commentMapper.selectCommentsByArticleId(articleId);

//        // Map<id, comment> 구성
//        Map<Integer, Comment> commentMap = new HashMap<>();
//        List<Comment> rootComments = new ArrayList<>();
//
//        for (Comment comment : comments) {
//            commentMap.put(comment.getId(), comment);
//        }
//
//        for (Comment comment : comments) {
//            if (comment.getCommentId() == null) {
//                rootComments.add(comment);
//            } else {
//                Comment parent = commentMap.get(comment.getCommentId());
//                if (parent != null) {
//                    parent.getReplies().add(comment);
//                }
//            }
//        }

        return comments;
    }
}