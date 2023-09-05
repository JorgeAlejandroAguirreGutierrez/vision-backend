package com.proyecto.vision.modelos.cliente;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.configuracion.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cliente_base")
@Getter
@Setter
@AllArgsConstructor
public class ClienteBase extends Entidad {
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "apellidos", nullable = true)
    private String apellidos;
    @Column(name = "nombres", nullable = true)
    private String nombres;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_nacimiento", nullable = true)
    private Date fechaNacimiento;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "referencia", nullable = true)
    private String referencia;
    @Column(name = "provincia", nullable = true)
    private String provincia;
    @Column(name = "canton", nullable = true)
    private String canton;
    @Column(name = "parroquia", nullable = true)
    private String parroquia;
    @Column(name = "telefono", nullable = true)
    private String telefono;
    @Column(name = "celular", nullable = true)
    private String celular;
    @Column(name = "correo", nullable = true)
    private String correo;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "origen", nullable = true)
    private String origen;
    @Column(name = "prioridad", nullable = true)
    private double prioridad;
    @ManyToOne
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;
    @ManyToOne
    @JoinColumn(name = "genero_id", nullable = true)
    private Genero genero;
    @ManyToOne
    @JoinColumn(name = "estado_civil_id", nullable = true)
    private EstadoCivil estadoCivil;

    public ClienteBase(long id){
        super(id);
    }

    public ClienteBase(){
        super();
        this.identificacion = Constantes.vacio;
        this.apellidos = Constantes.vacio;
        this.nombres = Constantes.vacio;
        this.fechaNacimiento = null;
        this.direccion = Constantes.vacio;
        this.referencia = Constantes.vacio;
        this.provincia = Constantes.vacio;
        this.canton = Constantes.vacio;
        this.parroquia = Constantes.vacio;
        this.telefono = Constantes.vacio;
        this.celular = Constantes.vacio;
        this.correo = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
        this.origen = Constantes.vacio;
        this.prioridad = Constantes.cero;
    }
      
    public void normalizar(){
        if(this.ubicacion == null) this.ubicacion = new Ubicacion();
        if(this.genero == null) this.genero = new Genero();
        if(this.estadoCivil == null) this.estadoCivil = new EstadoCivil();
    }
}
