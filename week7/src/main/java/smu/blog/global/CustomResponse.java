package smu.blog.global;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class CustomResponse<T> {

    @JsonProperty("isSuccess")
    private boolean isSuccess;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("result")
    private final T result;

    // 기본 메시지 사용
    public static <T>CustomResponse<T> onSuccess(T result) {
        return new CustomResponse<>(true, String.valueOf(HttpStatus.OK.value()), "요청이 성공적으로 처리되었습니다.", result);
    }

    // 커스텀 메시지 사용
    public static <T>CustomResponse<T> onSuccess(T result, String message) {
        return new CustomResponse<>(true, String.valueOf(HttpStatus.OK.value()), message, result);
    }

    public static <T>CustomResponse<T> onFailure(String code, String message) {
        return new CustomResponse<>(false, code, message, null);
    }

    public static <T>CustomResponse<T> onFailure(String code, String message, T result) {
        return new CustomResponse<>(false, code, message, result);
    }

}
