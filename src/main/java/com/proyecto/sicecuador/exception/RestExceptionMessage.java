package com.proyecto.vision.exception;

import java.util.Date;
import java.util.List;

public class RestExceptionMessage {
	private String codigo;
    private String mensaje;
    private List<String> errores;
    private Date timestamp;

    public RestExceptionMessage(String codigo, String mensaje, List<String> errores) {
        this.mensaje = mensaje;
        this.errores = errores;
        this.timestamp = new Date();
        this.codigo=codigo;
    }
    
    public String getCodigo() {
		return codigo;
	}

    public String getMensaje() {
		return mensaje;
	}
    
    public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
    
    public List<String> getErrores() {
		return errores;
	}
    
    public void setErrores(List<String> errores) {
		this.errores = errores;
	}

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
