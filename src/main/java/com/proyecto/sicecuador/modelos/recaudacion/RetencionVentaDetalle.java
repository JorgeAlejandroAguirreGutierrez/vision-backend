package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.otros.recaudacion.RetencionVentaDetalleUtil;

import javax.persistence.*;

@Entity
@Table(name = "retencion_venta_detalle")
@EntityListeners({RetencionVentaDetalleUtil.class})
public class RetencionVentaDetalle extends Entidad {
    @Column(name = "posicion", nullable = true)
    private long posicion;
    @Column(name = "base_imponible", nullable = true)
    private double base_imponible;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "retencion_venta_id", nullable = true)
    private RetencionVenta retencion_venta;
    @ManyToOne
    @JoinColumn(name = "tipo_retencion_id", nullable = true)
    private TipoRetencion tipo_retencion;

    public RetencionVentaDetalle(){
    }

    public RetencionVentaDetalle(long id){
        super(id);
    }

    public RetencionVentaDetalle(String codigo, long posicion, double base_imponible, double valor, RetencionVenta retencion_venta, TipoRetencion tipo_retencion){
        super(codigo);
        this.posicion=posicion;
        this.base_imponible=base_imponible;
        this.retencion_venta=retencion_venta;
        this.tipo_retencion=tipo_retencion;
    }
    public long getPosicion() {
        return posicion;
    }

    public double getBase_imponible() {
        return base_imponible;
    }

    public RetencionVenta getRetencion_venta() {
        return retencion_venta;
    }

    public TipoRetencion getTipo_retencion() {
        return tipo_retencion;
    }
}
