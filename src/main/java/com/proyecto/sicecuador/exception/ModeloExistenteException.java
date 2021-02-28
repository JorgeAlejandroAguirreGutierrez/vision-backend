package com.proyecto.sicecuador.exception;

import com.proyecto.sicecuador.otros.Util;

public class ModeloExistenteException extends RuntimeException  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModeloExistenteException() {
        super(Util.error_registro_existente);
    }
}
