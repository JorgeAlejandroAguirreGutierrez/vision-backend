package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.otros.cliente.ClienteUtil;
import com.proyecto.sicecuador.otros.cliente.FinanciamientoUtil;

import javax.persistence.*;

@Entity
@Table(name = "financiamiento")
public class Financiamiento extends Entidad {
    @Column(name = "monto", nullable = true)
    private double monto;
    @ManyToOne(cascade = CascadeType.MERGE, optional = true)
    @JoinColumn(name = "tipo_pago_id", nullable = true)
    private TipoPago tipo_pago;
    @ManyToOne(cascade = CascadeType.MERGE, optional = true)
    @JoinColumn(name = "forma_pago_id", nullable = true)
    private FormaPago forma_pago;
    @ManyToOne(cascade = CascadeType.MERGE, optional = true)
    @JoinColumn(name = "plazo_credito_id", nullable = true)
    private PlazoCredito plazo_credito;

    public Financiamiento(){

    }

    public Financiamiento(long id) {
        super(id);
    }

    public Financiamiento(String codigo, double monto, TipoPago tipo_pago, FormaPago forma_pago, PlazoCredito plazo_credito ){
        super(codigo);
        this.monto=monto;
        this.tipo_pago=tipo_pago;
        this.forma_pago=forma_pago;
        this.plazo_credito=plazo_credito;
    }

    public double getMonto() {
        return monto;
    }

    public TipoPago getTipo_pago() {
        return tipo_pago;
    }

    public FormaPago getForma_pago() {
        return forma_pago;
    }

    public PlazoCredito getPlazo_credito() {
        return plazo_credito;
    }

    public void setForma_pago(FormaPago forma_pago) {
        this.forma_pago = forma_pago;
    }

    public void setTipo_pago(TipoPago tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public void setPlazo_credito(PlazoCredito plazo_credito) {
        this.plazo_credito = plazo_credito;
    }
}
