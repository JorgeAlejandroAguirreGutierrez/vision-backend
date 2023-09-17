package com.proyecto.vision.modelos.usuario;

import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;

import static com.proyecto.vision.Constantes.tabla_suscripcion;

@Entity
@Table(name = tabla_suscripcion)
@Getter
@Setter
@AllArgsConstructor
public class Suscripcion extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "fecha_inicial", nullable = true)
    private Date fechaInicial;
    @Column(name = "fecha_final", nullable = true)
    private Date fechaFinal;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "paquete_id", nullable = true)
    private Paquete paquete;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    public void normalizar(){

    }
}
