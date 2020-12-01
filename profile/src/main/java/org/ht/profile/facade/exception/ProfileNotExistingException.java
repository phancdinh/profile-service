package org.ht.profile.facade.exception;

public class ProfileNotExistingException extends RuntimeException {
    public ProfileNotExistingException() {
    }

    public ProfileNotExistingException(String message) {
        super(message);
    }

    public ProfileNotExistingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProfileNotExistingException(Throwable cause) {
        super(cause);
    }

    public ProfileNotExistingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
