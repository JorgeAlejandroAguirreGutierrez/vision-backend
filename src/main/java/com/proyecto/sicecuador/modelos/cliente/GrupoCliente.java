package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;
import com.proyecto.sicecuador.modelos.usuario.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_grupo_cliente;

@Entity
@Table(name = tabla_grupo_cliente)
@Getter
@Setter
@AllArgsConstructor
public class GrupoCliente extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;
	@ManyToOne
    @JoinColumn(name = "cuenta_contable_id", nullable = true)
    private CuentaContable cuentaContable;
    //@ManyToOne
    //@JoinColumn(name = "empresa_id", nullable = true)
    //private Empresa empresa;

    public GrupoCliente(long id){
        super(id);
    }
    public GrupoCliente(){
        super();
        this.codigo = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.activo;
    }

    public void normalizar(){
        if(this.cuentaContable == null) this.cuentaContable = new CuentaContable();
        //if(this.empresa == null) this.empresa = new Empresa();
    }
}
