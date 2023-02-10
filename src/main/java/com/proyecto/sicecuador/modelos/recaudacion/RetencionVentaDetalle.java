package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "retencion_venta_detalle")
@Data
@AllArgsConstructor
public class RetencionVentaDetalle extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "posicion", nullable = true)
    private long posicion;
    @Column(name = "base_imponible", nullable = true)
    private double baseImponible;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "retencion_venta_id", nullable = true)
    private RetencionVenta retencionVenta;
    @ManyToOne
    @JoinColumn(name = "tipo_retencion_id", nullable = true)
    private TipoRetencion tipoRetencion;

    public RetencionVentaDetalle(long id){
        super(id);
    }
    public RetencionVentaDetalle(){
        super();
        this.codigo = Constantes.vacio;
        this.posicion = Constantes.ceroId;
        this.baseImponible = Constantes.cero;
        this.valor = Constantes.cero;
        this.retencionVenta = new RetencionVenta();
        this.tipoRetencion = new TipoRetencion();
    }
    public void normalizar(){
        if(this.retencionVenta == null) this.retencionVenta = new RetencionVenta();
        if(this.tipoRetencion == null) this.tipoRetencion = new TipoRetencion();
    }
}
