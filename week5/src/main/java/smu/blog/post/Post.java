package smu.blog.post;

import jakarta.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    public Post() {}

    public Post(String title, String content) {
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

    public void changeTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
