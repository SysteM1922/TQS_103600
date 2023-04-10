package tqs.air_quality.controler;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
	public ResponseEntity<Object> handleException(HttpRequestMethodNotSupportedException ex) {
		String message = String.format("Método %s não suportado. Métodos suportados: %s",
				ex.getMethod(),
				Arrays.toString(ex.getSupportedMethods()));
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(message);
	}
	
}
