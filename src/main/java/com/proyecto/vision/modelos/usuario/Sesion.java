package com.proyecto.vision.modelos.usuario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static com.proyecto.vision.Constantes.tabla_sesion;

@Entity
@Table(name = tabla_sesion)
@Getter
@Setter
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
        this.estado = Constantes.estadoActivo;
    }

    public void normalizar(){
        if(this.fechaApertura == null) this.fechaApertura = new Date();
        if(this.fechaCierre == null) this.fechaCierre = new Date();
        if(this.usuario == null) this.usuario = new Usuario();
    }
}
