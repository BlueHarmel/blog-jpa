package com.estsoft.blogjpa.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private Long id;
    private Long articleId; // articleId 추가
    private String body;
    private LocalDateTime createdAt;
}
