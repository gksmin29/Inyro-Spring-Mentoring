package smu.blog.domain.comment;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import smu.blog.global.CustomResponse;

@RestController
@CrossOrigin("*")
public class CommentController {

    private final CommentService commentService;

    // 추후에 Lombok을 사용해 축약할 수 있습니다.
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    public CustomResponse<?> createComment(@Valid @RequestBody CommentCreateRequest commentCreateRequest) {
        return CustomResponse.onSuccess(commentService.createComment(commentCreateRequest));
    }

}
