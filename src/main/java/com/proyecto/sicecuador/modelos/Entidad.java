package com.proyecto.sicecuador.modelos;

import com.proyecto.sicecuador.Constantes;
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
    private Timestamp fechaCreacion;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "fecha_actualizacion", nullable = true)
    private Timestamp fechaActualizacion;

    public Entidad(){
        this.id = Constantes.ceroId;
        this.codigo = Constantes.vacio;
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
    public void setId(long id) {
		this.id = id;
	}

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public Timestamp getFechaCreacion() {
		return fechaCreacion;
	}
    
    public void setFechaCreacion(Timestamp fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

    public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}
    
    public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
}
