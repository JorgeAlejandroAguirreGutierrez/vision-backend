package com.proyecto.sicecuador.modelos.administracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_modelo;

@Entity
@Table(name = tabla_modelo)
@Data
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
        this.estado = Constantes.activo;
    }
}
