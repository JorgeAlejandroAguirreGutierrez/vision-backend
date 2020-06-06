package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
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
    @JoinColumn(name = "grupo_producto_id", nullable = true)
    private GrupoProducto grupo_producto;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "kardex_id", nullable = true)
    private Kardex kardex;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private List<Precio> precios;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
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
                    boolean consignacion, boolean estado, String tipo_gasto, boolean serie_autogenerado, TipoProducto tipo_producto, GrupoProducto grupo_producto,
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
        this.grupo_producto = grupo_producto;
        this.impuesto = impuesto;
        this.kardex=kardex;
        this.stock_total = 0;
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

    public GrupoProducto getGrupo_producto() {
        return grupo_producto;
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
