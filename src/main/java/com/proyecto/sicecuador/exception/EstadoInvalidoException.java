package com.proyecto.vision.exception;

public class EstadoInvalidoException extends RuntimeException  {
	private static final long serialVersionUID = 1L;

	private String estado;

	public EstadoInvalidoException(String estado) {
		this.estado=estado;
	}

	public String getEstado() {
		return estado;
	}
}
