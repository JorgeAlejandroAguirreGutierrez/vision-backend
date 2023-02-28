package com.proyecto.sicecuador.modelos.configuracion;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_parametro;

@Entity
@Table(name = tabla_parametro)
@Data
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
        this.estado = Constantes.activo;
    }
}
