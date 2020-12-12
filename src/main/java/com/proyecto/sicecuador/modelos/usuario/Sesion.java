package com.proyecto.sicecuador.modelos.usuario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.TipoContribuyenteUtil;
import com.proyecto.sicecuador.otros.usuario.SesionUtil;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sesion")
@EntityListeners({SesionUtil.class})
public class Sesion extends Entidad {
    @Column(name = "fecha_apertura", nullable = true)
    private Date fecha_apertura;
    @Column(name = "fecha_cierre", nullable = true)
    private Date fecha_cierre;
    @Column(name = "nombre_pc", nullable = true)
    private String nombre_pc;
    @Column(name = "sesion_ip", nullable = true)
    private String sesion_ip;
    @Column(name = "activa", nullable = true)
    private boolean activa;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;

    public Sesion(){

    }

    public Sesion(long id){
        super(id);
    }

    public Sesion(String codigo, Date fecha_apertura, Date fecha_cierre, String nombre_pc, String sesion_ip, boolean activa, Usuario usuario){
        super(codigo);
        this.fecha_apertura=fecha_apertura;
        this.fecha_cierre=fecha_cierre;
        this.nombre_pc=nombre_pc;
        this.sesion_ip=sesion_ip;
        this.activa=activa;
        this.usuario=usuario;
    }
    public Sesion(List<String> datos){
        fecha_apertura=datos.get(0)== null ? null: new Date(datos.get(0));
        fecha_cierre=datos.get(1)== null ? null: new Date(datos.get(1));
        nombre_pc=datos.get(2)== null ? null: datos.get(2);
        sesion_ip=datos.get(3)== null ? null: datos.get(3);
        activa=datos.get(4)== null ? null: datos.get(4).equals("S") ? true : false;
        usuario=datos.get(5)== null ? null:new Usuario((long) Double.parseDouble(datos.get(5)));
    }
    public Date getFecha_apertura() {
        return fecha_apertura;
    }

    public Date getFecha_cierre() {
        return fecha_cierre;
    }

    public String getNombre_pc() {
        return nombre_pc;
    }

    public String getSesion_ip() {
        return sesion_ip;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setFecha_cierre(Date fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public void setFecha_apertura(Date fecha_apertura) {
        this.fecha_apertura = fecha_apertura;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
