package com.estsoft.blogjpa.service;

import com.estsoft.blogjpa.domain.Article;
import com.estsoft.blogjpa.domain.Comment;
import com.estsoft.blogjpa.dto.CommentRequest;
import com.estsoft.blogjpa.dto.CommentResponse;
import com.estsoft.blogjpa.repository.BlogRepository;
import com.estsoft.blogjpa.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, BlogRepository blogRepository) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
    }

    public CommentResponse getComment(Long articleId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        // 댓글이 특정 게시글에 속해 있는 경우에만 articleId를 설정
        Long articleIdOfComment = null;
        if (comment.getArticle() != null) {
            articleIdOfComment = comment.getArticle().getId();
        }

        return CommentResponse.builder()
                .id(comment.getId())
                .articleId(articleIdOfComment)
                .body(comment.getBody())
                .createdAt(comment.getCreatedAt())
                .build();
    }




    public CommentResponse createComment(Long articleId, CommentRequest request) {
        Article article = blogRepository.findById(articleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found with id: " + articleId));

        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setBody(request.getBody());
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.builder()
                .id(savedComment.getId())
                .articleId(savedComment.getArticle().getId()) // articleId 설정
                .body(savedComment.getBody())
                .createdAt(savedComment.getCreatedAt())
                .build();
    }
}
