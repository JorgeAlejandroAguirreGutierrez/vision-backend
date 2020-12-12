package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.recaudacion.TarjetaDebitoUtil;

import javax.persistence.*;

@Entity
@Table(name = "tarjeta_debito")
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
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @ManyToOne
    @JoinColumn(name = "operador_tarjeta_id", nullable = true)
    private OperadorTarjeta operador_tarjeta;
    @ManyToOne
    @JoinColumn(name = "franquicia_tarjeta_id", nullable = true)
    private FranquiciaTarjeta franquicia_tarjeta;
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public TarjetaDebito(){
    }

    public TarjetaDebito(long id){
        super(id);
    }

    public TarjetaDebito(String codigo, String identificacion, String nombre_titular, String lote, double valor, Banco banco, OperadorTarjeta operador_tarjeta, FranquiciaTarjeta franquicia_tarjeta, Recaudacion recaudacion){
        super(codigo);
        this.identificacion=identificacion;
        this.nombre_titular=nombre_titular;
        this.lote=lote;
        this.valor=valor;
        this.banco=banco;
        this.operador_tarjeta=operador_tarjeta;
        this.franquicia_tarjeta=franquicia_tarjeta;
        this.recaudacion= recaudacion;
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

    public OperadorTarjeta getOperador_tarjeta() {
        return operador_tarjeta;
    }
    @JsonBackReference
    public Recaudacion getRecaudacion() {
        return recaudacion;
    }

    public FranquiciaTarjeta getFranquicia_tarjeta() {
        return franquicia_tarjeta;
    }

    public Banco getBanco() {
        return banco;
    }
}
