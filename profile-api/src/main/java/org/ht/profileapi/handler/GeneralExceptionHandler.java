package org.ht.profileapi.handler;

import org.ht.account.exception.AccountRegisterFailureException;
import org.ht.profileapi.dto.response.GenericResponse;
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

    @ExceptionHandler(AccountRegisterFailureException.class)
    public final ResponseEntity<Object> handleAccountRegisterException(AccountRegisterFailureException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        GenericResponse response = new GenericResponse();
        response.setFailure(HttpStatus.BAD_REQUEST, "Account registration failed", details);
        return ResponseEntity.badRequest().body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        GenericResponse response = new GenericResponse();
        response.setFailure(HttpStatus.BAD_REQUEST, "Validation failed", details);
        return ResponseEntity.badRequest().body(response);
    }
}
