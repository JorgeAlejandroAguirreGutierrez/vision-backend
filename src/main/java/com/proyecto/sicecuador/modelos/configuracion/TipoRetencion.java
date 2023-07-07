package com.proyecto.vision.modelos.configuracion;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_tipo_retencion;

@Entity
@Table(name = tabla_tipo_retencion)
@Getter
@Setter
@AllArgsConstructor
public class TipoRetencion extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "impuesto_retencion", nullable = true)
    private String impuestoRetencion;
    @Column(name = "tipo_retencion", nullable = true)
    private String tipoRetencion;
    @Column(name = "codigo_sri", nullable = true)
    private String codigoSRI;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "porcentaje", nullable = true)
    private double porcentaje;
    @Column(name = "estado", nullable = true)
    private String estado;

    public TipoRetencion(long id){
        super(id);
    }
    public TipoRetencion(){
        super();
        this.codigo = Constantes.vacio;
        this.impuestoRetencion = Constantes.vacio;
        this.tipoRetencion = Constantes.vacio;
        this.codigoSRI = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.porcentaje = Constantes.cero;
        this.estado = Constantes.activo;
    }
}
