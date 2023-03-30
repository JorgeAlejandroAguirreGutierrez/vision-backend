package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_bodega;

@Entity
@Table(name = tabla_bodega)
@Data
@AllArgsConstructor
public class Bodega extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;

    public Bodega(long id){
        super(id);
    }

    public Bodega(){
        super();
        this.codigo = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.activo;
    }
}
