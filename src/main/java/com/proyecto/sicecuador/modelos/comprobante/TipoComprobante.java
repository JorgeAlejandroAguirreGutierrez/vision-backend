package com.proyecto.sicecuador.modelos.comprobante;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tipo_comprobante")
@Data
@AllArgsConstructor
public class TipoComprobante extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "codigo_sri", nullable = true)
    private String codigoSRI;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "nombre_tabla", nullable = true)
    private String nombreTabla;

    public TipoComprobante(long id){
        super(id);
    }
    public TipoComprobante(){
        super();
        this.codigo = Constantes.vacio;
        this.codigoSRI = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.nombreTabla = Constantes.vacio;
    }
}
