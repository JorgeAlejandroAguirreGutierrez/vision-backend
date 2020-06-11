package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.usuario.Perfil;
import com.proyecto.sicecuador.otros.recaudacion.ChequeUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cheque")
//@EntityListeners({ChequeUtil.class})
public class Cheque extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "fecha_efectivizacion", nullable = true)
    private Date fecha_efectivizacion;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public Cheque(){

    }

    public Cheque(long id){
        super(id);
    }

    public Cheque(String codigo, String numero, String tipo, Date fecha, Date fecha_efectivizacion, double valor, Recaudacion recaudacion, Banco banco){
        super(codigo);
        this.numero=numero;
        this.tipo=tipo;
        this.fecha=fecha;
        this.fecha_efectivizacion=fecha_efectivizacion;
        this.valor=valor;
        this.recaudacion=recaudacion;
        this.banco=banco;
    }

    public String getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public Date getFecha_efectivizacion() {
        return fecha_efectivizacion;
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


}
