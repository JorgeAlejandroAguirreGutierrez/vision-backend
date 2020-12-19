package com.proyecto.sicecuador.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
    public final ResponseEntity<RestExceptionMessage> handleAllExceptions(Exception ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(
			ex.getMessage(),
	        null
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(ModeloExistenteException.class)
	public final ResponseEntity<RestExceptionMessage> handleModeloExistenteException(
	    ModeloExistenteException ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(
	        ex.getMessage(),
	        null
	    );
	    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
