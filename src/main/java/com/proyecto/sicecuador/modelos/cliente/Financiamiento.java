package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "financiamiento")
public class Financiamiento extends Entidad {
    @Column(name = "monto", nullable = true)
    private double monto;
    @ManyToOne
    @JoinColumn(name = "tipo_pago_id", nullable = true)
    private TipoPago tipoPago;
    @ManyToOne
    @JoinColumn(name = "forma_pago_id", nullable = true)
    private FormaPago formaPago;
    @ManyToOne
    @JoinColumn(name = "plazo_credito_id", nullable = true)
    private PlazoCredito plazoCredito;

    public Financiamiento(){

    }

    public Financiamiento(long id) {
        super(id);
    }

    public Financiamiento(String codigo, double monto, TipoPago tipoPago, FormaPago formaPago, PlazoCredito plazoCredito ){
        super(codigo);
        this.monto=monto;
        this.tipoPago=tipoPago;
        this.formaPago=formaPago;
        this.plazoCredito=plazoCredito;
    }

    public Financiamiento(List<String> datos) {
        monto=datos.get(0)== null? null : Double.parseDouble(datos.get(0));
        tipoPago=datos.get(1)==null? null: new TipoPago((long) Double.parseDouble(datos.get(1)));
        formaPago=datos.get(2)==null? null: new FormaPago((long) Double.parseDouble(datos.get(2)));
        plazoCredito=datos.get(3)==null? null: new PlazoCredito((long) Double.parseDouble(datos.get(3)));
    }

    public double getMonto() {
        return monto;
    }

    public TipoPago getTipoPago() {
		return tipoPago;
	}

    public FormaPago getFormaPago() {
		return formaPago;
	}

    public PlazoCredito getPlazoCredito() {
		return plazoCredito;
	}

    public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

    public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}

    public void setPlazoCredito(PlazoCredito plazoCredito) {
		this.plazoCredito = plazoCredito;
	}
}
