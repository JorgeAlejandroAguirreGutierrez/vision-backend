package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "retencion_cliente")
@Data
@AllArgsConstructor
public class RetencionCliente extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @ManyToOne
    @JoinColumn(name = "tipo_retencion_id", nullable = true)
    private TipoRetencion tipoRetencion;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    public RetencionCliente(long id){
        super(id);
    }
    public RetencionCliente(){
        super();
        this.codigo = Constantes.vacio;
        this.tipoRetencion = new TipoRetencion();
    }

    public void normalizar(){
        if(this.tipoRetencion == null) this.tipoRetencion = new TipoRetencion();
    }
}
