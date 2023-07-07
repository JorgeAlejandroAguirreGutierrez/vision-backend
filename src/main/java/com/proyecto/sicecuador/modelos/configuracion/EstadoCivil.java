package com.proyecto.vision.modelos.configuracion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_estado_civil;

@Entity
@Table(name = tabla_estado_civil)
@Getter
@Setter
@AllArgsConstructor
public class EstadoCivil extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;

    public EstadoCivil(long id){
        super(id);
    }
    public EstadoCivil(){
        super();
        this.codigo = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.activo;
    }
}
