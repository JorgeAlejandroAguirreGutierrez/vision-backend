package com.proyecto.vision.modelos.cliente;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.configuracion.*;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.modelos.usuario.Estacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.proyecto.vision.Constantes.tabla_cliente;

@Entity
@Table(name = tabla_cliente)
@Getter
@Setter
@AllArgsConstructor
public class Cliente extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
    @Column(name = "obligado_contabilidad", nullable = true)
    private String obligadoContabilidad;
    @Column(name = "especial", nullable = true)
    private String especial;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "etiqueta", nullable = true)
    private String etiqueta;
    @Column(name = "referencia", nullable = true)
    private String referencia;
    @Column(name = "latitudgeo", nullable = true)
    private double latitudgeo;
    @Column(name = "longitudgeo", nullable = true)
    private double longitudgeo;
    @Column(name = "monto_financiamiento", nullable = true)
    private double montoFinanciamiento;
    @ManyToOne
    @JoinColumn(name = "tipo_identificacion_id", nullable = true)
    private TipoIdentificacion tipoIdentificacion;
    @ManyToOne
    @JoinColumn(name = "tipo_contribuyente_id", nullable = true)
    private TipoContribuyente tipoContribuyente;
    @ManyToOne
    @JoinColumn(name = "grupo_cliente_id", nullable = true)
    private GrupoCliente grupoCliente;
    @ManyToOne
    @JoinColumn(name = "forma_pago_id", nullable = true)
    private FormaPago formaPago;
    @ManyToOne
    @JoinColumn(name = "plazo_credito_id", nullable = true)
    private PlazoCredito plazoCredito;
    @ManyToOne
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;
    @ManyToOne
    @JoinColumn(name = "genero_id", nullable = true)
    private Genero genero;
    @ManyToOne
    @JoinColumn(name = "estado_civil_id", nullable = true)
    private EstadoCivil estadoCivil;
    @ManyToOne
    @JoinColumn(name = "calificacion_cliente_id", nullable = true)
    private CalificacionCliente calificacionCliente;
    @ManyToOne
    @JoinColumn(name = "origen_ingreso_id", nullable = true)
    private OrigenIngreso origenIngreso;
    @ManyToOne
    @JoinColumn(name = "segmento_id", nullable = true)
    private Segmento segmento;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;
    @JsonManagedReference
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private List<Dependiente> dependientes;
    @JsonManagedReference
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private List<Telefono> telefonos;
    @JsonManagedReference
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private List<Celular> celulares;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private List<Correo> correos;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private List<RetencionCliente> retencionesCliente;

    public Cliente(long id){
        super(id);
    }

    public Cliente(long id, String identificacion, String razonSocial){
        super(id);
        this.identificacion = identificacion;
        this.razonSocial = razonSocial;
    }

    public Cliente(){
        super();
        this.codigo = Constantes.vacio;
        this.identificacion = Constantes.vacio;
        this.razonSocial = Constantes.vacio;
        this.obligadoContabilidad = Constantes.no;
        this.especial = Constantes.no;
        this.estado = Constantes.estadoActivo;
        this.direccion = Constantes.vacio;
        this.referencia = Constantes.vacio;
        this.latitudgeo = Constantes.latCiudad;
        this.longitudgeo = Constantes.lngCiudad;
        this.montoFinanciamiento = Constantes.cero;
        this.dependientes = Collections.emptyList();
        this.telefonos = Collections.emptyList();
        this.celulares = Collections.emptyList();
        this.correos = Collections.emptyList();
        this.retencionesCliente = new ArrayList<>();
        this.retencionesCliente.add(new RetencionCliente());
        this.retencionesCliente.add(new RetencionCliente());
        this.retencionesCliente.add(new RetencionCliente());
        this.retencionesCliente.add(new RetencionCliente());
    }
      
    public void normalizar(){
        if(this.tipoIdentificacion == null) this.tipoIdentificacion = new TipoIdentificacion();
        if(this.tipoContribuyente == null) this.tipoContribuyente = new TipoContribuyente();
        if(this.grupoCliente == null) this.grupoCliente = new GrupoCliente();
        if(this.formaPago == null) this.formaPago = new FormaPago();
        if(this.plazoCredito == null) this.plazoCredito = new PlazoCredito();
        if(this.ubicacion == null) this.ubicacion = new Ubicacion();
        if(this.genero == null) this.genero = new Genero();
        if(this.estadoCivil == null) this.estadoCivil = new EstadoCivil();
        if(this.calificacionCliente == null) this.calificacionCliente = new CalificacionCliente();
        if(this.origenIngreso == null) this.origenIngreso = new OrigenIngreso();
        if(this.segmento == null) this.segmento = new Segmento();
        if(this.empresa == null) this.empresa = new Empresa();
        if(this.dependientes == null) this.dependientes = Collections.emptyList();
        if(this.telefonos == null) this.telefonos = Collections.emptyList();
        if(this.celulares == null) this.celulares = Collections.emptyList();
        if(this.correos == null) this.correos = Collections.emptyList();
        if(this.retencionesCliente == null){
            this.retencionesCliente = new ArrayList<>();
            this.retencionesCliente.add(new RetencionCliente());
            this.retencionesCliente.add(new RetencionCliente());
            this.retencionesCliente.add(new RetencionCliente());
            this.retencionesCliente.add(new RetencionCliente());
        }
        for (RetencionCliente retencionCliente : this.retencionesCliente){
            if (retencionCliente.getTipoRetencion() == null) retencionCliente.setTipoRetencion(new TipoRetencion());
        }
    }
}
