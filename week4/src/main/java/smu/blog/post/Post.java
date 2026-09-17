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

    public void changeTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
