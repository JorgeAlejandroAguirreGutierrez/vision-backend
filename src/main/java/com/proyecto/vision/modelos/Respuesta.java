package com.proyecto.vision.modelos;

import org.springframework.validation.ObjectError;

import java.util.List;

public class Respuesta {
    private boolean exito;
    private String mensaje;
    private Object resultado;
    
    public Respuesta() {
    	
    }

    public Respuesta(boolean exito, String mensaje,  Object resultado) {
        this.exito=exito;
        this.mensaje=mensaje;
        this.resultado=resultado;
    }

    public Respuesta(boolean exito, List<ObjectError> errores, Object resultado){
        this.exito=exito;
        mensaje="";
        for (int i=0; i<errores.size(); i++){
            mensaje=mensaje+errores.get(i).getDefaultMessage();
            if (i !=errores.size()-1) mensaje=mensaje+" - ";
        }
        this.resultado=resultado;
    }

    public boolean getExito() {
        return exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Object getResultado() {
        return resultado;
    }
}
