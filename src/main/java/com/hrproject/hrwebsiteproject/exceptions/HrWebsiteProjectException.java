package com.hrproject.hrwebsiteproject.exceptions;

public class HrWebsiteProjectException extends RuntimeException {
    private ErrorType errorType;

    public HrWebsiteProjectException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
