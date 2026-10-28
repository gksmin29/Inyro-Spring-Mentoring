package smu.blog.comment;

public record CommentCreateRequest(
        Long postId,
        String content
) {
}
