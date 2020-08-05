package com.proyecto.sicecuador.modelos.usuario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.otros.cliente.TipoContribuyenteUtil;
import com.proyecto.sicecuador.otros.usuario.EstablecimientoUtil;

import javax.persistence.*;

@Entity
@Table(name = "establecimiento")
public class Establecimiento extends Entidad {
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;

    public Establecimiento(){
        super();
    }
    public Establecimiento(long id){
        super(id);
    }

    public Establecimiento(String codigo, String direccion, Empresa empresa, Ubicacion ubicacion){
        super(codigo);
        this.direccion=direccion;
        this.empresa=empresa;
        this.ubicacion=ubicacion;
    }
    public String getDireccion() {
        return direccion;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }
}
