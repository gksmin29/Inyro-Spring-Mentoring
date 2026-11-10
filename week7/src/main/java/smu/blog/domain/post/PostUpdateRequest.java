package smu.blog.domain.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostUpdateRequest(

        @NotNull
        Long postId,

        @NotBlank(message = "제목은 필수 입력 항목입니다.")
        @Size(max = 20, message = "제목은 최대 20자까지 입력 가능합니다.")
        String title,

        @NotBlank(message = "내용은 필수 입력 항목입니다.")
        String content
) {
}
