package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;

import javax.persistence.*;

@Entity
@Table(name = "retencion_venta_detalle")
public class RetencionVentaDetalle extends Entidad {
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

    public RetencionVentaDetalle(){
        super();
        this.posicion = Constantes.ceroId;
        this.baseImponible = Constantes.cero;
        this.valor = Constantes.cero;
        this.retencionVenta = new RetencionVenta();
        this.tipoRetencion = new TipoRetencion();
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

    public void normalizar(){
        if(this.retencionVenta == null) this.retencionVenta = new RetencionVenta();
        if(this.tipoRetencion == null) this.tipoRetencion = new TipoRetencion();
    }
}
