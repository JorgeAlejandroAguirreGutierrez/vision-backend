package com.proyecto.vision.modelos.usuario;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.configuracion.TipoIdentificacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_empresa;

@Entity
@Table(name = tabla_empresa)
@Getter
@Setter
@AllArgsConstructor
public class Empresa extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
	@Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
    @Column(name = "nombre_comercial", nullable = true)
    private String nombreComercial;
    @Column(name = "representante_legal", nullable = true)
    private String representanteLegal;
    @Column(name = "cargo_representante_legal", nullable = true)
    private String cargoRepresentanteLegal;
    @Transient
    private String logo64;
    @Lob
    @Column(name = "logo", nullable = true)
    private byte[] logo;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "obligado_contabilidad", nullable = true)
    private String obligadoContabilidad;
    @Column(name = "especial", nullable = true)
    private String especial;
    @Column(name = "resolucion_especial", nullable = true)
    private String resolucionEspecial;
    @Column(name = "agente_retencion", nullable = true)
    private String agenteRetencion;
    @Column(name = "resolucion_agente", nullable = true)
    private String resolucionAgente;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "tipo_identificacion_id", nullable = true)
    private TipoIdentificacion tipoIdentificacion;

    public Empresa(long id){
        super(id);
    }
    public Empresa(){
        super();
        this.codigo = Constantes.vacio;
        this.identificacion = Constantes.vacio;
        this.razonSocial = Constantes.vacio;
        this.nombreComercial = Constantes.vacio;
        this.representanteLegal = Constantes.vacio;
        this.cargoRepresentanteLegal = Constantes.vacio;
        this.logo64 = Constantes.vacio;
        this.logo = null;
        this.direccion = Constantes.vacio;
        this.obligadoContabilidad = Constantes.no;
        this.especial = Constantes.no;
        this.resolucionEspecial = Constantes.vacio;
        this.agenteRetencion = Constantes.no;
        this.resolucionAgente = Constantes.vacio;
        this.estado = Constantes.activo;
    }

    public void normalizar(){
        if(this.tipoIdentificacion == null) this.tipoIdentificacion = new TipoIdentificacion();
    }
}
