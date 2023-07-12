package com.proyecto.vision.modelos.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.contabilidad.CuentaContable;
import com.proyecto.vision.modelos.usuario.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_grupo_producto;

@Entity
@Table(name = tabla_grupo_producto)
@Getter
@Setter
@AllArgsConstructor
public class GrupoProducto extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "grupo", nullable = true)
    private String grupo;
    @Column(name = "subgrupo", nullable = true)
    private String subgrupo;
    @Column(name = "seccion", nullable = true)
    private String seccion;
    @Column(name = "linea", nullable = true)
    private String linea;
    @Column(name = "sublinea", nullable = true)
    private String sublinea;
    @Column(name = "presentacion", nullable = true)
    private String presentacion;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "categoria_producto_id", nullable = true)
    private CategoriaProducto categoriaProducto;
    @ManyToOne
    @JoinColumn(name = "cuenta_contable_id", nullable = true)
    private CuentaContable cuentaContable;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    public GrupoProducto(long id){
        super(id);
    }
    public GrupoProducto(){
    	super();
        this.codigo = Constantes.vacio;
        this.grupo = Constantes.vacio;
        this.subgrupo = Constantes.vacio;
        this.seccion = Constantes.vacio;
        this.linea = Constantes.vacio;
        this.sublinea = Constantes.vacio;
        this.presentacion = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
    }

    public void normalizar(){
        if(this.categoriaProducto == null) this.categoriaProducto = new CategoriaProducto();
        if(this.cuentaContable == null) this.cuentaContable = new CuentaContable();
    }
}
