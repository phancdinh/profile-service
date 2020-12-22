package org.ht.id.common.exception;

import java.util.UUID;

public class BaseException extends RuntimeException {
    private UUID traceId;

    public BaseException() { }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, UUID traceId) {
        super(message);
        this.traceId = traceId;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UUID getTraceId() {
        return traceId;
    }

    public void setTraceId(UUID traceId) {
        this.traceId = traceId;
    }
}
