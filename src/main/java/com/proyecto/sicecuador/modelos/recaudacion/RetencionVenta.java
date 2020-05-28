package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.otros.recaudacion.RetencionVentaUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "retencion_venta")
@EntityListeners({RetencionVentaUtil.class})
public class RetencionVenta extends Entidad {
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
    private double base_imponible;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "tipo_retencion_id", nullable = true)
    private TipoRetencion tipo_retencion;
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public RetencionVenta(){
    }

    public RetencionVenta(long id){
        super(id);
    }

    public RetencionVenta(String codigo,String numero, Date fecha, String agente, String autorizacion, boolean compensado, double base_imponible, double valor, TipoRetencion tipo_retencion, Recaudacion recaudacion ){
        super(codigo);
        this.numero=numero;
        this.fecha=fecha;
        this.agente=agente;
        this.autorizacion=autorizacion;
        this.compensado=compensado;
        this.base_imponible=base_imponible;
        this.valor=valor;
        this.tipo_retencion=tipo_retencion;
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

    public double getBase_imponible() {
        return base_imponible;
    }

    public double getValor() {
        return valor;
    }

    public TipoRetencion getTipo_retencion() {
        return tipo_retencion;
    }

    public Recaudacion getRecaudacion() {
        return recaudacion;
    }
}
