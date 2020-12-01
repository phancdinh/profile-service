package org.ht.profile.facade.exception;

public class ProfileConflictingException extends RuntimeException {
    public ProfileConflictingException() {
    }

    public ProfileConflictingException(String message) {
        super(message);
    }

    public ProfileConflictingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfileConflictingException(Throwable cause) {
        super(cause);
    }

    public ProfileConflictingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
