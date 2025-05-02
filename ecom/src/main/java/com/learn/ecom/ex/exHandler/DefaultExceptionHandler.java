package com.learn.ecom.ex.exHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class DefaultExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultException> handleDefaultException(Exception ex, WebRequest request) {
        DefaultException defaultException = new DefaultException();

        switch (ex.getClass().getSimpleName()) {
            case "NoResourceFoundException" -> {
            defaultException.errorCode = "404";
            defaultException.errorMessage = "Resource not found.";
            defaultException.errorDescription = "The requested resource could not be found on the server.";
            defaultException.errEndpoint = request.getDescription(false);
            return new ResponseEntity<>(defaultException, HttpStatus.NOT_FOUND);

            }
            case "IllegalArgumentException" -> {
            defaultException.errorCode = "400";
            defaultException.errorMessage = "Bad Request";
            defaultException.errorDescription = "The request contains invalid parameters.";
            defaultException.errEndpoint = request.getDescription(false);
            return new ResponseEntity<>(defaultException, HttpStatus.BAD_REQUEST);

            }
            case "NullPointerException" -> {
            defaultException.errorCode = "500";
            defaultException.errorMessage = "Null Pointer Exception";
            defaultException.errorDescription = "A null value was encountered where it is not allowed.";
            defaultException.errEndpoint = request.getDescription(false);
            return new ResponseEntity<>(defaultException, HttpStatus.INTERNAL_SERVER_ERROR);

            }
            default -> {
            defaultException.errorCode = "500";
            defaultException.errorMessage = "Internal Server Error";
            defaultException.errorDescription = ex.getMessage();
            defaultException.errEndpoint = request.getDescription(false);
            return new ResponseEntity<>(defaultException, HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }
      
     

    }

}
