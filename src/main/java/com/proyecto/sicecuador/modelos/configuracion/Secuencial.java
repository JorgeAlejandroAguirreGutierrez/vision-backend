package com.proyecto.sicecuador.modelos.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.usuario.Empresa;
import com.proyecto.sicecuador.modelos.usuario.Estacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.secuencial;

@Entity
@Table(name = secuencial)
@Getter
@Setter
@AllArgsConstructor
public class Secuencial extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "numero_siguiente", nullable = true)
    private long numeroSiguiente;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "tipo_comprobante_id", nullable = true)
    private TipoComprobante tipoComprobante;
    @ManyToOne
    @JoinColumn(name = "estacion_id", nullable = true)
    private Estacion estacion;
    public Secuencial(long id){
        super(id);
    }
    public Secuencial(){
        super();
        this.codigo = Constantes.vacio;
        this.numeroSiguiente = Constantes.ceroId;
        this.estado = Constantes.activo;
    }
}
