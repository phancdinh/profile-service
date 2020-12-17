package org.ht.id.common.exception;

import java.util.UUID;

public class DataConflictingException extends BaseException {
    public DataConflictingException(String message) {
        super(message);
    }
    public DataConflictingException(String message, UUID traceId) {
        super(message, traceId);
    }
}
