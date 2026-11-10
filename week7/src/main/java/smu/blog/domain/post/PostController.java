package smu.blog.domain.post;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import smu.blog.global.CustomResponse;

@RestController
@CrossOrigin("*")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 포스팅 게시
    @PostMapping("/posts")
    public CustomResponse<?> create(@Valid @RequestBody PostCreateRequest postCreateRequest) {
        return CustomResponse.onSuccess(postService.createPost(postCreateRequest));
    }

    // 포스트 단건 조회
    @GetMapping("/posts/{id}")
    public CustomResponse<?> getPost(@PathVariable Long id) {
        return CustomResponse.onSuccess(postService.getPost(id));
    }

    // 모든 포스트 조회
    @GetMapping("/posts")
    public CustomResponse<?> getPosts() {
        return CustomResponse.onSuccess(postService.getPosts());
    }

    // 포스팅 수정
    @PatchMapping("/posts")
    public CustomResponse<?> updatePost(@Valid @RequestBody PostUpdateRequest postUpdateRequest) {
        return CustomResponse.onSuccess(postService.updatePost(postUpdateRequest));
    }

    // 포스팅 삭제
    @DeleteMapping("/posts/{id}")
    public CustomResponse<?> deletePost(@PathVariable Long id) {
        return CustomResponse.onSuccess(postService.deletePost(id));
    }

}
