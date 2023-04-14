package com.proyecto.sicecuador.exception;

public class FacturaElectronicaInvalidaException extends RuntimeException  {
	private static final long serialVersionUID = 1L;

	private String estado;

	public FacturaElectronicaInvalidaException(String estado) {
		this.estado = estado;
	}

	public String getEstado() {
		return estado;
	}
}
