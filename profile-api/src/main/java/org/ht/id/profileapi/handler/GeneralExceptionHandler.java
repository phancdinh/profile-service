package org.ht.id.profileapi.handler;

import org.ht.id.common.exception.AccountRegisterFailureException;
import org.ht.id.common.exception.BaseException;
import org.ht.id.common.exception.DataConflictingException;
import org.ht.id.common.exception.DataNotExistingException;
import org.ht.id.common.exception.EmailAlreadyRegisteredException;
import org.ht.id.common.exception.InactivatedAccountException;
import org.ht.id.common.exception.ServiceUnavailableException;
import org.ht.id.common.exception.WrongInputException;
import org.ht.id.profileapi.dto.response.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AccountRegisterFailureException.class, WrongInputException.class,
            DataNotExistingException.class, InactivatedAccountException.class, ServiceUnavailableException.class})
    public final ResponseEntity<Object> handleBadRequestException(BaseException ex, WebRequest request) {
        return createFailureResponse(HttpStatus.BAD_REQUEST, ex.getMessage());

    }

    @ExceptionHandler({DataConflictingException.class, EmailAlreadyRegisteredException.class})
    public ResponseEntity<Object> handleConflictException(BaseException ex, WebRequest request) {
        return createFailureResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        return createFailureResponse(status, details.toString());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternalServerException(Exception ex, WebRequest request) {
        return createFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<Object> createFailureResponse(HttpStatus status, String message) {
        GenericResponse response = new GenericResponse();
        response.setFailure(status, message);
        return ResponseEntity.status(status).body(response);
    }
}
