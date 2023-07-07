package com.proyecto.vision.modelos;

import com.proyecto.vision.Constantes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
public class Entidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
    }
    public Entidad(long id){
        this.id = id;
    }
}
