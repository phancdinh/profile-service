package org.ht.id.common.exception;

import org.ht.id.common.exception.BaseException;

import java.util.UUID;

public class EmailAlreadyRegisteredException extends BaseException {
    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }
    public EmailAlreadyRegisteredException(String message, UUID traceId) {
        super(message, traceId);
    }
}
