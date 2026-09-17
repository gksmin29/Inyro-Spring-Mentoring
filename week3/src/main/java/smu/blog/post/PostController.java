package smu.blog.post;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 포스트 게시
    @PostMapping("/posts")
    public String create(@RequestBody PostCreateRequest postCreateRequest) {
        return postService.createPost(postCreateRequest);
    }

    // 포스트 단건 조회
    @GetMapping("/posts/{id}")
    public Post getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 모든 포스트 조회
    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postService.getPosts();
    }

}
