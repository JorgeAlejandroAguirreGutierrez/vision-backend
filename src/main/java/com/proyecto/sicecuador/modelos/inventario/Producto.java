package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.entrega.Transportista;
import com.proyecto.sicecuador.otros.inventario.ProductoUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "producto")
@EntityListeners({ProductoUtil.class})
public class Producto extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "categoria", nullable = true)
    private String categoria;
    @Column(name = "linea", nullable = true)
    private String linea;
    @Column(name = "sublinea", nullable = true)
    private String sublinea;
    @Column(name = "presentacion", nullable = true)
    private String presentacion;
    @Column(name = "costo", nullable = true)
    private double costo;
    @Column(name = "consignacion", nullable = true)
    private boolean consignacion;
    @Column(name = "estado", nullable = true)
    private boolean estado;
    @Column(name = "tipo_gasto", nullable = true)
    private String tipo_gasto;
    @Column(name = "serie_autogenerado", nullable = true)
    private boolean serie_autogenerado;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tipo_producto_id", nullable = true)
    private TipoProducto tipo_producto;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "presentacion_producto_id", nullable = true)
    private PresentacionProducto presentacion_producto;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "kardex_id", nullable = true)
    private Kardex kardex;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producto_id")
    private List<Precio> precios;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producto_id")
    private List<Caracteristica> caracteristicas;
    @Transient
    private long stock_total;

    public Producto() {
        super();
    }

    public Producto(long id) {
        super(id);
    }

    public Producto(String codigo, String nombre, String categoria, String linea, String sublinea, String presentacion, double costo,
                    boolean consignacion, boolean estado, String tipo_gasto, boolean serie_autogenerado, TipoProducto tipo_producto, PresentacionProducto presentacion_producto,
                    Impuesto impuesto, Kardex kardex) {
        super(codigo);
        this.nombre = nombre;
        this.categoria = categoria;
        this.linea = linea;
        this.sublinea = sublinea;
        this.presentacion = presentacion;
        this.costo = costo;
        this.consignacion = consignacion;
        this.estado = estado;
        this.tipo_gasto = tipo_gasto;
        this.serie_autogenerado = serie_autogenerado;
        this.tipo_producto = tipo_producto;
        this.presentacion_producto = presentacion_producto;
        this.impuesto = impuesto;
        this.kardex=kardex;
        this.stock_total = 0;
    }
    public Producto(List<String>datos){
        nombre=datos.get(0)== null ? null: datos.get(0);
        categoria=datos.get(1)== null ? null: datos.get(1);
        linea=datos.get(2)== null ? null: datos.get(2);
        sublinea=datos.get(3)== null ? null: datos.get(3);
        presentacion=datos.get(4)== null ? null: datos.get(4);
        costo=datos.get(5)== null ? null: Double.parseDouble(datos.get(5));
        consignacion=datos.get(6)== null ? null: datos.get(6).equals("S") ? true : false;
        estado=datos.get(7)== null ? null: datos.get(7).equals("S") ? true : false;
        tipo_gasto=datos.get(8)== null ? null: datos.get(8);
        serie_autogenerado=datos.get(9)== null ? null: datos.get(9).equals("S") ? true : false;
        tipo_producto=datos.get(10)== null ? null: new TipoProducto((long) Double.parseDouble(datos.get(10)));
        presentacion_producto=datos.get(11)== null ? null: new PresentacionProducto((long) Double.parseDouble(datos.get(11)));
        impuesto=datos.get(12)== null ? null: new Impuesto((long) Double.parseDouble(datos.get(12)));
        kardex=datos.get(13)== null ? null: new Kardex((long) Double.parseDouble(datos.get(13)));
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getLinea() {
        return linea;
    }

    public String getSublinea() {
        return sublinea;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public double getCosto() {
        return costo;
    }

    public boolean isConsignacion() {
        return consignacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public String getTipo_gasto() {
        return tipo_gasto;
    }

    public boolean isSerie_autogenerado() {
        return serie_autogenerado;
    }

    public void setSerie_autogenerado(boolean serie_autogenerado) {
        this.serie_autogenerado = serie_autogenerado;
    }

    public TipoProducto getTipo_producto() {
        return tipo_producto;
    }

    public PresentacionProducto getPresentacion_producto() {
        return presentacion_producto;
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public Kardex getKardex() {
        return kardex;
    }

    @JsonManagedReference
    public List<Precio> getPrecios() {
        return precios;
    }

    @JsonManagedReference
    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public long getStock_total() {
        return stock_total;
    }

    public void setStock_total(long stock_total) {
        this.stock_total = stock_total;
    }
}
