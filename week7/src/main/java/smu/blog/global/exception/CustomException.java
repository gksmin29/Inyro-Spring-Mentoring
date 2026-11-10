package smu.blog.global.exception;

import lombok.Getter;
import smu.blog.global.code.BaseErrorCode;

@Getter
public class CustomException extends RuntimeException {

    public CustomException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode.getMessage());
        this.baseErrorCode = baseErrorCode;
    }

    private final BaseErrorCode baseErrorCode;
}
