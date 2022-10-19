package com.proyecto.sicecuador.modelos.recaudacion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
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
    private String nombreTitular;
    @Column(name = "lote", nullable = true)
    private String lote;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "operador_tarjeta_id", nullable = true)
    private OperadorTarjeta operadorTarjeta;
    @ManyToOne
    @JoinColumn(name = "franquicia_tarjeta_id", nullable = true)
    private FranquiciaTarjeta franquiciaTarjeta;
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public TarjetaCredito(){
    }

    public TarjetaCredito(long id){
        super(id);
    }

    public TarjetaCredito(String codigo, boolean diferido, boolean titular, String identificacion, String nombreTitular, String lote, double valor, OperadorTarjeta operadorTarjeta, FranquiciaTarjeta franquiciaTarjeta, Recaudacion recaudacion){
        super(codigo);
        this.diferido=diferido;
        this.titular=titular;
        this.identificacion=identificacion;
        this.nombreTitular=nombreTitular;
        this.lote=lote;
        this.valor=valor;
        this.operadorTarjeta=operadorTarjeta;
        this.franquiciaTarjeta=franquiciaTarjeta;
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

    public FranquiciaTarjeta getFranquiciaTarjeta() {
		return franquiciaTarjeta;
	}
    
    @JsonBackReference
    public Recaudacion getRecaudacion() {
        return recaudacion;
    }
}
