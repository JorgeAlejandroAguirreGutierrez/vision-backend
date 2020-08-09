package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.otros.cliente.GeneroUtil;
import com.proyecto.sicecuador.otros.cliente.RetencionClienteUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "retencion_cliente")
//@EntityListeners({RetencionClienteUtil.class})
public class RetencionCliente extends Entidad {
    @ManyToOne(cascade = CascadeType.MERGE, optional = true)
    @JoinColumn(name = "tipo_retencion_id", nullable = true)
    private TipoRetencion tipo_retencion;
    @ManyToOne(cascade = CascadeType.MERGE, optional = true)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    public RetencionCliente(){
        super();
    }

    public RetencionCliente(long id) {
        super(id);
    }

    public RetencionCliente(String codigo, TipoRetencion tipo_retencion, Cliente cliente){
        super(codigo);
        this.tipo_retencion=tipo_retencion;
        this.cliente=cliente;
    }

    public RetencionCliente(List<String> datos){
        tipo_retencion=datos.get(0)== null ? null:new TipoRetencion((long) Double.parseDouble(datos.get(0)));
        cliente=datos.get(1)== null ? null:new Cliente((long) Double.parseDouble(datos.get(1)));
    }

    public TipoRetencion getTipo_retencion() {
        return tipo_retencion;
    }

    public void setTipo_retencion(TipoRetencion tipo_retencion) {
        this.tipo_retencion = tipo_retencion;
    }

    @JsonBackReference
    public Cliente getCliente() {
        return cliente;
    }
}
