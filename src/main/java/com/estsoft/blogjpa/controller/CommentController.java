package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.dto.CommentRequest;
import com.estsoft.blogjpa.dto.CommentResponse;
import com.estsoft.blogjpa.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long articleId, @RequestBody CommentRequest request) {
        CommentResponse createdComment = commentService.createComment(articleId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @GetMapping("/{articleId}/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long articleId, @PathVariable Long commentId) {
        CommentResponse comment = commentService.getComment(articleId, commentId);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

