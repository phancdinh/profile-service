package org.ht.account.exception;

public class DataNotExistingException extends RuntimeException {
    public DataNotExistingException() {
    }

    public DataNotExistingException(String message) {
        super(message);
    }

    public DataNotExistingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotExistingException(Throwable cause) {
        super(cause);
    }

    public DataNotExistingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
