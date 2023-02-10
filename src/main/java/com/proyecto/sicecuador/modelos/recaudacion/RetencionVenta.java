package com.proyecto.sicecuador.modelos.recaudacion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "retencion_venta")
@Data
@AllArgsConstructor
public class RetencionVenta extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
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
    private String compensado;
    @Column(name = "base_imponible", nullable = true)
    private double baseImponible;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "tipo_retencion_id", nullable = true)
    private TipoRetencion tipoRetencion;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public RetencionVenta(long id){
        super(id);
    }
    public RetencionVenta(){
        super();
        this.codigo = Constantes.vacio;
        this.codigoSRI = Constantes.vacio;
        this.numero = Constantes.vacio;
        this.fecha = new Date();
        this.agente = Constantes.vacio;
        this.autorizacion = Constantes.vacio;
        this.compensado = Constantes.vacio;
        this.baseImponible = Constantes.cero;
        this.valor = Constantes.cero;
        this.tipoRetencion = new TipoRetencion();
    }
    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.tipoRetencion == null) this.tipoRetencion = new TipoRetencion();
    }
}
