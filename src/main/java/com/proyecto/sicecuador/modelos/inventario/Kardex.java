package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.proveedor.Proveedor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "kardex")
public class Kardex extends Entidad {
	@JsonProperty("fecha")
    @Column(name = "fecha", nullable = true)
    private Date fecha;
	@JsonProperty("documento")
    @Column(name = "documento", nullable = true)
    private String documento;
	@JsonProperty("numero")
    @Column(name = "numero", nullable = true)
    private String numero;
	@JsonProperty("operacion")
    @Column(name = "operacion", nullable = true)
    private String operacion;
	@JsonProperty("entrada")
    @Column(name = "entrada", nullable = true)
    private double entrada;
	@JsonProperty("salida")
    @Column(name = "salida", nullable = true)
    private long salida;
	@JsonProperty("debe")
    @Column(name = "debe", nullable = true)
    private double debe;
	@JsonProperty("haber")
    @Column(name = "haber", nullable = true)
    private double haber;
	@JsonProperty("cantidad")
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
	@JsonProperty("costo_promedio")
    @Column(name = "costo_promedio", nullable = true)
    private double costoPromedio;
	@JsonProperty("costo_total")
    @Column(name = "costo_total", nullable = true)
    private double costoTotal;
    @ManyToOne
    @JsonProperty("proveedor")
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;
    @JsonBackReference
    @ManyToOne
    @JsonProperty("producto")
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;

    public Kardex(){
        super();
    }

    public Kardex(long id){
        super(id);
    }

    public Kardex(String codigo, Date fecha, String documento, String numero, String operacion, double entrada, long salida,
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
//        this.costoUnitario=costoUnitario;
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

    public long getSalida() {
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

    public Producto getProducto() {
        return producto;
    }

    public void setSalida(long salida) {
        this.salida = salida;
    }

    public void normalizar(){
        if (proveedor.getId()==0){
            proveedor=null;
        }
    }
}
