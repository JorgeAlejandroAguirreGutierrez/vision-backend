package com.proyecto.sicecuador.modelos.recaudacion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transferencia")
public class Transferencia extends Entidad {
    @Column(name = "tipo_transaccion", nullable = true)
    private String tipoTransaccion;
    @Column(name = "numero_transaccion", nullable = true)
    private String numeroTransaccion;
    @Column(name = "fecha_transaccion", nullable = true)
    private Date fechaTransaccion;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public Transferencia(){
        super();
        this.tipoTransaccion = Constantes.vacio;
        this.numeroTransaccion = Constantes.vacio;
        this.fechaTransaccion = new Date();
        this.valor = Constantes.cero;
        this.banco = new Banco();
    }

    public Transferencia(long id){
        super(id);
    }

    public Transferencia(String codigo, String tipoTransaccion, String numeroTransaccion, Date fechaTransaccion, double valor, Recaudacion recaudacion, Banco banco){
        super(codigo);
        this.tipoTransaccion=tipoTransaccion;
        this.numeroTransaccion=numeroTransaccion;
        this.fechaTransaccion=fechaTransaccion;
        this.valor=valor;
        this.recaudacion=recaudacion;
        this.banco=banco;
    }
    
    public String getTipoTransaccion() {
		return tipoTransaccion;
	}

    public String getNumeroTransaccion() {
		return numeroTransaccion;
	}

    public Date getFechaTransaccion() {
		return fechaTransaccion;
	}

    public double getValor() {
        return valor;
    }

    public Banco getBanco() {
        return banco;
    }
    
    @JsonBackReference
    public Recaudacion getRecaudacion() {
        return recaudacion;
    }

    public void normalizar(){
        if(this.fechaTransaccion == null) this.fechaTransaccion = new Date();
        if(this.banco == null) this.banco = new Banco();
    }
}
