package com.example.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ErrorMessage",
        description = "this is example of ErrorMessage object"
)
public class ErrorMessage {
    private String errorMessage;
    private int errorCode;

    public ErrorMessage(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}