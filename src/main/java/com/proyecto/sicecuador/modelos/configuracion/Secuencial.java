package com.proyecto.sicecuador.modelos.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.venta.TipoComprobante;
import com.proyecto.sicecuador.modelos.usuario.Estacion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.secuencial;

@Entity
@Table(name = secuencial)
@Data
@AllArgsConstructor
public class Secuencial extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "numero_siguiente", nullable = true)
    private double numeroSiguiente;
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
        this.numeroSiguiente = Constantes.cero;
        this.estado = Constantes.activo;
        this.tipoComprobante = new TipoComprobante();
        this.estacion = new Estacion();
    }
}