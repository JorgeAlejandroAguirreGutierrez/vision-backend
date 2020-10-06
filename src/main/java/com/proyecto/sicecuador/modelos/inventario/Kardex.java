package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.otros.inventario.KardexUtil;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "kardex")
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
    @Column(name = "debe", nullable = true)
    private double debe;
    @Column(name = "haber", nullable = true)
    private double haber;
    @Column(name = "long", nullable = true)
    private long cantidad;
    @Column(name = "costo_unitario", nullable = true)
    private double costo_unitario;
    @Column(name = "costo_promedio", nullable = true)
    private double costo_promedio;
    @Column(name = "costo_total", nullable = true)
    private double costo_total;
    @ManyToOne(cascade = CascadeType.MERGE, optional = true)
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;

    public Kardex(){
        super();
    }

    public Kardex(long id){
        super(id);
    }

    public Kardex(String codigo, Date fecha, String documento, String numero, String operacion, double entrada, double salida,
                  double debe, double haber, long cantidad, double costo_unitario, double costo_promedio,double costo_total,
                  Proveedor proveedor,Producto producto){
        super(codigo);
        this.fecha=fecha;
        this.documento=documento;
        this.numero=numero;
        this.operacion=operacion;
        this.entrada=entrada;
        this.salida=salida;
        this.debe=debe;
        this.haber=haber;
        this.cantidad=cantidad;
        this.costo_unitario=costo_unitario;
        this.costo_promedio=costo_promedio;
        this.costo_total=costo_total;
        this.proveedor=proveedor;
        this.producto=producto;
    }

    public Kardex(List<String> datos){

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

    public double getDebe() {
        return debe;
    }

    public double getHaber() {
        return haber;
    }

    public double getCosto_promedio() {
        return costo_promedio;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public double getCosto_unitario() {
        return costo_unitario;
    }

    public long getCantidad() {
        return cantidad;
    }

    public double getCosto_total() {
        return costo_total;
    }
    @JsonBackReference
    public Producto getProducto() {
        return producto;
    }

    public void normalizar(){
        if (proveedor.getId()==0){
            proveedor=null;
        }
    }
}
