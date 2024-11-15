package com.proyecto.vision.modelos.configuracion;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_impuesto;

@Entity
@Table(name = tabla_impuesto)
@Getter
@Setter
@AllArgsConstructor
public class Impuesto extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name="codigo_sri", nullable = true)
    private String codigoSRI;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "porcentaje", nullable = true)
    private double porcentaje;
    @Column(name = "estado", nullable = true)
    private String estado;

    public Impuesto(long id){
        super(id);
    }
    public Impuesto(){
        super();
        this.codigo = Constantes.vacio;
        this.codigoSRI = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.porcentaje = Constantes.cero;
        this.estado = Constantes.estadoActivo;
    }
}
