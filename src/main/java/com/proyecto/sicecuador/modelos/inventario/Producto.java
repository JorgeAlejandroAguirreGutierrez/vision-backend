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
    @Column(name = "consignacion", nullable = true)
    private boolean consignacion;
    @Column(name = "estado", nullable = true)
    private boolean estado;
    @Column(name = "serie_autogenerado", nullable = true)
    private boolean serie_autogenerado;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tipo_producto_id", nullable = true)
    private TipoProducto tipo_producto;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "presentacion_producto_id", nullable = true)
    private PresentacionProducto presentacion_producto;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tipo_gasto_id", nullable = true)
    private TipoGasto tipo_gasto;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producto_id", nullable = true)
    private List<Kardex> kardexs;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producto_id", nullable = true)
    private List<Precio> precios;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producto_id", nullable = true)
    private List<Caracteristica> caracteristicas;
    @Transient
    private long stock_total;

    public Producto() {
        super();
    }

    public Producto(long id) {
        super(id);
    }

    public Producto(String codigo, String nombre, boolean consignacion, boolean estado, boolean serie_autogenerado, TipoGasto tipo_gasto, TipoProducto tipo_producto, PresentacionProducto presentacion_producto,
                    Impuesto impuesto) {
        super(codigo);
        this.nombre = nombre;
        this.presentacion_producto = presentacion_producto;
        this.consignacion = consignacion;
        this.estado = estado;
        this.serie_autogenerado = serie_autogenerado;
        this.tipo_gasto = tipo_gasto;
        this.tipo_producto = tipo_producto;
        this.presentacion_producto = presentacion_producto;
        this.impuesto = impuesto;
        this.stock_total = 0;
    }
    public Producto(List<String>datos){
        nombre=datos.get(0)== null ? null: datos.get(0);
        consignacion=datos.get(1)== null ? null: datos.get(1).equals("S") ? true : false;
        estado=datos.get(2)== null ? null: datos.get(2).equals("S") ? true : false;
        serie_autogenerado=datos.get(3)== null ? null: datos.get(3).equals("S") ? true : false;
        tipo_producto=datos.get(4)== null ? null: new TipoProducto((long) Double.parseDouble(datos.get(4)));
        presentacion_producto=datos.get(5)== null ? null: new PresentacionProducto((long) Double.parseDouble(datos.get(5)));
        impuesto=datos.get(6)== null ? null: new Impuesto((long) Double.parseDouble(datos.get(6)));
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isConsignacion() {
        return consignacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public TipoGasto getTipo_gasto() {
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

    @JsonManagedReference
    public List<Kardex> getKardexs() {
        return kardexs;
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

    public void normalizar(){
        for(int i=0; i<kardexs.size(); i++){
            if (kardexs.get(i).getProveedor().getId()==0){
                kardexs.get(i).setProveedor(null);
            }
        }
    }
}
