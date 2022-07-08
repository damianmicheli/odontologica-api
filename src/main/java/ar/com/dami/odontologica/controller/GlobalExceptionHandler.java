package ar.com.dami.odontologica.controller;

import ar.com.dami.odontologica.service.ConflictoException;
import ar.com.dami.odontologica.service.NoEncontradoException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoEncontradoException.class)
    public ResponseEntity<?> handleNoEncontradoException(NoEncontradoException ex) {

        String mensajeError = ex.getMessage();

        logger.error(mensajeError);

        return new ResponseEntity<>(mensajeError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictoException.class)
    public ResponseEntity<?> handleConflictoException(ConflictoException ex) {

        String mensajeError = ex.getMessage();

        logger.error(mensajeError);

        return new ResponseEntity<>(mensajeError, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex, WebRequest request) {
        logger.error(ex.getMessage());
        return new ResponseEntity<>("Error general: " + ex.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
