package org.ht.id.common.exception;

import java.util.UUID;

public class ServiceUnavailableException extends BaseException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
    public ServiceUnavailableException(String message, UUID traceId) {
        super(message, traceId);
    }
}
