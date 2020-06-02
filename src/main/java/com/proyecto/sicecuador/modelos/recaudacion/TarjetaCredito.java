package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.recaudacion.TarjetaCreditoUtil;

import javax.persistence.*;

@Entity
@Table(name = "tarjeta_credito")
@EntityListeners({TarjetaCreditoUtil.class})
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
    @ManyToOne
    @JoinColumn(name = "tarjeta_id", nullable = true)
    private Tarjeta tarjeta;
    @ManyToOne
    @JoinColumn(name = "operador_tarjeta_id", nullable = true)
    private OperadorTarjeta operador_tarjeta;
    @ManyToOne
    @JoinColumn(name = "franquicia_tarjeta_id", nullable = true)
    private FranquiciaTarjeta franquicia_tarjeta;

    public TarjetaCredito(){
    }

    public TarjetaCredito(long id){
        super(id);
    }

    public TarjetaCredito(String codigo, boolean diferido, boolean titular, String identificacion, String nombre_titular, String lote, double valor, Tarjeta tarjeta, OperadorTarjeta operador_tarjeta){
        super(codigo);
        this.diferido=diferido;
        this.titular=titular;
        this.identificacion=identificacion;
        this.nombre_titular=nombre_titular;
        this.lote=lote;
        this.valor=valor;
        this.tarjeta=tarjeta;
        this.operador_tarjeta=operador_tarjeta;
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

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public OperadorTarjeta getOperador_tarjeta() {
        return operador_tarjeta;
    }

    public FranquiciaTarjeta getFranquicia_tarjeta() {
        return franquicia_tarjeta;
    }
}
