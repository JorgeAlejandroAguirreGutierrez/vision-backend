package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.FacturaDetalle;
import com.proyecto.sicecuador.modelos.entrega.Transportista;
import com.proyecto.sicecuador.otros.inventario.CaracteristicaUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "caracteristica")
@EntityListeners({CaracteristicaUtil.class})
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
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "factura_detalle_id", nullable = true)
    private FacturaDetalle factura_detalle;
    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;

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
        this.factura_detalle=null;
    }
    public Caracteristica(List<String> datos){
        descripcion=datos.get(0)== null ? null: datos.get(0);
        color=datos.get(1)== null ? null: datos.get(1);
        marca=datos.get(2)== null ? null: datos.get(2);
        modelo=datos.get(3)== null ? null: datos.get(3);
        serie=datos.get(4)== null ? null: datos.get(4);
        producto=datos.get(5)== null ? null: new Producto((long) Double.parseDouble(datos.get(5)));
        factura_detalle=datos.get(6)== null ? null: new FacturaDetalle((long) Double.parseDouble(datos.get(6)));
        bodega=datos.get(7)== null ? null: new Bodega((long) Double.parseDouble(datos.get(7)));
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

    @JsonBackReference(value="factura-detalle-caracteristica")
    public void setFactura_detalle(FacturaDetalle factura_detalle) {
        this.factura_detalle = factura_detalle;
    }

    public FacturaDetalle getFactura_detalle() {
        return factura_detalle;
    }

    @JsonBackReference
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Bodega getBodega() {
        return bodega;
    }
}
