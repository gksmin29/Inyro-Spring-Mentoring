package smu.blog.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import smu.blog.domain.post.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
