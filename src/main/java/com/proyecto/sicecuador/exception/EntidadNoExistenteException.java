package com.proyecto.vision.exception;

public class EntidadNoExistenteException extends RuntimeException  {
	private static final long serialVersionUID = 1L;
	
	private String entidad;
	
	public EntidadNoExistenteException(String entidad) {
		this.entidad=entidad;
	}
	
	public String getEntidad() {
		return entidad;
	}
}
