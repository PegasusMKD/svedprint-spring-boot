package com.svedprint.main.errorControllers;

import com.svedprint.main.exceptions.SvedPrintException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandling {

    @ExceptionHandler({SvedPrintException.class})
    public ResponseEntity<ErrorResponse> handleConstraintViolation(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(ex.getLocalizedMessage());
        error.setErrorCode("SvedPrintException");
        error.setData(ex.getCause());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
