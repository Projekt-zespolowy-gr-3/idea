package pl.dmcs.idea.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.dmcs.idea.exceptions.AppBaseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Handled exception", e);
        if (e instanceof AppBaseException) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(AppBaseException.UNEXPECTED_ERROR);
        }
    }
}
