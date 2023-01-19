package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
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
    @Column(name = "serie_autogenerado", nullable = true)
    private String serieAutogenerado;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "categoria_producto_id", nullable = true)
    private CategoriaProducto categoriaProducto;
    @ManyToOne
    @JoinColumn(name = "tipo_gasto_id", nullable = true)
    private TipoGasto tipoGasto;
    @ManyToOne
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @ManyToOne
    @JoinColumn(name = "grupo_producto_id", nullable = true)
    private GrupoProducto grupoProducto; 
    @ManyToOne
    @JoinColumn(name = "medida_id", nullable = true)
    private Medida medida;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "producto_id", nullable = true)
    private List<Kardex> kardexs;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "producto_id", nullable = true)
    private List<Caracteristica> caracteristicas;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "producto_id", nullable = true)
    private List<Precio> precios;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "producto_id", nullable = true)
    private List<ProductoProveedor> productosProveedores;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "producto_id", nullable = true)
    private List<ProductoBodega> productosBodegas;
  
    
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

    public Producto(String codigo, String nombre, String consignacion, String estado,
                    String serieAutogenerado, TipoGasto tipoGasto,
                    CategoriaProducto categoriaProducto, Impuesto impuesto, GrupoProducto grupoProducto, Medida medida) {
        super(codigo);
        this.nombre = nombre;
        this.consignacion = consignacion;
        this.estado = estado;
        this.serieAutogenerado = serieAutogenerado;
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
        serieAutogenerado=datos.get(3)== null ? null: datos.get(3);
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

    public String getSerieAutogenerado() {
		return serieAutogenerado;
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
    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
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
    
    public void setSerieAutogenerado(String serieAutogenerado) {
		this.serieAutogenerado = serieAutogenerado;
	}

    public void setMedida(Medida medida) {
		this.medida = medida;
	}    
    
    public void normalizar(){
        for(int i=0; i<kardexs.size(); i++){
            if (kardexs.get(i).getProveedor().getId()==0){
                kardexs.get(i).setProveedor(null);
            }
        }
    }
}
