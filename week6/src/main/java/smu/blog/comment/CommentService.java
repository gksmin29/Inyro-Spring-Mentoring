package smu.blog.comment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.blog.post.Post;
import smu.blog.post.PostRepository;

import java.util.Optional;

@Service
@Transactional
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 추후에 Lombok을 사용해 축약할 수 있습니다.
    public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    // 댓글 생성
    public String createComment(CommentCreateRequest commentCreateRequest) {
        Long postId = commentCreateRequest.postId();
        String content = commentCreateRequest.content();
        Optional<Post> optionalPost = postRepository.findById(postId);
        // 추후에 람다식을 사용해 축약할 수 있습니다.
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            // 추후에 Lombok을 사용해 축약할 수 있습니다.
            Comment comment = new Comment(post, content);
            commentRepository.save(comment);
            return "댓글 저장 완료";
        }
        return "포스팅을 찾을 수 없음";
    }

}
