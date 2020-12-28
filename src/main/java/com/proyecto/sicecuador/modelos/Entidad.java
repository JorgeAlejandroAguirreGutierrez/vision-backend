package com.proyecto.sicecuador.modelos;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@MappedSuperclass
public class Entidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "fecha_creacion", nullable = true)
    private Timestamp fecha_creacion;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "fecha_actualizacion", nullable = true)
    private Timestamp fecha_actualizacion;

    public Entidad(){

    }

    public Entidad(long id){
        this.id=id;
    }
    public Entidad(String codigo){
        this.codigo=codigo;
    }
    public Entidad(long id, String codigo){
        this.id=id;
        this.codigo=codigo;
    }

    public long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public Date getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_creacion(Timestamp fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setFecha_actualizacion(Timestamp fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }
}
