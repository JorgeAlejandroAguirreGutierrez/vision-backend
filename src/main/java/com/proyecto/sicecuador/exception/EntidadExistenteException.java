package com.proyecto.sicecuador.exception;

public class EntidadExistenteException extends RuntimeException  {
	private static final long serialVersionUID = 1L;
	
	private String entidad;
	
	public EntidadExistenteException(String entidad) {
		this.entidad=entidad;
	}
	
	public String getEntidad() {
		return entidad;
	}
}
