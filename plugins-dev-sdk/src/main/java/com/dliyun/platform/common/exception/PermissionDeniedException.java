package com.dliyun.platform.common.exception;

public class PermissionDeniedException extends Exception {
    private static final long serialVersionUID = -438346846416711065L;

    public PermissionDeniedException() {
    }

    public PermissionDeniedException(String message) {
        super(message);
    }

    public PermissionDeniedException(Throwable cause) {
        super(cause);
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
