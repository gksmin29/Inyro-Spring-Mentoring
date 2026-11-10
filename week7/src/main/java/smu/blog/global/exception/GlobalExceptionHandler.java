package smu.blog.global.exception;

import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smu.blog.global.CustomResponse;
import smu.blog.global.code.BaseErrorCode;
import smu.blog.global.code.GeneralErrorCode;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Bean Validation의 DTO 검증으로 발생하는 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<@NonNull CustomResponse<Map<String, String>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>(); // 에러 정보는 Map으로 저장
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage())
        );  // 예외에서 정보를 꺼내 각 필드 단위로 Map에 저장 (필드명, 메시지)

        return buildErrorResponse(GeneralErrorCode.VALIDATION_FAILED, errors);
    }

    // 애플리케이션에서 발생하는 커스텀 예외를 처리
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<@NonNull CustomResponse<Void>> handleCustomException(CustomException ex) {
        return buildErrorResponse(ex.getBaseErrorCode());
    }

    // 그외 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<@NonNull CustomResponse<Void>> handleAllException(Exception ex) {
        return buildErrorResponse(GeneralErrorCode.INTERNAL_SERVER_ERROR);
    }

    // 검증 실패 등 부가 데이터가 필요한 경우
    private <T> ResponseEntity<CustomResponse<T>> buildErrorResponse(BaseErrorCode errorCode, T result) {
        CustomResponse<T> errorResponse = CustomResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), result);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    // 부가 데이터가 없는 경우
    private ResponseEntity<CustomResponse<Void>> buildErrorResponse(BaseErrorCode errorCode) {
        return buildErrorResponse(errorCode, null);
    }

}
