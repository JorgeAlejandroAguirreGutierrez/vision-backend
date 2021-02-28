package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import com.proyecto.sicecuador.otros.recaudacion.CompensacionUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "compensacion")
//@EntityListeners({CompensacionUtil.class})
public class Compensacion extends Entidad {
	@JsonProperty("comprobante")
    @Column(name = "comprobante", nullable = true)
    private String comprobante;
	@JsonProperty("fecha_comprobante")
    @Column(name = "fecha_comprobante", nullable = true)
    private Date fechaComprobante;
	@JsonProperty("origen")
    @Column(name = "origen", nullable = true)
    private String origen;
	@JsonProperty("motivo")
    @Column(name = "motivo", nullable = true)
    private String motivo;
	@JsonProperty("fecha_vencimiento")
    @Column(name = "fecha_vencimiento", nullable = true)
    private Date fechaVencimiento;
	@JsonProperty("valor_origen")
    @Column(name = "valor_origen", nullable = true)
    private double valorOrigen;
	@JsonProperty("saldo_anterior")
    @Column(name = "saldo_anterior", nullable = true)
    private double saldoAnterior;
	@JsonProperty("valor_compensado")
    @Column(name = "valor_compensado", nullable = true)
    private double valorCompensado;
	@JsonProperty("saldo")
    @Column(name = "saldo", nullable = true)
    private double saldo;
	@JsonProperty("compensado")
    @Column(name = "compensado", nullable = true)
    private double compensado;
    @ManyToOne
    @JsonProperty("tipo_comprobante")
    @JoinColumn(name = "tipo_comprobante_id", nullable = true)
    private TipoComprobante tipoComprobante;
    @ManyToOne
    @JsonProperty("cliente")
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;
    @ManyToOne
    @JsonProperty("recaudacion")
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public Compensacion(){
        super();
    }

    public Compensacion(long id){
        super(id);
    }
    @JsonBackReference
    public Recaudacion getRecaudacion() {
        return recaudacion;
    }

    public String getComprobante() {
        return comprobante;
    }

    public Date getFechaComprobante() {
		return fechaComprobante;
	}

    public String getOrigen() {
        return origen;
    }

    public String getMotivo() {
        return motivo;
    }

    public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

    public double getValorOrigen() {
		return valorOrigen;
	}

    public double getSaldoAnterior() {
		return saldoAnterior;
	}

    public double getValorCompensado() {
		return valorCompensado;
	}

    public double getSaldo() {
        return saldo;
    }

    public double getCompensado() {
        return compensado;
    }

    public TipoComprobante getTipoComprobante() {
		return tipoComprobante;
	}

    public Cliente getCliente() {
        return cliente;
    }
}
