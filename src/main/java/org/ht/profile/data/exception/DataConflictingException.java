package org.ht.profile.data.exception;

public class DataConflictingException extends RuntimeException {
    public DataConflictingException() {
    }

    public DataConflictingException(String message) {
        super(message);
    }

    public DataConflictingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataConflictingException(Throwable cause) {
        super(cause);
    }

    public DataConflictingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
