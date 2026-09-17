package smu.blog.post;

public record PostUpdateRequest(
        Long postId,
        String title,
        String content
) {
}
