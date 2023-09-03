package com.proyecto.vision.modelos.recaudacion;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_operador_tarjeta;

@Entity
@Table(name = tabla_operador_tarjeta)
@Getter
@Setter
@AllArgsConstructor
public class OperadorTarjeta extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;

    public OperadorTarjeta(long id){
        super(id);
    }
    public OperadorTarjeta(){
        super();
        this.codigo = Constantes.vacio;
        this.tipo = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
    }
    public void normalizar(){
        this.tipo = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
    }
}
