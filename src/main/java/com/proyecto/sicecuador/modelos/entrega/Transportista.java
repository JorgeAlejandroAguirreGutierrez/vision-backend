package com.proyecto.sicecuador.modelos.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_transportista;

@Entity
@Table(name = tabla_transportista)
@Getter
@Setter
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
    @JoinColumn(name = "tipo_identificacion_id", nullable = true)
    private TipoIdentificacion tipoIdentificacion;
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
    }

    public void normalizar(){
        if(this.tipoIdentificacion == null) this.tipoIdentificacion = new TipoIdentificacion();
        if(this.vehiculoTransporte == null) this.vehiculoTransporte = new VehiculoTransporte();
    }
}
