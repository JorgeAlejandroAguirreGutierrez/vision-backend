package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.GeneroUtil;
import com.proyecto.sicecuador.otros.cliente.OrigenIngresoUtil;

import javax.persistence.*;

@Entity
@Table(name = "origen_ingreso")
//@EntityListeners({OrigenIngresoUtil.class})
public class OrigenIngreso extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public OrigenIngreso(){
        super();
    }

    public OrigenIngreso(long id) {
        super(id);
    }

    public OrigenIngreso(String codigo, String descripcion, String abreviatura){
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
