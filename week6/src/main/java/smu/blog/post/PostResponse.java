package smu.blog.post;

import smu.blog.comment.Comment;

import java.util.List;

public record PostResponse(
        Post post,
        List<Comment> comments
) {
}
