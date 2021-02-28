package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.otros.recaudacion.RetencionVentaDetalleUtil;

import javax.persistence.*;

@Entity
@Table(name = "retencion_venta_detalle")
@EntityListeners({RetencionVentaDetalleUtil.class})
public class RetencionVentaDetalle extends Entidad {
	@JsonProperty("posicion")
    @Column(name = "posicion", nullable = true)
    private long posicion;
	@JsonProperty("base_imponible")
    @Column(name = "base_imponible", nullable = true)
    private double baseImponible;
	@JsonProperty("valor")
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JsonProperty("retencion_venta")
    @JoinColumn(name = "retencion_venta_id", nullable = true)
    private RetencionVenta retencionVenta;
    @ManyToOne
    @JsonProperty("tipo_retencion")
    @JoinColumn(name = "tipo_retencion_id", nullable = true)
    private TipoRetencion tipoRetencion;

    public RetencionVentaDetalle(){
    }

    public RetencionVentaDetalle(long id){
        super(id);
    }

    public RetencionVentaDetalle(String codigo, long posicion, double baseImponible, double valor, RetencionVenta retencionVenta, TipoRetencion tipoRetencion){
        super(codigo);
        this.posicion=posicion;
        this.baseImponible=baseImponible;
        this.retencionVenta=retencionVenta;
        this.tipoRetencion=tipoRetencion;
    }
    public long getPosicion() {
        return posicion;
    }

    public double getBaseImponible() {
		return baseImponible;
	}

    public RetencionVenta getRetencionVenta() {
		return retencionVenta;
	}

    public TipoRetencion getTipoRetencion() {
		return tipoRetencion;
	}
}
