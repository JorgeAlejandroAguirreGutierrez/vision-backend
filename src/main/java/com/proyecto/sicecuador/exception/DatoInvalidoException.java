package com.proyecto.sicecuador.exception;

public class DatoInvalidoException extends RuntimeException  {
	private static final long serialVersionUID = 1L;

	private String dato;

	public DatoInvalidoException(String dato) {
		this.dato=dato;
	}

	public String getDato() {
		return dato;
	}
}
