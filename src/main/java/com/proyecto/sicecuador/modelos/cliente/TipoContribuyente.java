package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_tipo_contribuyente;

@Entity
@Table(name = tabla_tipo_contribuyente)
@Getter
@Setter
@AllArgsConstructor
public class TipoContribuyente extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "subtipo", nullable = true)
    private String subtipo;
    @Column(name = "obligado_contabilidad", nullable = true)
    private String obligadoContabilidad;

    public TipoContribuyente(long id){
        super(id);
    }
    public TipoContribuyente(){
        super();
        this.codigo = Constantes.vacio;
        this.tipo = Constantes.vacio;
        this.subtipo = Constantes.vacio;
        this.obligadoContabilidad = Constantes.no;
    }
}
