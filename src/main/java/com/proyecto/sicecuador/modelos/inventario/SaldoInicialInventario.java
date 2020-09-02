package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "saldo_inicial_inventario")
public class SaldoInicialInventario extends Entidad {

    @Column(name = "cantidad", nullable = true)
    private long cantidad;
    @Column(name = "costo_unitario", nullable = true)
    private double costo_unitario;
    @Column(name = "costo_total", nullable = true)
    private double costo_total;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;

    public SaldoInicialInventario(){
    }
    public SaldoInicialInventario(long id){
        super(id);
    }
    public SaldoInicialInventario(String codigo){
        super(codigo);
    }
    public SaldoInicialInventario(String codigo, long cantidad, double costo_unitario, double costo_total, Producto producto){
        super(codigo);
        this.cantidad=cantidad;
        this.costo_unitario=costo_unitario;
        this.costo_total=costo_total;
        this.producto=producto;
    }

    public SaldoInicialInventario(List<String> datos){
        cantidad=datos.get(0)== null ? null: (long) Double.parseDouble(datos.get(0));
        costo_unitario=datos.get(1)== null ? null: Double.parseDouble(datos.get(1));
        costo_total=datos.get(2)== null ? null: Double.parseDouble(datos.get(2));
    }

    public long getCantidad() {
        return cantidad;
    }

    public double getCosto_unitario() {
        return costo_unitario;
    }

    public double getCosto_total() {
        return costo_total;
    }

    public Producto getProducto() {
        return producto;
    }
}
