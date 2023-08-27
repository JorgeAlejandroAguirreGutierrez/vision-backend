package com.proyecto.vision.exception;

public class ErrorInternoException extends RuntimeException  {
	private static final long serialVersionUID = 1L;

	private String mensaje;

	public ErrorInternoException(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}
}