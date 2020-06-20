package com.svedprint.main.exceptions;

public class SvedPrintException extends RuntimeException {
    private static final long serialVersionUID = 8633391674318795826L;

    public SvedPrintException(SvedPrintExceptionType exceptionType) {
        super(exceptionType.toString());
    }

    public SvedPrintException(SvedPrintExceptionType exceptionType, String message) {
        super(exceptionType.toString() + " " + message);
    }
}
