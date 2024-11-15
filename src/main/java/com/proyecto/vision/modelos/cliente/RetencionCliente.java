package com.proyecto.vision.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.configuracion.TipoRetencion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_retencion_cliente;

@Entity
@Table(name = tabla_retencion_cliente)
@Getter
@Setter
@AllArgsConstructor
public class RetencionCliente extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @ManyToOne
    @JoinColumn(name = "tipo_retencion_id", nullable = true)
    private TipoRetencion tipoRetencion;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
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
