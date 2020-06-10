package com.proyecto.sicecuador.modelos.compra;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.recaudacion.Banco;
import com.proyecto.sicecuador.modelos.recaudacion.Recaudacion;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

public class RetencionCompra extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "fecha_cheque", nullable = true)
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

    public RetencionCompra(){

    }

    public RetencionCompra(long id){
        super(id);
    }

    public RetencionCompra(String codigo, String numero, String tipo, Date fecha, Date fecha_efectivizacion, double valor, Recaudacion recaudacion, Banco banco){
        super(codigo);
        this.numero=numero;
        this.tipo=tipo;
        this.fecha=fecha;
        this.fecha_efectivizacion=fecha_efectivizacion;
        this.valor=valor;
        this.recaudacion=recaudacion;
        this.banco=banco;
    }
    @JsonBackReference
    public Recaudacion getRecaudacion() {
        return recaudacion;
    }
}
