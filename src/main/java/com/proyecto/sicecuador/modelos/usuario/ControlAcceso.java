package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "control_acceso")
public class ControlAcceso  extends Entidad{
	@NotNull
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
	@NotNull
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
	@NotNull
    @Column(name = "estado", nullable = true)
    private String estado;

	public ControlAcceso(){
        super();
    }
    public ControlAcceso(long id){
        super(id);
    }
	
}
