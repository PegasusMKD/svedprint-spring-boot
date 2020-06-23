package com.svedprint.main.errorControllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String message;
    private Object data;

    public ErrorResponse() {
        errorCode = "";
        message = "";
    }

    public ErrorResponse(String errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
