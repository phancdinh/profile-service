package org.ht.id.common.exception;

import org.ht.id.common.exception.BaseException;

import java.util.UUID;

public class WrongInputException extends BaseException {
    public WrongInputException(String message) {
        super(message);
    }
    public WrongInputException(String message, UUID traceId) {
        super(message, traceId);
    }
}