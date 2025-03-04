package com.proyecto.vision.exception;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.proyecto.vision.Constantes;

public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<RestExceptionMessage> handleAllExceptions(Exception ex, WebRequest req) {
		List<String> errors=new ArrayList<String>();
		errors.add(ex.getLocalizedMessage());
		errors.add(ex.getMessage());
		errors.add(ex.toString());
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_generico,
				Constantes.error_generico,
				errors
		);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(EntidadExistenteException.class)
	public final ResponseEntity<RestExceptionMessage> handleModeloExistenteException(
			EntidadExistenteException ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_modelo_existente,
				Constantes.error_entidad_existente+Constantes.espacio+ex.getEntidad(),
				null
		);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(EntidadNoExistenteException.class)
	public final ResponseEntity<RestExceptionMessage> handleModeloNoExistenteException(EntidadNoExistenteException ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_modelo_no_existente,
				Constantes.error_entidad_no_existente+Constantes.espacio+ex.getEntidad(),
				null
		);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CodigoNoExistenteException.class)
	public final ResponseEntity<RestExceptionMessage> handleCodigoNoExistenteException(
			CodigoNoExistenteException ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_codigo_no_existente,
				Constantes.error_codigo_no_existente,
				null
		);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(SecuencialNoExistenteException.class)
	public final ResponseEntity<RestExceptionMessage> handleSecuencialNoExistenteException(
			SecuencialNoExistenteException ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_secuencial_no_existente,
				Constantes.error_secuencial_no_existente,
				null
		);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IdentificacionInvalidaException.class)
	public final ResponseEntity<RestExceptionMessage> handleIdentificacionInvalidaException(
			IdentificacionInvalidaException ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_identificacion_invalida,
				Constantes.error_identificacion_invalida,
				null
		);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ParametroInvalidoException.class)
	public final ResponseEntity<RestExceptionMessage> handleParametroInvalidaException(
			ParametroInvalidoException ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_parametro_invalido,
				Constantes.error_parametro_invalido+Constantes.espacio+ex.getParametro(),
				null
		);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(EstadoInvalidoException.class)
	public final ResponseEntity<RestExceptionMessage> handleEstadoInvalidoException(
			EstadoInvalidoException ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_estado_invalido,
				Constantes.error_estado_invalido+Constantes.espacio+ex.getEstado(),
				null
		);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DatoInvalidoException.class)
	public final ResponseEntity<RestExceptionMessage> handleDatoInvalidoException(
			DatoInvalidoException ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_dato_invalido,
				Constantes.error_dato_invalido + Constantes.espacio + ex.getDato(),
				null
		);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CertificadoNoExistenteException.class)
	public final ResponseEntity<RestExceptionMessage> handleCertificadoNoExistenteException(
			CertificadoNoExistenteException ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_certificado_no_existente,
				Constantes.error_certificado_no_existente,
				null
		);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FacturaElectronicaInvalidaException.class)
	public final ResponseEntity<RestExceptionMessage> handleFacturaElectronicaInvalidaException(
			FacturaElectronicaInvalidaException ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_factura_electronica_invalida,
				ex.getEstado(),
				null
		);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(SuscripcionInvalidaException.class)
	public final ResponseEntity<RestExceptionMessage> handleSuscripcionInvalidaException(
			SuscripcionInvalidaException ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_suscripcion_invalida,
				Constantes.error_suscripcion_invalida,
				null
		);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ErrorInternoException.class)
	public final ResponseEntity<RestExceptionMessage> handleErrorInternoException(
			ErrorInternoException ex, WebRequest req) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_generico,
				Constantes.error_generico + Constantes.espacio + ex.getMensaje(),
				null
		);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> errors = new ArrayList<>();
		for(FieldError field : ex.getBindingResult().getFieldErrors()) {
			errors.add(field.getField());
		}
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_datos_invalidos, ex.getMessage(),errors);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		RestExceptionMessage exceptionResponse = new RestExceptionMessage(Constantes.error_codigo_datos_invalidos, ex.getMessage(),null);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}