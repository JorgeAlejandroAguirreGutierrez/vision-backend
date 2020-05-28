package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.otros.inventario.PrecioUtil;

import javax.persistence.*;

@Entity
@Table(name = "precio")
@EntityListeners({PrecioUtil.class})
public class Precio extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "descuento", nullable = true)
    private double descuento;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;

    public Precio(){
        super();
    }

    public Precio(long id){
        super(id);
    }
    public Precio(String codigo, String nombre, double descuento, double valor, Producto producto){
        super(codigo);
        this.nombre=nombre;
        this.descuento=descuento;
        this.valor=valor;
        this.producto=producto;
    }
    public String getNombre() {
        return nombre;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getValor() {
        return valor;
    }
    @JsonBackReference
    public Producto getProducto() {
        return producto;
    }
}
