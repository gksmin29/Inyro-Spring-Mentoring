package smu.blog.post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 포스트 생성
    public String createPost(PostCreateRequest postCreateRequest) {
        String title = postCreateRequest.title();
        String content = postCreateRequest.content();

        Post post = new Post(title, content);
        postRepository.save(post);

        return "저장 완료";
    }

    // 포스트 단건 조회
    public Post getPost(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        }
        else {
            return null;
        }
    }

    // 포스트 조회
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    // 포스트 수정
    public String updatePost(PostUpdateRequest postUpdateRequest) {
        Long postId = postUpdateRequest.postId();
        String title = postUpdateRequest.title();
        String content = postUpdateRequest.content();

        Post post = getPost(postId);

        if (post != null) {
            post.changeTitleAndContent(title, content);
            return "수정 완료";
        }
        return "포스팅 정보 없음";
    }

    // 포스트 삭제
    public String deletePost(Long id) {
        Post post = getPost(id);
        if (post != null) {
            postRepository.delete(post);
            return "삭제 완료";
        }
        return "포스팅 정보 없음";
    }

}
