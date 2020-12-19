package com.proyecto.sicecuador.exception;

import com.proyecto.sicecuador.otros.Util;

public class ModeloExistenteException extends RuntimeException  {
	
	public ModeloExistenteException() {
        super(Util.error_registro_existente);
    }
}
