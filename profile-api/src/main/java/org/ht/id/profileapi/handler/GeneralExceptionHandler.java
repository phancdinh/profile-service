package org.ht.id.profileapi.handler;

import org.ht.id.account.exception.AccountRegisterFailureException;
import org.ht.id.common.exception.UserInputException;
import org.ht.id.account.exception.DataNotExistingException;
import org.ht.id.account.exception.ServiceUnavailableException;
import org.ht.id.profileapi.dto.response.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AccountRegisterFailureException.class, DataNotExistingException.class,
            ServiceUnavailableException.class, UserInputException.class})

    @Nullable
    public final ResponseEntity<Object> handleProfileException(Exception ex, WebRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        GenericResponse genericResponse = new GenericResponse();
        if (ex instanceof AccountRegisterFailureException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            genericResponse.setFailure(status, "Account registration failed", ex.getLocalizedMessage());
            return handleExceptionInternal((AccountRegisterFailureException) ex, genericResponse, headers,
                    status, request);
        }
        if (ex instanceof UserInputException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            genericResponse.setFailure(status, ex.getMessage(), ex.getLocalizedMessage());
            return handleExceptionInternal((UserInputException) ex, genericResponse, headers,
                    status, request);
        }
        if (ex instanceof DataNotExistingException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            genericResponse.setFailure(status, "Data not found", ex.getLocalizedMessage());
            return handleExceptionInternal((DataNotExistingException) ex, genericResponse, headers, status, request);
        }
        if (ex instanceof ServiceUnavailableException) {
            HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
            genericResponse.setFailure(status, "Service is unavailable", ex.getLocalizedMessage());
            return handleExceptionInternal((ServiceUnavailableException) ex, "Service is unavailable", headers,
                    status, request);
        } else {
            // Unknown exception, typically a wrapper with a common MVC exception as cause
            // (since @ExceptionHandler type declarations also match first-level causes):
            // We only deal with top-level MVC exceptions here, so let's rethrow the given
            // exception for further processing through the HandlerExceptionResolver chain.
            throw ex;
        }
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }

}
