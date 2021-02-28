package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.DetalleFactura;
import com.proyecto.sicecuador.otros.inventario.CaracteristicaUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "caracteristica")
@EntityListeners({CaracteristicaUtil.class})
public class Caracteristica extends Entidad {
	@JsonProperty("descripcion")
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
	@JsonProperty("color")
    @Column(name = "color", nullable = true)
    private String color;
	@JsonProperty("marca")
    @Column(name = "marca", nullable = true)
    private String marca;
	@JsonProperty("modelo")
    @Column(name = "modelo", nullable = true)
    private String modelo;
	@JsonProperty("serie")
    @Column(name = "serie", nullable = true)
    private String serie;
    @ManyToOne
    @JsonProperty("producto")
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne
    @JsonProperty("detalle_factura")
    @JoinColumn(name = "detalle_factura_id", nullable = true)
    private DetalleFactura detalleFactura;
    @ManyToOne
    @JsonProperty("bodega")
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
        this.detalleFactura=null;
    }
    public Caracteristica(List<String> datos){
        descripcion=datos.get(0)== null ? null: datos.get(0);
        color=datos.get(1)== null ? null: datos.get(1);
        marca=datos.get(2)== null ? null: datos.get(2);
        modelo=datos.get(3)== null ? null: datos.get(3);
        serie=datos.get(4)== null ? null: datos.get(4);
        producto=datos.get(5)== null ? null: new Producto((long) Double.parseDouble(datos.get(5)));
        detalleFactura=datos.get(6)== null ? null: new DetalleFactura((long) Double.parseDouble(datos.get(6)));
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

    @JsonBackReference(value="detalle-factura-caracteristica")
    public void setDetalleFactura(DetalleFactura detalleFactura) {
		this.detalleFactura = detalleFactura;
	}

    public DetalleFactura getDetalleFactura() {
		return detalleFactura;
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
