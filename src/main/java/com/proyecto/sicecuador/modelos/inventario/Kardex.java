package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.proveedor.Proveedor;

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
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
    @Column(name = "costo_promedio", nullable = true)
    private double costoPromedio;
    @Column(name = "costo_total", nullable = true)
    private double costoTotal;
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;

    public Kardex(){
        super();
        this.fecha = new Date();
        this.documento = Constantes.vacio;
        this.numero = Constantes.vacio;
        this.operacion = Constantes.vacio;
        this.entrada = Constantes.cero;
        this.salida = Constantes.cero;
        this.debe = Constantes.cero;
        this.haber = Constantes.cero;
        this.cantidad = Constantes.ceroId;
        this.costoPromedio = Constantes.cero;
        this.costoTotal = Constantes.cero;
        this.proveedor = new Proveedor();
    }

    public Kardex(long id){
        super(id);
    }

    public Kardex(String codigo, Date fecha, String documento, String numero, String operacion, double entrada, double salida,
                  double debe, double haber, long cantidad, double costoPromedio,
                  double costoTotal, Proveedor proveedor,Producto producto){
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
        this.costoPromedio=costoPromedio;
        this.costoTotal=costoTotal;
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

    public double getCostoPromedio() {
		return costoPromedio;
	}
    
    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public long getCantidad() {
        return cantidad;
    }

    public double getCostoTotal() {
		return costoTotal;
	}

    @JsonBackReference
    public Producto getProducto() {
        return producto;
    }

    public void setSalida(double salida) {
        this.salida = salida;
    }

    public void normalizar(){
        if(this.proveedor == null) this.proveedor = new Proveedor();
    }
}
