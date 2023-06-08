package com.proyecto.sicecuador.modelos.inventario;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_tipo_gasto;

@Entity
@Table(name = tabla_tipo_gasto)
@Getter
@Setter
@AllArgsConstructor
public class TipoGasto extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public TipoGasto(long id){
        super(id);
    }
    public TipoGasto(){
        super();
        this.codigo = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
    }
}
