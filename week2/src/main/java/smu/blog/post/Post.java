package smu.blog.post;

public class Post {

    private Long postId;
    private String title;
    private String content;

    public Post(Long postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
