package com.axungu.common.exception;

public class NoFoundException extends Exception {

    private static final long serialVersionUID = -6599531762911251584L;

    public NoFoundException() {
    }

    public NoFoundException(String message) {
        super(message);
    }

    public NoFoundException(Throwable cause) {
        super(cause);
    }

    public NoFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
