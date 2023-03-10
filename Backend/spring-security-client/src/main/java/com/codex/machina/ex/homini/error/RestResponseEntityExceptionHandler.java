package com.codex.machina.ex.homini.error;

import com.codex.machina.ex.homini.entity.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<ErrorMessage> articleNotFoundException(ArticleNotFoundException exception, WebRequest request)
    {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorMessage> IOException(IOException exception, WebRequest request)
    {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.SERVICE_UNAVAILABLE, exception.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorMessage);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
     public ResponseEntity<ErrorMessage> sqlIntegrityConstraintViolationException(
             SQLIntegrityConstraintViolationException exception, WebRequest request)
     {
         ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
         return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
     }

     @ExceptionHandler(AuthenticationException.class)
     public ResponseEntity<ErrorMessage> authenticationException(AuthenticationException exception, WebRequest request)
     {
         ErrorMessage errorMessage = new ErrorMessage(HttpStatus.FORBIDDEN, exception.getMessage());
         return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
     }

     @ExceptionHandler(ServletException.class)
     public ResponseEntity<ErrorMessage> servletException(ServletException exception, WebRequest request)
     {
         ErrorMessage errorMessage = new ErrorMessage(HttpStatus.FORBIDDEN, exception.getMessage());
         return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
     }
}
