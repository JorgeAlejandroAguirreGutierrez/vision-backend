package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;

@Entity
@Table(name = "tarjeta_debito")
public class TarjetaDebito extends Entidad {
	@Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "nombre_titular", nullable = true)
    private String nombreTitular;
    @Column(name = "lote", nullable = true)
    private String lote;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @ManyToOne
    @JoinColumn(name = "operador_tarjeta_id", nullable = true)
    private OperadorTarjeta operadorTarjeta;
    @ManyToOne
    @JoinColumn(name = "franquicia_tarjeta_id", nullable = true)
    private FranquiciaTarjeta franquiciaTarjeta;
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public TarjetaDebito(){
        super();
        this.identificacion = Constantes.vacio;
        this.nombreTitular = Constantes.vacio;
        this.lote = Constantes.vacio;
        this.valor = Constantes.cero;
        this.banco = new Banco();
        this.operadorTarjeta = new OperadorTarjeta();
        this.franquiciaTarjeta = new FranquiciaTarjeta();
    }

    public TarjetaDebito(long id){
        super(id);
    }

    public TarjetaDebito(String codigo, String identificacion, String nombreTitular, String lote, double valor, Banco banco, OperadorTarjeta operadorTarjeta, FranquiciaTarjeta franquiciaTarjeta, Recaudacion recaudacion){
        super(codigo);
        this.identificacion=identificacion;
        this.nombreTitular=nombreTitular;
        this.lote=lote;
        this.valor=valor;
        this.banco=banco;
        this.operadorTarjeta=operadorTarjeta;
        this.franquiciaTarjeta=franquiciaTarjeta;
    }
    
    public String getIdentificacion() {
        return identificacion;
    }

    public String getNombreTitular() {
		return nombreTitular;
	}

    public String getLote() {
        return lote;
    }

    public double getValor() {
        return valor;
    }

    public OperadorTarjeta getOperadorTarjeta() {
		return operadorTarjeta;
	}
    
    @JsonBackReference
    public Recaudacion getRecaudacion() {
        return recaudacion;
    }

    public FranquiciaTarjeta getFranquiciaTarjeta() {
		return franquiciaTarjeta;
	}

    public Banco getBanco() {
        return banco;
    }

    public void normalizar(){
        if(this.banco == null) this.banco = new Banco();
        if(this.operadorTarjeta == null) this.operadorTarjeta = new OperadorTarjeta();
        if(this.franquiciaTarjeta == null) this.franquiciaTarjeta = new FranquiciaTarjeta();
    }
}
