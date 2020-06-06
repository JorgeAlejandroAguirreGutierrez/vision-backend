package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.otros.inventario.KardexUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "kardex")
@EntityListeners({KardexUtil.class})
public class Kardex extends Entidad {
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "documento", nullable = true)
    private String documento;
    @Column(name = "numero", nullable = true)
    private String numero;
    @Column(name = "operacion", nullable = true)
    private String operacion;
    @Column(name = "entrada", nullable = true)
    private double entrada;
    @Column(name = "salida", nullable = true)
    private double salida;
    @Column(name = "saldo", nullable = true)
    private double saldo;
    @Column(name = "debe", nullable = true)
    private double debe;
    @Column(name = "haber", nullable = true)
    private double haber;
    @Column(name = "costo_promedio", nullable = true)
    private double costo_promedio;
    @Column(name = "costo_ultimo", nullable = true)
    private double costo_ultimo;
    @Column(name = "total", nullable = true)
    private double total;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "guia_remision_id", nullable = true)
    private GuiaRemision guia_remision;

    public Kardex(){
        super();
    }

    public Kardex(long id){
        super(id);
    }

    public Kardex(String codigo, Date fecha, String documento, String numero, String operacion, double entrada, double salida,
                  double saldo, double debe, double haber, double costo_promedio,double costo_ultimo, double total, Proveedor proveedor,
                  Cliente cliente, GuiaRemision guia_remision){
        super(codigo);
        this.fecha=fecha;
        this.documento=documento;
        this.numero=numero;
        this.operacion=operacion;
        this.entrada=entrada;
        this.salida=salida;
        this.saldo=saldo;
        this.debe=debe;
        this.haber=haber;
        this.costo_promedio=costo_promedio;
        this.costo_ultimo=costo_ultimo;
        this.total=total;
        this.proveedor=proveedor;
        this.cliente=cliente;
        this.guia_remision=guia_remision;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNumero() {
        return numero;
    }

    public String getOperacion() {
        return operacion;
    }

    public double getEntrada() {
        return entrada;
    }

    public double getSalida() {
        return salida;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getDebe() {
        return debe;
    }

    public double getHaber() {
        return haber;
    }

    public double getCosto_promedio() {
        return costo_promedio;
    }

    public double getCosto_ultimo() {
        return costo_ultimo;
    }

    public double getTotal() {
        return total;
    }
}
