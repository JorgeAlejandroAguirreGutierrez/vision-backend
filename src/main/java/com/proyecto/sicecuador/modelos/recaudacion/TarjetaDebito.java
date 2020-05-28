package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.recaudacion.TarjetaDebitoUtil;

import javax.persistence.*;

@Entity
@Table(name = "tarjeta_debito")
@EntityListeners({TarjetaDebitoUtil.class})
public class TarjetaDebito extends Entidad {
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "nombre_titular", nullable = true)
    private String nombre_titular;
    @Column(name = "lote", nullable = true)
    private String lote;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "tarjeta_id", nullable = true)
    private Tarjeta tarjeta;
    @ManyToOne
    @JoinColumn(name = "operador_tarjeta_id", nullable = true)
    private OperadorTarjeta operador_tarjeta;

    public TarjetaDebito(){
    }

    public TarjetaDebito(long id){
        super(id);
    }

    public TarjetaDebito(String codigo, String identificacion, String nombre_titular, String lote, double valor, Tarjeta tarjeta, OperadorTarjeta operador_tarjeta){
        super(codigo);
        this.identificacion=identificacion;
        this.nombre_titular=nombre_titular;
        this.lote=lote;
        this.valor=valor;
        this.tarjeta=tarjeta;
        this.operador_tarjeta=operador_tarjeta;
    }
    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombre_titular() {
        return nombre_titular;
    }

    public String getLote() {
        return lote;
    }

    public double getValor() {
        return valor;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public OperadorTarjeta getOperador_tarjeta() {
        return operador_tarjeta;
    }
}
