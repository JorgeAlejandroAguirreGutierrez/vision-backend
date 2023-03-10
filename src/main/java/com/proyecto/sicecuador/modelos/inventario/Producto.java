package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.compra.Proveedor;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import static com.proyecto.sicecuador.Constantes.tabla_producto;

@Entity
@Table(name = tabla_producto)
@Data
@AllArgsConstructor
public class Producto extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "consignacion", nullable = true)
    private String consignacion;
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
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "producto_id")
    private List<Precio> precios;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "producto_id")
    private List<Kardex> kardexs;

    public Producto(long id){
        super(id);
    }
    public Producto() {
        super();
        this.codigo = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.consignacion = Constantes.si;
        this.estado = Constantes.activo;
        this.categoriaProducto = new CategoriaProducto();
        this.grupoProducto = new GrupoProducto();
        this.tipoGasto = new TipoGasto();
        this.impuesto = new Impuesto();
        this.medida = new Medida();
        this.proveedor = new Proveedor();
        this.precios = Collections.emptyList();
        this.kardexs = Collections.emptyList();
    }

    public void normalizar(){
        if(this.categoriaProducto == null) this.categoriaProducto = new CategoriaProducto();
        if(this.grupoProducto == null) this.grupoProducto = new GrupoProducto();
        if(this.tipoGasto == null) this.tipoGasto = new TipoGasto();
        if(this.impuesto == null) this.impuesto = new Impuesto();
        if(this.medida == null) this.medida = new Medida();
        if(this.proveedor == null) this.proveedor = new Proveedor();
        if(this.precios == null) this.precios = Collections.emptyList();
        if(this.kardexs == null) this.kardexs = Collections.emptyList();
    }
}
