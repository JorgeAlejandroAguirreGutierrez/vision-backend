package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.proyecto.sicecuador.Constantes.tabla_tipo_operacion;

@Entity
@Table(name = tabla_tipo_operacion)
@Getter
@Setter
@AllArgsConstructor
public class TipoOperacion extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;

    public TipoOperacion(long id){
        super(id);
    }
    public TipoOperacion(){
        super();
        this.codigo = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.activo;
    }
}
