package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "producto")
public class Producto extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "consignacion", nullable = true)
    private String consignacion;
    @Column(name = "stock_total", nullable = true)
    private double stockTotal;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "categoria_producto_id", nullable = true)
    private CategoriaProducto categoriaProducto;
    @ManyToOne
    @JoinColumn(name = "grupo_producto_id", nullable = true)
    private GrupoProducto grupoProducto;
    @ManyToOne
    @JoinColumn(name = "tipo_gasto_id", nullable = true)
    private TipoGasto tipoGasto;
    @ManyToOne
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @ManyToOne
    @JoinColumn(name = "medida_id", nullable = true)
    private Medida medida;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "producto_id")
    private List<Kardex> kardexs;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "producto_id")
    private List<Precio> precios;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "producto_id")
    private List<ProductoProveedor> productosProveedores;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "producto_id")
    private List<ProductoBodega> productosBodegas;
  
    
    public Producto() {
        super();
        this.nombre = Constantes.vacio;
        this.consignacion = Constantes.si;
        this.stockTotal = Constantes.cero;
        this.estado = Constantes.activo;
        this.categoriaProducto = new CategoriaProducto();
        this.tipoGasto = new TipoGasto();
        this.impuesto = new Impuesto();
        this.grupoProducto = new GrupoProducto();
        this.medida = new Medida();
        this.kardexs = Collections.emptyList();
        this.precios = Collections.emptyList();
        this.productosProveedores = Collections.emptyList();
        this.productosBodegas = Collections.emptyList();
    }

    public Producto(long id) {
        super(id);
    }

    public Producto(String nombre) {
        super(null);
        this.nombre=nombre;
    }

    public Producto(String codigo, String nombre, String consignacion, String estado, TipoGasto tipoGasto,
                    CategoriaProducto categoriaProducto, Impuesto impuesto, GrupoProducto grupoProducto, Medida medida) {
        super(codigo);
        this.nombre = nombre;
        this.consignacion = consignacion;
        this.estado = estado;
        this.tipoGasto = tipoGasto;
        this.categoriaProducto = categoriaProducto;
        this.impuesto = impuesto;
        this.grupoProducto = grupoProducto;
        this.medida = medida;
    }
    public Producto(List<String>datos){
        nombre=datos.get(0)== null ? null: datos.get(0);
        consignacion=datos.get(1)== null ? null: datos.get(1);
        estado=datos.get(2)== null ? null: datos.get(2);
        tipoGasto=datos.get(4)== null ? null: new TipoGasto((long) Double.parseDouble(datos.get(4)));
        categoriaProducto=datos.get(5)== null ? null: new CategoriaProducto((long) Double.parseDouble(datos.get(5)));
        impuesto=datos.get(6)== null ? null: new Impuesto((long) Double.parseDouble(datos.get(6)));
        grupoProducto=datos.get(7)== null ? null: new GrupoProducto((long) Double.parseDouble(datos.get(7)));
        medida=datos.get(8)== null ? null: new Medida((long) Double.parseDouble(datos.get(8)));
        
    }

    public String getNombre() {
        return nombre;
    }

    public String getConsignacion() {
		return consignacion;
	}

    public String getEstado() {
        return estado;
    }

    public TipoGasto getTipoGasto() {
		return tipoGasto;
	}

    public double getStockTotal() {
        return stockTotal;
    }

    public CategoriaProducto getCategoriaProducto() {
		return categoriaProducto;
	}

    public Impuesto getImpuesto() {
        return impuesto;
    }
    
    public GrupoProducto getGrupoProducto() {
		return grupoProducto;
	}
    
    public Medida getMedida() {
		return medida;
	}
    
    public List<Kardex> getKardexs() {
        return kardexs;
    }
    
    @JsonManagedReference
    public List<Precio> getPrecios() {
		return precios;
	}
    @JsonManagedReference
    public List<ProductoProveedor> getProductosProveedores(){
    	return productosProveedores;
    }
    @JsonManagedReference
    public List<ProductoBodega> getProductosBodegas() {
		return productosBodegas;
	}

    public void setEstado(String estado) {
		this.estado = estado;
	}

    public void setMedida(Medida medida) {
		this.medida = medida;
	}

    public void normalizar(){
        if(this.categoriaProducto == null) this.categoriaProducto = new CategoriaProducto();
        if(this.tipoGasto == null) this.tipoGasto = new TipoGasto();
        if(this.impuesto == null) this.impuesto = new Impuesto();
        if(this.grupoProducto == null) this.grupoProducto = new GrupoProducto();
        if(this.medida == null) this.medida = new Medida();
        if(this.kardexs == null) this.kardexs = Collections.emptyList();
        if(this.precios == null) this.precios = Collections.emptyList();
        if(this.productosProveedores == null) this.productosProveedores = Collections.emptyList();
        if(this.productosBodegas == null) this.productosBodegas = Collections.emptyList();
    }
}
