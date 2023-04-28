package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Regimen;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import static com.proyecto.sicecuador.Constantes.tabla_establecimiento;

@Entity
@Table(name = tabla_establecimiento)
@Data
@AllArgsConstructor
public class Establecimiento extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "codigoSRI", nullable = true)
    private String codigoSRI;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "latitudgeo", nullable = true)
    private String latitudgeo;
    @Column(name = "longitudgeo", nullable = true)
    private String longitudgeo;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "regimen_id", nullable = true)
    private Regimen regimen;
    @ManyToOne
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private List<TelefonoEstablecimiento> telefonosEstablecimiento;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private List<CelularEstablecimiento> celularesEstablecimiento;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private List<CorreoEstablecimiento> correosEstablecimiento;

    public Establecimiento(long id){
        super(id);
    }
    public Establecimiento(){
        super();
        this.codigo = Constantes.vacio;
        this.codigoSRI = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.direccion = Constantes.vacio;
        this.latitudgeo = Constantes.vacio;
        this.longitudgeo = Constantes.vacio;
        this.estado = Constantes.activo;
        this.regimen = new Regimen();
        this.ubicacion = new Ubicacion();
        this.empresa = new Empresa();
        this.telefonosEstablecimiento = Collections.emptyList();
        this.celularesEstablecimiento = Collections.emptyList();
        this.correosEstablecimiento = Collections.emptyList();

    }
    public void normalizar(){
        if(this.regimen == null) this.regimen = new Regimen();
        if(this.ubicacion == null) this.ubicacion = new Ubicacion();
        if(this.empresa == null) this.empresa = new Empresa();
        if(this.telefonosEstablecimiento == null) this.telefonosEstablecimiento = Collections.emptyList();
        if(this.celularesEstablecimiento == null) this.celularesEstablecimiento = Collections.emptyList();
        if(this.correosEstablecimiento == null) this.correosEstablecimiento = Collections.emptyList();
    }
}
