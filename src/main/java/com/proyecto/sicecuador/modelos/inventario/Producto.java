package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "producto")
public class Producto extends Entidad {
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
	@JsonProperty("consignacion")
    @Column(name = "consignacion", nullable = true)
    private boolean consignacion;
	@JsonProperty("estado")
    @Column(name = "estado", nullable = true)
    private String estado;
	@JsonProperty("stock_total")
    @Column(name = "stock_total", nullable = true)
    private double stockTotal;
		
	@JsonProperty("serie_autogenerado")
    @Column(name = "serie_autogenerado", nullable = true)
    private boolean serieAutogenerado;
    @ManyToOne
    @JsonProperty("categoria_producto")
    @JoinColumn(name = "categoria_producto_id", nullable = true)
    private CategoriaProducto categoriaProducto;
    @ManyToOne
    @JsonProperty("tipo_gasto")
    @JoinColumn(name = "tipo_gasto_id", nullable = true)
    private TipoGasto tipoGasto;
    @ManyToOne
    @JsonProperty("impuesto")
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @ManyToOne
    @JsonProperty("grupo_producto")
    @JoinColumn(name = "grupo_producto_id", nullable = true)
    private GrupoProducto grupoProducto;
    
    @ManyToOne
    @JsonProperty("medida_kardex")
    @JoinColumn(name = "medida_kardex_id", nullable = true)
    private Medida medidaKardex;
    
    @ManyToOne
    @JsonProperty("bodega")
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;
    
//    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("kardexs")
    @JoinColumn(name = "producto_id", nullable = true)
    private List<Kardex> kardexs;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("caracteristicas")
    @JoinColumn(name = "producto_id", nullable = true)
    private List<Caracteristica> caracteristicas;
    //crear precios a partir de productos
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("precios")
    @JoinColumn(name = "producto_id", nullable = true)
    private List<Precio> precios;
    @JsonManagedReference    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("productos_proveedores")
    @JoinColumn(name = "producto_id", nullable = true)
    private List<ProductoProveedor> productosProveedores;
 /*   @JsonManagedReference    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("productos_bodegas")
    @JoinColumn(name = "producto_id", nullable = true)
    private List<ProductoBodega> productosBodegas;
*/    
    
    public Producto() {
        super();
    }

    public Producto(long id) {
        super(id);
    }

    public Producto(String nombre) {
        super(null);
        this.nombre=nombre;
    }

    public Producto(String codigo, String nombre, boolean consignacion, String estado,
                    boolean serieAutogenerado, TipoGasto tipoGasto,
                    CategoriaProducto categoriaProducto, Impuesto impuesto, GrupoProducto grupoProducto, Medida medidaKardex, Bodega bodega) {
        super(codigo);
        this.nombre = nombre;
        this.consignacion = consignacion;
        this.estado = estado;
        this.serieAutogenerado = serieAutogenerado;
        this.tipoGasto = tipoGasto;
        this.categoriaProducto = categoriaProducto;
        this.impuesto = impuesto;
        this.grupoProducto = grupoProducto;
        this.medidaKardex = medidaKardex;
        this.bodega = bodega;
    }
    public Producto(List<String>datos){
        nombre=datos.get(0)== null ? null: datos.get(0);
        consignacion=datos.get(1)== null ? null: datos.get(1).equals("S") ? true : false;
        estado=datos.get(2)== null ? null: datos.get(2);
        serieAutogenerado=datos.get(3)== null ? null: datos.get(3).equals("S") ? true : false;
        tipoGasto=datos.get(4)== null ? null: new TipoGasto((long) Double.parseDouble(datos.get(4)));
        categoriaProducto=datos.get(5)== null ? null: new CategoriaProducto((long) Double.parseDouble(datos.get(5)));
        impuesto=datos.get(6)== null ? null: new Impuesto((long) Double.parseDouble(datos.get(6)));
        grupoProducto=datos.get(7)== null ? null: new GrupoProducto((long) Double.parseDouble(datos.get(7)));
        medidaKardex=datos.get(8)== null ? null: new Medida((long) Double.parseDouble(datos.get(8)));
        bodega=datos.get(9)== null ? null: new Bodega((long) Double.parseDouble(datos.get(9)));
        
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isConsignacion() {
        return consignacion;
    }

    public String getEstado() {
        return estado;
    }

    public TipoGasto getTipoGasto() {
		return tipoGasto;
	}

    public boolean isSerieAutogenerado() {
		return serieAutogenerado;
	}

    public void setSerieAutogenerado(boolean serieAutogenerado) {
		this.serieAutogenerado = serieAutogenerado;
	}

    public void setMedidaKardex(Medida medidaKardex) {
    	this.medidaKardex = medidaKardex;
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
    
    public Medida getMedidaKardex() {
    	return medidaKardex;
    }
    
    public Bodega getBodega() {
		return bodega;
	}

    public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}
    
    public List<Kardex> getKardexs() {
        return kardexs;
    }

    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }
    
    public List<Precio> getPrecios() {
		return precios;
	}
    
    public List<ProductoProveedor> getProductosProveedores(){
    	return productosProveedores;
    	
    }

    public void setProductosProveedores(List<ProductoProveedor> productosProveedores) {
    	this.productosProveedores = productosProveedores;
    }
    
 /*   public List<ProductoBodega> getProductosBodegas() {
		return productosBodegas;
	}
    
    public void setProductosBodegas(List<ProductoBodega> productosBodegas) {
		this.productosBodegas = productosBodegas;
	}
    */
    public void normalizar(){
        for(int i=0; i<kardexs.size(); i++){
            if (kardexs.get(i).getProveedor().getId()==0){
                kardexs.get(i).setProveedor(null);
            }
        }
    }
}
