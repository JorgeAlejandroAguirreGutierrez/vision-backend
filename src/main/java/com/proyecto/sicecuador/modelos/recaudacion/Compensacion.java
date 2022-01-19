package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "compensacion")
public class Compensacion extends Entidad {
    @Column(name = "comprobante", nullable = true)
    private String comprobante;
    @Column(name = "fecha_comprobante", nullable = true)
    private Date fechaComprobante;
    @Column(name = "origen", nullable = true)
    private String origen;
    @Column(name = "motivo", nullable = true)
    private String motivo;
    @Column(name = "fecha_vencimiento", nullable = true)
    private Date fechaVencimiento;
    @Column(name = "valor_origen", nullable = true)
    private double valorOrigen;
    @Column(name = "saldo_anterior", nullable = true)
    private double saldoAnterior;
    @Column(name = "valor_compensado", nullable = true)
    private double valorCompensado;
    @Column(name = "saldo", nullable = true)
    private double saldo;
    @Column(name = "compensado", nullable = true)
    private double compensado;
    @ManyToOne
    @JoinColumn(name = "tipo_comprobante_id", nullable = true)
    private TipoComprobante tipoComprobante;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;
    @ManyToOne
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
