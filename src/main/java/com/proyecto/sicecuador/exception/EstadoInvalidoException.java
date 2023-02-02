package com.proyecto.sicecuador.exception;

public class EstadoInvalidoException extends RuntimeException  {
	private static final long serialVersionUID = 1L;

	private String entidad;

	public EstadoInvalidoException(String parametro) {
		this.entidad=entidad;
	}

	public String getEntidad() {
		return entidad;
	}
}
