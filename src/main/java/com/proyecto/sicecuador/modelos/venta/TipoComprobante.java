package com.proyecto.sicecuador.modelos.venta;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_tipo_comprobante;

@Entity
@Table(name = tabla_tipo_comprobante)
@Data
@AllArgsConstructor
public class TipoComprobante extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "codigo_sri", nullable = true)
    private String codigoSRI;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "nombre_tabla", nullable = true)
    private String nombreTabla;
    @Column(name = "electronica", nullable = true)
    private String electronica;
    @Column(name = "estado", nullable = true)
    private String estado;

    public TipoComprobante(long id){
        super(id);
    }
    public TipoComprobante(){
        super();
        this.codigo = Constantes.vacio;
        this.codigoSRI = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.nombreTabla = Constantes.vacio;
        this.electronica = Constantes.no;
        this.estado = Constantes.activo;
    }
}
