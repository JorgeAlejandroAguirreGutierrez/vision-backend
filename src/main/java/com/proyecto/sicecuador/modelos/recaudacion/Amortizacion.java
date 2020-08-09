package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.inventario.Kardex;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "amortizacion")
public class Amortizacion extends Entidad {
    @Column(name = "numero_cuota", nullable = true)
    private int numero_cuota;
    @Column(name = "fecha_pago", nullable = true)
    private Date fecha_pago;
    @Column(name = "numero_dias", nullable = true)
    private long numero_dias;
    @Column(name = "capital_inicio_periodo", nullable = true)
    private double capital_inicio_periodo;
    @Column(name = "capital", nullable = true)
    private double capital;
    @Column(name = "intereses_periodo", nullable = true)
    private double intereses_periodo;
    @Column(name = "cuota", nullable = true)
    private double valor_cuota;
    @Column(name = "saldo_capital", nullable = true)
    private double saldo_capital;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "credito_id", nullable = true)
    private Credito credito;

    public Amortizacion(){
    }

    public Amortizacion(long id) {
        super(id);
    }

    public Amortizacion(String codigo, int numero_cuota, Date fecha_pago, long numero_dias, double capital_inicio_periodo,
                        double capital, double intereses_periodo, double valor_cuota, double saldo_capital, Credito credito){
        super(codigo);
        this.numero_cuota=numero_cuota;
        this.fecha_pago=fecha_pago;
        this.numero_dias=numero_dias;
        this.capital_inicio_periodo=capital_inicio_periodo;
        this.capital=capital;
        this.intereses_periodo=intereses_periodo;
        this.valor_cuota=valor_cuota;
        this.saldo_capital=saldo_capital;
        this.credito=credito;
    }

    public Amortizacion(List<String> datos) {
        numero_cuota=datos.get(0)== null ? null: (int)Double.parseDouble(datos.get(0));
        fecha_pago=datos.get(1)== null ? null: new Date(datos.get(1));
        numero_dias=datos.get(2)== null ? null: (long)Double.parseDouble(datos.get(2));
        capital_inicio_periodo=datos.get(3)== null ? null: Double.parseDouble(datos.get(3));
        capital=datos.get(4)== null ? null: Double.parseDouble(datos.get(4));
        intereses_periodo=datos.get(5)== null ? null: Double.parseDouble(datos.get(5));
        valor_cuota=datos.get(6)== null ? null: Double.parseDouble(datos.get(6));
        saldo_capital=datos.get(7)== null ? null: Double.parseDouble(datos.get(7));
        credito=datos.get(8)== null ? null: new Credito((long) Double.parseDouble(datos.get(8)));
    }

    public int getNumero_cuota() {
        return numero_cuota;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public long getNumero_dias() {
        return numero_dias;
    }

    public double getCapital_inicio_periodo() {
        return capital_inicio_periodo;
    }

    public double getCapital() {
        return capital;
    }

    public double getIntereses_periodo() {
        return intereses_periodo;
    }

    public double getValor_cuota() {
        return valor_cuota;
    }

    public double getSaldo_capital() {
        return saldo_capital;
    }
    @JsonBackReference
    public Credito getCredito() {
        return credito;
    }
}
