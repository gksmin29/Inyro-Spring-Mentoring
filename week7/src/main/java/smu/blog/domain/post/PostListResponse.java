package smu.blog.domain.post;

import java.util.List;

public record PostListResponse(
        Long postId,
        String title,
        String content
) {
    static List<PostListResponse> from(List<Post> posts) {
        return posts.stream()
                .map(post -> new PostListResponse(
                        post.getPostId(),
                        post.getTitle(),
                        post.getContent()
                ))
                .toList();
    }
}
