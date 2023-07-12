package com.proyecto.vision.modelos.configuracion;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_parametro;

@Entity
@Table(name = tabla_parametro)
@Getter
@Setter
@AllArgsConstructor
public class Parametro extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "tabla", nullable = true)
    private String tabla;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;

    public Parametro(long id){
        super(id);
    }
    public Parametro(){
        super();
        this.codigo = Constantes.vacio;
        this.tipo = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.tabla = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
    }
}
