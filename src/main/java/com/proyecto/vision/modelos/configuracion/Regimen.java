package com.proyecto.vision.modelos.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.proyecto.vision.Constantes.tabla_regimen;

@Entity
@Table(name = tabla_regimen)
@Getter
@Setter
@AllArgsConstructor
public class Regimen extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "visible", nullable = true)
    private String visible;
    @Column(name = "estado", nullable = true)
    private String estado;

    public Regimen(long id){
        super(id);
    }
    public Regimen(){
        super();
        this.codigo = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.visible = Constantes.no;
        this.estado = Constantes.vacio;
    }
}
