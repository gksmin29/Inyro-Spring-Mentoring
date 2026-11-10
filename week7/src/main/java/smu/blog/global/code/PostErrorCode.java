package smu.blog.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum PostErrorCode implements BaseErrorCode {

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST404", "게시글을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}
