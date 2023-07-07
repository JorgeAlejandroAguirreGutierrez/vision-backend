package com.proyecto.vision.exception;

public class ParametroInvalidoException extends RuntimeException  {
	private static final long serialVersionUID = 1L;
	
	private String parametro;
	
	public ParametroInvalidoException(String parametro) {
		this.parametro=parametro;
	}
	
	public String getParametro() {
		return parametro;
	}

}
