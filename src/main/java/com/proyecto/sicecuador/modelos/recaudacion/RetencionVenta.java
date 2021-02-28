package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.otros.recaudacion.RetencionVentaUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "retencion_venta")
public class RetencionVenta extends Entidad {
	@JsonProperty("numero")
    @Column(name = "numero", nullable = true)
    private String numero;
	@JsonProperty("fecha")
    @Column(name = "fecha", nullable = true)
    private Date fecha;
	@JsonProperty("agente")
    @Column(name = "agente", nullable = true)
    private String agente;
	@JsonProperty("autorizacion")
    @Column(name = "autorizacion", nullable = true)
    private String autorizacion;
	@JsonProperty("compensado")
    @Column(name = "compensado", nullable = true)
    private boolean compensado;
	@JsonProperty("base_imponible")
    @Column(name = "base_imponible", nullable = true)
    private double baseImponible;
	@JsonProperty("valor")
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JsonProperty("tipo_retencion")
    @JoinColumn(name = "tipo_retencion_id", nullable = true)
    private TipoRetencion tipoRetencion;
    @ManyToOne
    @JsonProperty("recaudacion")
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public RetencionVenta(){
    }

    public RetencionVenta(long id){
        super(id);
    }

    public RetencionVenta(String codigo,String numero, Date fecha, String agente, String autorizacion, boolean compensado, double baseImponible, double valor, TipoRetencion tipoRetencion, Recaudacion recaudacion ){
        super(codigo);
        this.numero=numero;
        this.fecha=fecha;
        this.agente=agente;
        this.autorizacion=autorizacion;
        this.compensado=compensado;
        this.baseImponible=baseImponible;
        this.valor=valor;
        this.tipoRetencion=tipoRetencion;
        this.recaudacion=recaudacion;
    }

    public String getNumero() {
        return numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getAgente() {
        return agente;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public boolean isCompensado() {
        return compensado;
    }

    public double getBaseImponible() {
		return baseImponible;
	}

    public double getValor() {
        return valor;
    }

    public TipoRetencion getTipoRetencion() {
		return tipoRetencion;
	}
    @JsonBackReference
    public Recaudacion getRecaudacion() {
        return recaudacion;
    }
}
