package br.com.samueltorga.spring.controller.dto.post;

import java.time.Instant;
import java.util.Set;

public record FindPostResponse(
        Integer id,
        String title,
        String content,
        Integer userId,
        Instant created,
        Instant updated,
        Set<PostComment> postComments
) {
    public record PostComment(
            Integer id,
            String content,
            Integer userId,
            Integer parentCommentId,
            Instant created,
            Instant updated,
            Set<PostComment> children
    ) {}
}