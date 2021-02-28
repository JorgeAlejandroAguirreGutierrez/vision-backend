package com.proyecto.sicecuador.exception;

import com.proyecto.sicecuador.otros.Util;

public class ModeloNoExistenteException extends RuntimeException  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModeloNoExistenteException() {
        super(Util.error_registro_no_existente);
    }
}
