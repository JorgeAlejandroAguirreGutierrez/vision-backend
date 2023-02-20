package com.proyecto.sicecuador.modelos.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sesion")
@Data
@AllArgsConstructor
public class Sesion extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "fecha_apertura", nullable = true)
    private Date fechaApertura;
    @Column(name = "fecha_cierre", nullable = true)
    private Date fechaCierre;
    @Column(name = "ip", nullable = true)
    private String ip;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    public Sesion(long id){
        super(id);
    }
    public Sesion(){
        super();
        this.codigo = Constantes.vacio;
        this.fechaApertura = new Date();
        this.fechaCierre = new Date();
        this.ip = Constantes.vacio;
        this.estado = Constantes.activo;
        this.usuario = new Usuario();
        this.empresa = new Empresa();
    }

    public void normalizar(){
        if(this.fechaApertura == null) this.fechaApertura = new Date();
        if(this.fechaCierre == null) this.fechaCierre = new Date();
        if(this.usuario == null) this.usuario = new Usuario();
        if(this.empresa == null) this.empresa = new Empresa();
    }
}
