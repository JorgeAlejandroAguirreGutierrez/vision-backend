package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.ProductoUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "producto")
@EntityListeners({ProductoUtil.class})
public class Producto extends Entidad {
	@JsonProperty("nombre")
    @Column(name = "nombre", nullable = true)
    private String nombre;
	@JsonProperty("consignacion")
    @Column(name = "consignacion", nullable = true)
    private boolean consignacion;
	@JsonProperty("estado")
    @Column(name = "estado", nullable = true)
    private boolean estado;
	@JsonProperty("serie_autogenerado")
    @Column(name = "serie_autogenerado", nullable = true)
    private boolean serieAutogenerado;
    @ManyToOne
    @JsonProperty("tipo_producto")
    @JoinColumn(name = "tipo_producto_id", nullable = true)
    private TipoProducto tipoProducto;
    @ManyToOne
    @JsonProperty("grupo_producto")
    @JoinColumn(name = "grupo_producto_id", nullable = true)
    private GrupoProducto grupoProducto;
    @ManyToOne
    @JsonProperty("sub_grupo_producto")
    @JoinColumn(name = "sub_grupo_producto_id", nullable = true)
    private SubGrupoProducto subGrupoProducto;
    @ManyToOne
    @JsonProperty("categoria_producto")
    @JoinColumn(name = "categoria_producto_id", nullable = true)
    private CategoriaProducto categoriaProducto;
    @ManyToOne
    @JsonProperty("linea_producto")
    @JoinColumn(name = "linea_producto_id", nullable = true)
    private LineaProducto lineaProducto;
    @ManyToOne
    @JsonProperty("sub_linea_producto")
    @JoinColumn(name = "sub_linea_producto_id", nullable = true)
    private SubLineaProducto subLineaProducto;
    @ManyToOne
    @JsonProperty("presentacion_producto")
    @JoinColumn(name = "presentacion_producto_id", nullable = true)
    private PresentacionProducto presentacionProducto;
    @ManyToOne
    @JsonProperty("tipo_gasto")
    @JoinColumn(name = "tipo_gasto_id", nullable = true)
    private TipoGasto tipoGasto;
    @ManyToOne
    @JsonProperty("impuesto")
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("kardexs")
    @JoinColumn(name = "producto_id", nullable = true)
    private List<Kardex> kardexs;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("medidas_precios")
    @JoinColumn(name = "producto_id", nullable = true)
    private List<MedidaPrecio> medidasPrecios;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("caracteristicas")
    @JoinColumn(name = "producto_id", nullable = true)
    private List<Caracteristica> caracteristicas;

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

    public Producto(String codigo, String nombre, boolean consignacion, boolean estado,
                    boolean serieAutogenerado, TipoGasto tipoGasto,
                    TipoProducto tipoProducto, GrupoProducto grupoProducto, SubGrupoProducto subGrupoProducto,
                    LineaProducto lineaProducto, SubLineaProducto subLineaProducto,
                    CategoriaProducto categoriaProducto, PresentacionProducto presentacionProducto,
                    Impuesto impuesto) {
        super(codigo);
        this.nombre = nombre;
        this.presentacionProducto = presentacionProducto;
        this.consignacion = consignacion;
        this.estado = estado;
        this.serieAutogenerado = serieAutogenerado;
        this.tipoGasto = tipoGasto;
        this.tipoProducto = tipoProducto;
        this.grupoProducto= grupoProducto;
        this.subGrupoProducto = subGrupoProducto;
        this.categoriaProducto = categoriaProducto;
        this.lineaProducto = lineaProducto;
        this.subLineaProducto = subLineaProducto;
        this.presentacionProducto = presentacionProducto;
        this.impuesto = impuesto;
    }
    public Producto(List<String>datos){
        nombre=datos.get(0)== null ? null: datos.get(0);
        consignacion=datos.get(1)== null ? null: datos.get(1).equals("S") ? true : false;
        estado=datos.get(2)== null ? null: datos.get(2).equals("S") ? true : false;
        serieAutogenerado=datos.get(3)== null ? null: datos.get(3).equals("S") ? true : false;
        tipoProducto=datos.get(4)== null ? null: new TipoProducto((long) Double.parseDouble(datos.get(4)));
        presentacionProducto=datos.get(5)== null ? null: new PresentacionProducto((long) Double.parseDouble(datos.get(5)));
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

    public TipoGasto getTipoGasto() {
		return tipoGasto;
	}

    public boolean isSerieAutogenerado() {
		return serieAutogenerado;
	}

    public void setSerieAutogenerado(boolean serieAutogenerado) {
		this.serieAutogenerado = serieAutogenerado;
	}

    public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

    public GrupoProducto getGrupoProducto() {
		return grupoProducto;
	}

    public SubGrupoProducto getSubGrupoProducto() {
		return subGrupoProducto;
	}

    public CategoriaProducto getCategoriaProducto() {
		return categoriaProducto;
	}

    public LineaProducto getLineaProducto() {
		return lineaProducto;
	}

    public SubLineaProducto getSubLineaProducto() {
		return subLineaProducto;
	}

    public PresentacionProducto getPresentacionProducto() {
    	return presentacionProducto;
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    @JsonManagedReference
    public List<Kardex> getKardexs() {
        return kardexs;
    }

    @JsonManagedReference
    public List<MedidaPrecio> getMedidasPrecios() {
		return medidasPrecios;
	}

    @JsonManagedReference
    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void normalizar(){
        for(int i=0; i<kardexs.size(); i++){
            if (kardexs.get(i).getProveedor().getId()==0){
                kardexs.get(i).setProveedor(null);
            }
        }
    }
}
