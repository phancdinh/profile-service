package org.ht.account.exception;

public class AccountRegisterFailureException extends RuntimeException {
    public AccountRegisterFailureException() {
    }

    public AccountRegisterFailureException(String message) {
        super(message);
    }

    public AccountRegisterFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountRegisterFailureException(Throwable cause) {
        super(cause);
    }

    public AccountRegisterFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
