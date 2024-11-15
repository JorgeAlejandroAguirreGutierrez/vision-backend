package com.proyecto.vision.modelos.configuracion;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_tipo_identificacion;

@Entity
@Table(name = tabla_tipo_identificacion)
@Getter
@Setter
@AllArgsConstructor
public class TipoIdentificacion extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
	@Column(name = "codigo_sri", nullable = true)
    private String codigoSRI;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;

    public TipoIdentificacion(long id){
        super(id);
    }
    public TipoIdentificacion(){
        super();
        this.codigo = Constantes.vacio;
        this.codigoSRI = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
    }
}