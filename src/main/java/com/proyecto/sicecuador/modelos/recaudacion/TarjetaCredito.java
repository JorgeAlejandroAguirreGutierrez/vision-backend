package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.recaudacion.TarjetaCreditoUtil;

import javax.persistence.*;

@Entity
@Table(name = "tarjeta_credito")
public class TarjetaCredito extends Entidad {
    @Column(name = "diferido", nullable = true)
    private boolean diferido;
    @Column(name = "titular", nullable = true)
    private boolean titular;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "nombre_titular", nullable = true)
    private String nombre_titular;
    @Column(name = "lote", nullable = true)
    private String lote;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "operador_tarjeta_id", nullable = true)
    private OperadorTarjeta operador_tarjeta;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "franquicia_tarjeta_id", nullable = true)
    private FranquiciaTarjeta franquicia_tarjeta;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public TarjetaCredito(){
    }

    public TarjetaCredito(long id){
        super(id);
    }

    public TarjetaCredito(String codigo, boolean diferido, boolean titular, String identificacion, String nombre_titular, String lote, double valor, OperadorTarjeta operador_tarjeta, FranquiciaTarjeta franquicia_tarjeta, Recaudacion recaudacion){
        super(codigo);
        this.diferido=diferido;
        this.titular=titular;
        this.identificacion=identificacion;
        this.nombre_titular=nombre_titular;
        this.lote=lote;
        this.valor=valor;
        this.operador_tarjeta=operador_tarjeta;
        this.franquicia_tarjeta=franquicia_tarjeta;
        this.recaudacion=recaudacion;
    }
    public boolean isDiferido() {
        return diferido;
    }

    public boolean isTitular() {
        return titular;
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

    public FranquiciaTarjeta getFranquicia_tarjeta() {
        return franquicia_tarjeta;
    }
    @JsonBackReference
    public Recaudacion getRecaudacion() {
        return recaudacion;
    }
}
