package org.ht.id.common.exception;

import java.util.UUID;

public class AccountRegisterFailureException extends BaseException {
    public AccountRegisterFailureException(String message) {
        super(message);
    }
    public AccountRegisterFailureException(String message, UUID traceId) {
        super(message, traceId);
    }
}