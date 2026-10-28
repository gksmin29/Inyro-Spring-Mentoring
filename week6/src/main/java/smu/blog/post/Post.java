package smu.blog.post;

import jakarta.persistence.*;
import smu.blog.comment.Comment;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 추후에 Lombok을 사용해 축약할 수 있습니다.
    public Post() {}

    // 추후에 Lombok을 사용해 축약할 수 있습니다.
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 추후에 Lombok을 사용해 축약할 수 있습니다.
    public Long getPostId() {
        return postId;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public List<Comment> getComments() { return comments; }

    public void changeTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
