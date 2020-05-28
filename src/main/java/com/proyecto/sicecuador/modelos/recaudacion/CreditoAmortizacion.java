package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Genero;
import com.proyecto.sicecuador.otros.recaudacion.CreditoAmortizacionUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "credito_amortizacion")
@EntityListeners({CreditoAmortizacionUtil.class})
public class CreditoAmortizacion extends Entidad {
    @Column(name = "secuencia", nullable = true)
    private String secuencia;
    @Column(name = "vencimiento", nullable = true)
    private Date vencimiento;
    @Column(name = "saldo_capital", nullable = true)
    private double saldo_capital;
    @Column(name = "dias", nullable = true)
    private long dias;
    @Column(name = "capital", nullable = true)
    private double capital;
    @Column(name = "interes", nullable = true)
    private double interes;
    @Column(name = "dividendo", nullable = true)
    private double dividendo;
    @Column(name = "seguro", nullable = true)
    private double seguro;
    @Column(name = "otro", nullable = true)
    private double otro;
    @Column(name = "cuota", nullable = true)
    private double cuota;
    @Column(name = "mora", nullable = true)
    private double mora;
    @Column(name = "fecha_abono", nullable = true)
    private Date fecha_abono;
    @Column(name = "abono", nullable = true)
    private double abono;
    @Column(name = "fecha_pago", nullable = true)
    private Date fecha_pago;
    @Column(name = "pago", nullable = true)
    private double pago;
    @Column(name = "capital_reducido", nullable = true)
    private double capital_reducido;
    @Column(name = "estado", nullable = true)
    private boolean estado;
    @ManyToOne
    @JoinColumn(name = "credito_id", nullable = true)
    private Credito credito;
    public CreditoAmortizacion(){

    }
    public CreditoAmortizacion(long id){
        super(id);
    }
}
