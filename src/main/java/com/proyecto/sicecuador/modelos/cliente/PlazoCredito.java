package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "plazo_credito")
@Data
@AllArgsConstructor
public class PlazoCredito extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "plazo", nullable = true)
    private double plazo;
    @Column(name = "estado", nullable = true)
    private String estado;

    public PlazoCredito(long id){
        super(id);
    }
    public PlazoCredito(){
        super();
        this.codigo = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.plazo = Constantes.cero;
        this.estado = Constantes.activo;
    }
}
