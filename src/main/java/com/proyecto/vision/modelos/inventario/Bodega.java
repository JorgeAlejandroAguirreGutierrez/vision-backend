package com.proyecto.vision.modelos.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.usuario.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_bodega;

@Entity
@Table(name = tabla_bodega)
@Getter
@Setter
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
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    public Bodega(long id){
        super(id);
    }

    public Bodega(){
        super();
        this.codigo = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
    }
}
