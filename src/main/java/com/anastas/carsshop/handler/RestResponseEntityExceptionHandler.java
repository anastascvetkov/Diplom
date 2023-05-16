package com.anastas.carsshop.handler;

import com.anastas.carsshop.excaption.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {InvalidIdValueException.class})
    protected ResponseEntity<Object> handleInvalidIdValueException(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception, exception.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ElementNotFoundException.class})
    protected ResponseEntity<Object> handleElementNotFoundException(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception, exception.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {IncorrectPasswordException.class})
    protected ResponseEntity<Object> handleIncorrectPasswordException(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception, exception.getMessage(),
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgument(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception, exception.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler(value = {BadRequestContentException.class})
    protected ResponseEntity<Object> handleBadRequestContentException(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception, exception.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {BadLoginCredentialException.class})
    protected ResponseEntity<Object> handleBadLoginCredentialException(RuntimeException exception, WebRequest request){
        return handleExceptionInternal(exception, exception.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
