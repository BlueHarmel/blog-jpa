package com.estsoft.blogjpa.domain;

import com.estsoft.blogjpa.dto.CommentResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(name = "body", nullable = false)
    private String body;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public Comment(Article article, String body) {
        this.article = article;
        this.body = body;
    }

    public CommentResponse toResponse() {
        return CommentResponse.builder()
                .articleId(article.getId())
                .body(body)
                .createdAt(createdAt)
                .build();
    }

    // Setter 추가
    public void setArticle(Article article) {
        this.article = article;
    }
}
