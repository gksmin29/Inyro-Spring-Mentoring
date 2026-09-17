package smu.blog.post;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    private List<Post> postList = new ArrayList<>();
    private Long sequence = 0L;

    // 포스트 게시
    @PostMapping("/posts")
    public String createPost(@RequestBody PostCreateRequest postCreateRequest) {
        sequence += 1;
        String title = postCreateRequest.title();
        String content = postCreateRequest.content();

        Post post = new Post(sequence, title, content);
        postList.add(post);

        return "저장 완료";
    }

    // 포스트 단건 조회
    @GetMapping("/posts/{id}")
    public Post getPost(@PathVariable Long id) {
        for (Post post : postList) {
            if (post.getPostId().equals(id)) {
                return post;
            }
        }
        return null;
    }

    // 모든 포스트 조회
    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postList;
    }

}
