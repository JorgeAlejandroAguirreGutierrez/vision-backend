package com.proyecto.vision.modelos.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_ubicacion;

@Entity
@Table(name = tabla_ubicacion)
@Getter
@Setter
@AllArgsConstructor
public class Ubicacion extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "codigo_norma", nullable = true)
    private String codigoNorma;
    @Column(name = "provincia", nullable = true)
    private String provincia;
    @Column(name = "canton", nullable = true)
    private String canton;
    @Column(name = "parroquia", nullable = true)
    private String parroquia;
    @Column(name = "estado", nullable = true)
    private String estado;
    public Ubicacion(long id){
        super(id);
    }
    public Ubicacion(){
        super();
        this.codigo = Constantes.vacio;
        this.codigoNorma = Constantes.vacio;
        this.provincia = Constantes.vacio;
        this.canton = Constantes.vacio;
        this.parroquia = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
    }
}
