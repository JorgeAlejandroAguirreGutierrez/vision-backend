package com.proyecto.sicecuador.modelos.contabilidad;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_cuenta_contable;

@Entity
@Table(name = tabla_cuenta_contable)
@Data
@AllArgsConstructor
public class CuentaContable extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "cuenta", nullable = true)
    private String cuenta;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "clasificacion", nullable = true)
    private String clasificacion;
    @Column(name = "nivel", nullable = true)
    private long nivel;
    @Column(name = "fe", nullable = true)
    private String fe;
    @Column(name = "casillero", nullable = true)
    private String casillero;
    @Column(name = "mapeo", nullable = true)
    private String mapeo;
    @Column(name = "estado", nullable = true)
    private String estado;

    public CuentaContable(long id){
        super(id);
    }
    public CuentaContable(){
        super();
        this.codigo = Constantes.vacio;
		this.cuenta = Constantes.vacio;
		this.descripcion = Constantes.vacio;
		this.clasificacion = Constantes.vacio;
		this.nivel = Constantes.ceroId;
		this.fe = Constantes.vacio;
		this.casillero = Constantes.vacio;
		this.mapeo = Constantes.vacio;
		this.estado = Constantes.activo;
    }
}
