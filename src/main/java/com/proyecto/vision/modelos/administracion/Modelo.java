package com.proyecto.vision.modelos.administracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_modelo;

@Entity
@Table(name = tabla_modelo)
@Getter
@Setter
@AllArgsConstructor
public class Modelo extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "nombre_tecnico", nullable = true)
    private String nombreTecnico;
    @Column(name = "endpoint", nullable = true)
    private String endpoint;
    @Column(name = "estado", nullable = true)
    private String estado;
    public Modelo(long id){
        super(id);
    }
    public Modelo(){
        super();
        this.codigo = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.nombreTecnico = Constantes.vacio;
        this.endpoint = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
    }
}
