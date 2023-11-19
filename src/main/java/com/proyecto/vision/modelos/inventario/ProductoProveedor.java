package com.proyecto.vision.modelos.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.configuracion.Impuesto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.proyecto.vision.modelos.compra.Proveedor;

import javax.persistence.*;
import java.util.Collections;

@Entity
@Table(name = "producto_proveedor")
@Getter
@Setter
@AllArgsConstructor

public class ProductoProveedor extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "codigo_equivalente", nullable = true)
    private String codigoEquivalente;
    @Column(name = "estado", nullable = true)
    private String estado;
	@ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;

    public ProductoProveedor(long id){
        super(id);
    }

    public ProductoProveedor(){
        super();
        this.codigo = Constantes.vacio;
        this.codigoEquivalente = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
    }

    public void normalizar(){
        if(this.producto == null) this.producto = new Producto();
        if(this.proveedor == null) this.proveedor = new Proveedor();
    }
}
