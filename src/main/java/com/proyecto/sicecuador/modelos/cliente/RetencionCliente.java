package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "retencion_cliente")
public class RetencionCliente extends Entidad {
    @ManyToOne
    @JoinColumn(name = "tipo_retencion_id", nullable = true)
    private TipoRetencion tipoRetencion;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    public RetencionCliente(){
        super();
        this.tipoRetencion = new TipoRetencion();
    }

    public RetencionCliente(long id) {
        super(id);
    }

    public RetencionCliente(String codigo, TipoRetencion tipoRetencion, Cliente cliente){
        super(codigo);
        this.tipoRetencion=tipoRetencion;
        this.cliente=cliente;
    }

    public RetencionCliente(List<String> datos){
        tipoRetencion=datos.get(0)== null ? null:new TipoRetencion((long) Double.parseDouble(datos.get(0)));
        cliente=datos.get(1)== null ? null:new Cliente((long) Double.parseDouble(datos.get(1)));
    }

    public TipoRetencion getTipoRetencion() {
		return tipoRetencion;
	}
    
    public void setTipoRetencion(TipoRetencion tipoRetencion) {
		this.tipoRetencion = tipoRetencion;
	}

    @JsonBackReference
    public Cliente getCliente() {
        return cliente;
    }

    public void normalizar(){
        if(this.tipoRetencion == null) this.tipoRetencion = new TipoRetencion();
    }
}
