package com.proyecto.sicecuador.modelos.cliente;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.EstadoCivil;
import com.proyecto.sicecuador.modelos.configuracion.Genero;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "contribuyente")
@Getter
@Setter
@AllArgsConstructor
public class Contribuyente extends Entidad {
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
    @Column(name = "jurisdiccion", nullable = true)
    private String jurisdiccion;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "regimen", nullable = true)
    private String regimen;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_inicio", nullable = true)
    private Date fechaInicio;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_actualiza", nullable = true)
    private Date fechaActualiza;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_suspension", nullable = true)
    private Date fechaSuspension;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_reinicio", nullable = true)
    private Date fechaReinicio;
    @Column(name = "obligado_contabilidad", nullable = true)
    private String obligadoContabilidad;
    @Column(name = "establecimiento", nullable = true)
    private long establecimiento;
    @Column(name = "nombre_comercial", nullable = true)
    private String nombreComercial;
    @Column(name = "estado_establecimiento", nullable = true)
    private String estadoEstablecimiento;
    @Column(name = "provincia", nullable = true)
    private String provincia;
    @Column(name = "canton", nullable = true)
    private String canton;
    @Column(name = "parroquia", nullable = true)
    private String parroquia;
    @Column(name = "codigo_ciiu", nullable = true)
    private String codigoCiiu;
    @Column(name = "actividad_economica", nullable = true)
    private String actividadEconomica;
    @ManyToOne
    @JoinColumn(name = "tipo_contribuyente_id", nullable = true)
    private TipoContribuyente tipoContribuyente;
    @ManyToOne
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;

    public Contribuyente(long id){
        super(id);
    }

    public Contribuyente(){
        super();
        this.identificacion = Constantes.vacio;
        this.razonSocial = Constantes.vacio;
        this.jurisdiccion = Constantes.vacio;
        this.estado = Constantes.vacio;
        this.regimen = Constantes.vacio;
        this.fechaInicio = null;
        this.fechaActualiza = null;
        this.fechaSuspension = null;
        this.fechaReinicio = null;
        this.obligadoContabilidad = Constantes.vacio;
        this.establecimiento = Constantes.ceroId;
        this.nombreComercial = Constantes.vacio;
        this.estadoEstablecimiento = Constantes.vacio;
        this.provincia = Constantes.vacio;
        this.canton = Constantes.vacio;
        this.parroquia = Constantes.vacio;
        this.codigoCiiu = Constantes.vacio;
        this.actividadEconomica = Constantes.vacio;
    }
      
    public void normalizar(){
        if(this.tipoContribuyente == null) this.tipoContribuyente = new TipoContribuyente();
        if(this.ubicacion == null) this.ubicacion = new Ubicacion();
    }
}
