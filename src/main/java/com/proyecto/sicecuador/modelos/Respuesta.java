package com.proyecto.sicecuador.modelos;

public class Respuesta {
    private boolean exito;
    private String mensaje;
    private Object resultado;

    public Respuesta(boolean exito, String mensaje,  Object resultado) {
        this.exito=exito;
        this.mensaje=mensaje;
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
