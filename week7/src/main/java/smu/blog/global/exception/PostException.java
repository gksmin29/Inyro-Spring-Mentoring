package smu.blog.global.exception;

import smu.blog.global.code.BaseErrorCode;

public class PostException extends CustomException {
    public PostException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode);
    }
}
