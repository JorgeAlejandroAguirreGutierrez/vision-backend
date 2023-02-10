package com.proyecto.sicecuador.modelos.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "transportista")
@Data
@AllArgsConstructor
public class Transportista extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "vehiculo_propio", nullable = true)
    private String vehiculoPropio;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "vehiculo_transporte_id", nullable = true)
    private VehiculoTransporte vehiculoTransporte;

    public Transportista(long id){
        super(id);
    }
    public Transportista(){
        super();
        this.codigo = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.identificacion = Constantes.vacio;
        this.vehiculoPropio = Constantes.si;
        this.estado = Constantes.activo;
        this.vehiculoTransporte = new VehiculoTransporte();
    }

    public void normalizar(){
        if(this.vehiculoTransporte == null) this.vehiculoTransporte = new VehiculoTransporte();
    }
}
