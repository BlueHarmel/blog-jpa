package com.estsoft.blogjpa.repository;

import com.estsoft.blogjpa.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndArticleId(Long commentId, Long articleId);
}

