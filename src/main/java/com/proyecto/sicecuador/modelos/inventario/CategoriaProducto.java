package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categoria_producto")
public class CategoriaProducto extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;

    public CategoriaProducto(){
        super();
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.activo;
    }

    public CategoriaProducto(long id){
        super(id);
    }

    public CategoriaProducto(List<String> datos){

    }
    public CategoriaProducto(String codigo, String descripcion, String abreviatura, String estado){
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
        this.estado=estado;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public String getEstado() {
        return abreviatura;
    }
    
    public void setEstado(String estado) {
		this.estado = estado;
	}
    
}
