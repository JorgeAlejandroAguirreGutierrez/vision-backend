package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "caracteristica")
public class Caracteristica extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "color", nullable = true)
    private String color;
    @Column(name = "marca", nullable = true)
    private String marca;
    @Column(name = "modelo", nullable = true)
    private String modelo;
    @Column(name = "serie", nullable = true)
    private String serie;
    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;

    public Caracteristica(){

    }

    public Caracteristica(long id){
        super(id);
    }

    public Caracteristica(String codigo, String descripcion, String color, String marca, String modelo, String serie, Producto producto, Bodega bodega) {
        super(codigo);
        this.descripcion = descripcion;
        this.color = color;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.producto=producto;
        this.bodega=bodega;
    }
    public Caracteristica(List<String> datos){
        descripcion=datos.get(0)== null ? null: datos.get(0);
        color=datos.get(1)== null ? null: datos.get(1);
        marca=datos.get(2)== null ? null: datos.get(2);
        modelo=datos.get(3)== null ? null: datos.get(3);
        serie=datos.get(4)== null ? null: datos.get(4);
        producto=datos.get(5)== null ? null: new Producto(Long.parseLong(datos.get(5)));
        bodega=datos.get(6)== null ? null: new Bodega(Long.parseLong(datos.get(6)));
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getColor() {
        return color;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getSerie() {
        return serie;
    }

    @JsonBackReference
    public Producto getProducto() {
        return producto;
    }

    public Bodega getBodega() {
        return bodega;
    }
}
