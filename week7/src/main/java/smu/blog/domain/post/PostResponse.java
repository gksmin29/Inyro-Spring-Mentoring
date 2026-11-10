package smu.blog.domain.post;

import smu.blog.domain.comment.Comment;

import java.util.List;

public record PostResponse(
        Long postId,
        String title,
        String content,
        List<String> comments
) {

    public static PostResponse from (Post post, List<Comment> comments) {
        return new PostResponse(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                comments.stream().map(Comment::getContent).toList()
        );
    }
}
