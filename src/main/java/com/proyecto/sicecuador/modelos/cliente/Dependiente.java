package com.proyecto.sicecuador.modelos.cliente;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import static com.proyecto.sicecuador.Constantes.tabla_dependiente;

@Entity
@Table(name = tabla_dependiente)
@Data
@AllArgsConstructor
public class Dependiente extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "latitudgeo", nullable = true)
    private double latitudgeo;
    @Column(name = "longitudgeo", nullable = true)
    private double longitudgeo;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "dependiente_id")
    private List<TelefonoDependiente> telefonosDependiente;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "dependiente_id")
    private List<CelularDependiente> celularesDependiente;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "dependiente_id")
    private List<CorreoDependiente> correosDependiente;

    public Dependiente(long id){
        super(id);
    }
    public Dependiente(){
        super();
        this.codigo = Constantes.vacio;
        this.razonSocial = Constantes.vacio;
        this.direccion = Constantes.vacio;
        this.ubicacion = new Ubicacion();
        this.estado = Constantes.activo;
        this.telefonosDependiente = Collections.emptyList();
        this.celularesDependiente = Collections.emptyList();
        this.correosDependiente = Collections.emptyList();

    }
    public void normalizar(){
        if(this.ubicacion == null) this.ubicacion = new Ubicacion();
        if(this.telefonosDependiente == null) this.telefonosDependiente = Collections.emptyList();
        if(this.celularesDependiente == null) this.celularesDependiente = Collections.emptyList();
        if(this.correosDependiente == null) this.correosDependiente = Collections.emptyList();
    }
}
