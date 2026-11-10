package smu.blog.domain.comment;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import smu.blog.domain.post.Post;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="comment_id")
    private Long commentId;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    // 추후에 Lombok을 사용해 축약할 수 있습니다.
    public Comment() {
    }

    // 추후에 Lombok을 사용해 축약할 수 있습니다.
    public Comment(Post post, String content) {
        this.content = content;
        this.post = post;
    }

    // 추후에 Lombok을 사용해 축약할 수 있습니다.
    public String getContent() {
        return content;
    }
    public Post getPost() {
        return post;
    }


}
