package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_contribuyente")
public class TipoContribuyente extends Entidad {
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "subtipo", nullable = true)
    private String subtipo;
    @Column(name = "obligado_contabilidad", nullable = true)
    private String obligadoContabilidad;

    public TipoContribuyente(){
        super();
        this.tipo = Constantes.vacio;
        this.subtipo = Constantes.vacio;
        this.obligadoContabilidad = Constantes.no;
    }

    public TipoContribuyente(long id) {
        super(id);
    }

    public TipoContribuyente(String codigo, String tipo, String subtipo, String obligadoContabilidad){
        super(codigo);
        this.tipo=tipo;
        this.subtipo=subtipo;
        this.obligadoContabilidad=obligadoContabilidad;
    }

    public TipoContribuyente(List<String> datos){
        tipo=datos.get(0)== null ? null: datos.get(0);
        subtipo=datos.get(1)== null ? null: datos.get(1);
        obligadoContabilidad=datos.get(2)== null ? null: datos.get(2);
    }

    public String getTipo() {
        return tipo;
    }

    public String getSubtipo() {
        return subtipo;
    }

    public String getObligadoContabilidad() {
		return obligadoContabilidad;
	}
}
