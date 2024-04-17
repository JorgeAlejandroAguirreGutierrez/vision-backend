package com.proyecto.vision.modelos.contabilidad;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.acceso.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_cuenta_contable;

@Entity
@Table(name = tabla_cuenta_contable)
@Getter
@Setter
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
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

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
		this.estado = Constantes.estadoActivo;
    }

    public void normalizar(){
        if(this.empresa == null) this.empresa = new Empresa();
    }
}
