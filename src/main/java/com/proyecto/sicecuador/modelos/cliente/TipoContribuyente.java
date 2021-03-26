package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_contribuyente")
public class TipoContribuyente extends Entidad {
	@JsonProperty("tipo")
    @Column(name = "tipo", nullable = true)
    private String tipo;
	@JsonProperty("subtipo")
    @Column(name = "subtipo", nullable = true)
    private String subtipo;
	@JsonProperty("obligado_contabilidad")
    @Column(name = "obligado_contabilidad", nullable = true)
    private boolean obligadoContabilidad;

    public TipoContribuyente(){
        super();
    }

    public TipoContribuyente(long id) {
        super(id);
    }

    public TipoContribuyente(String codigo, String tipo, String subtipo, boolean obligadoContabilidad){
        super(codigo);
        this.tipo=tipo;
        this.subtipo=subtipo;
        this.obligadoContabilidad=obligadoContabilidad;
    }

    public TipoContribuyente(List<String> datos){
        tipo=datos.get(0)== null ? null: datos.get(0);
        subtipo=datos.get(1)== null ? null: datos.get(1);
        obligadoContabilidad=datos.get(2)== null ? null: datos.get(2).equals("S") ? true : false;
    }

    public String getTipo() {
        return tipo;
    }

    public String getSubtipo() {
        return subtipo;
    }

    public boolean isObligadoContabilidad() {
		return obligadoContabilidad;
	}
}
