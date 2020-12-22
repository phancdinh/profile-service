package org.ht.id.common.exception;

import org.ht.id.common.exception.BaseException;

import java.util.UUID;

public class InactivatedAccountException extends BaseException {
    public InactivatedAccountException(String message) {
        super(message);
    }
    public InactivatedAccountException(String message, UUID traceId) {
        super(message, traceId);
    }
}
