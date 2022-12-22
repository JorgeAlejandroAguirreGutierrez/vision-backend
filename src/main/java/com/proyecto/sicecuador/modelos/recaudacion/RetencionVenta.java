package com.proyecto.sicecuador.modelos.recaudacion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "retencion_venta")
public class RetencionVenta extends Entidad {
	@Column(name = "codigo_sri", nullable = true)
    private String codigoSRI;
    @Column(name = "numero", nullable = true)
    private String numero;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "agente", nullable = true)
    private String agente;
    @Column(name = "autorizacion", nullable = true)
    private String autorizacion;
    @Column(name = "compensado", nullable = true)
    private boolean compensado;
    @Column(name = "base_imponible", nullable = true)
    private double baseImponible;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "tipo_retencion_id", nullable = true)
    private TipoRetencion tipoRetencion;
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public RetencionVenta(){
    }

    public RetencionVenta(long id){
        super(id);
    }

    public RetencionVenta(String codigo, String codigoSRI, String numero, Date fecha, String agente, String autorizacion, boolean compensado, double baseImponible, double valor, TipoRetencion tipoRetencion, Recaudacion recaudacion ){
        super(codigo);
        this.codigoSRI=codigoSRI;
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
    
    public String getCodigoSRI() {
		return codigoSRI;
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
