package org.ht.id.common.exception;

import java.util.UUID;

public class EncryptFailureException extends BaseException {
    public EncryptFailureException(String message) {
        super(message);
    }
    public EncryptFailureException(String message, UUID traceId) {
        super(message, traceId);
    }
}
