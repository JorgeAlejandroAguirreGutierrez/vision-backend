package com.proyecto.sicecuador.modelos.cliente;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.inventario.Segmento;
import com.proyecto.sicecuador.modelos.usuario.Estacion;

import javax.persistence.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente extends Entidad {
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
    @JoinColumn(name = "estacion_id", nullable = true)
    private Estacion estacion;
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

    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private List<Dependiente> dependientes;
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private List<Telefono> telefonos;
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private List<Celular> celulares;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private List<Correo> correos;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = true)
    private List<RetencionCliente> retencionesCliente;

    public Cliente(){
        super();
        this.identificacion = Constantes.vacio;
        this.razonSocial = Constantes.vacio;
        this.obligadoContabilidad = Constantes.no;
        this.especial = Constantes.no;
        this.estado = Constantes.activo;
        this.direccion = Constantes.vacio;
        this.referencia = Constantes.vacio;
        this.latitudgeo = Constantes.cero;
        this.longitudgeo = Constantes.cero;
        this.montoFinanciamiento = Constantes.cero;
        this.tipoIdentificacion = new TipoIdentificacion();
        this.tipoContribuyente = new TipoContribuyente();
        this.estacion = new Estacion();
        this.grupoCliente = new GrupoCliente();
        this.formaPago = new FormaPago();
        this.plazoCredito = new PlazoCredito();
        this.ubicacion = new Ubicacion();
        this.genero = new Genero();
        this.estadoCivil = new EstadoCivil();
        this.calificacionCliente = new CalificacionCliente();
        this.origenIngreso = new OrigenIngreso();
        this.segmento = new Segmento();
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

    public Cliente(long id){
        super(id);
    }
    public Cliente(String razon_social){
        super(0);
        this.razonSocial=razon_social;
    }
    
    public Cliente(TipoIdentificacion tipoIdentificacion, TipoContribuyente tipoContribuyente){
        super(0);
        this.tipoIdentificacion=tipoIdentificacion;
        this.tipoContribuyente=tipoContribuyente;
    }

    public Cliente(String codigo, String identificacion, String razonSocial, String obligadoContabilidad, String especial, String estado, 
    		String direccion, String etiqueta, String referencia, double latitudgeo, double longitudgeo,
    		TipoIdentificacion tipoIdentificacion, Estacion estacion, GrupoCliente grupoCliente, TipoContribuyente tipoContribuyente,
    		Ubicacion ubicacion, double montoFinanciamiento, FormaPago formaPago, PlazoCredito plazoCredito, Genero genero, EstadoCivil estadoCivil,
            CalificacionCliente calificacionCliente, OrigenIngreso origenIngreso, Segmento segmento){
        super(codigo);
        this.tipoIdentificacion=tipoIdentificacion;
        this.identificacion=identificacion;
        this.razonSocial=razonSocial;
        this.obligadoContabilidad=obligadoContabilidad;
        this.especial=especial;
        this.estado=estado;
        this.estacion=estacion;
        this.grupoCliente=grupoCliente;
        this.tipoContribuyente=tipoContribuyente;
        this.direccion=direccion;
        this.etiqueta=etiqueta;
        this.referencia = referencia;
        this.latitudgeo = latitudgeo;
        this.longitudgeo = longitudgeo;
        this.montoFinanciamiento = montoFinanciamiento;
        this.formaPago = formaPago;
        this.plazoCredito = plazoCredito;
        this.ubicacion = ubicacion;
        this.genero=genero;
        this.estadoCivil=estadoCivil;
        this.calificacionCliente=calificacionCliente;
        this.origenIngreso=origenIngreso;
        this.segmento=segmento;
    }

    public Cliente(List<String> datos){
        super(null);
        tipoIdentificacion=datos.get(0)== null ? null: new TipoIdentificacion(Long.parseLong(datos.get(0)));
        identificacion=datos.get(1)== null ? null: datos.get(1);
        razonSocial=datos.get(2)== null ? null: datos.get(2);
        especial=datos.get(3)== null ? null: datos.get(3);
        estado= datos.get(4)== null ? null: datos.get(4);
        tipoContribuyente= datos.get(6)== null ? null: new TipoContribuyente((long) Double.parseDouble(datos.get(6)));
        estacion= datos.get(7)== null ? null: new Estacion((long) Double.parseDouble(datos.get(7)));
        grupoCliente= datos.get(8)== null ? null: new GrupoCliente((long) Double.parseDouble(datos.get(8)));
        direccion= datos.get(9)== null ? null: datos.get(9);
        etiqueta= datos.get(10)== null ? null: datos.get(10);
        genero=datos.get(11)== null ? null:new Genero((long) Double.parseDouble(datos.get(11)));
        estadoCivil=datos.get(12)== null ? null:new EstadoCivil((long) Double.parseDouble(datos.get(12)));
        calificacionCliente=datos.get(13)== null ? null:new CalificacionCliente((long) Double.parseDouble(datos.get(13)));
        origenIngreso=datos.get(14)== null ? null:new OrigenIngreso((long) Double.parseDouble(datos.get(14)));
        segmento=datos.get(15)== null ? null:new Segmento((long) Double.parseDouble(datos.get(15)));
    }
    
    public TipoContribuyente getTipoContribuyente() {
		return tipoContribuyente;
	}
    
    public TipoIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
	}

    public String getIdentificacion() {
        return identificacion;
    }

    public String getRazonSocial() {
		return razonSocial;
	}
    
    public String getObligadoContabilidad() {
		return obligadoContabilidad;
	}

    public String getEspecial() {
		return especial;
	}

    public String getEstado() {
        return estado;
    }

    public Estacion getEstacion() {
		return estacion;
	}

    public GrupoCliente getGrupoCliente() {
		return grupoCliente;
	}


    public String getDireccion() {
		return direccion;
	}

    public String getEtiqueta() {
        return etiqueta;
    }
    
    public String getReferencia() {
		return referencia;
	}
    
    public double getLongitudgeo() {
		return longitudgeo;
	}
    
    public double getLatitudgeo() {
		return latitudgeo;
	}

    public double getMontoFinanciamiento() {
        return montoFinanciamiento;
    }
    public FormaPago getFormaPago() {
        return formaPago;
    }
    public PlazoCredito getPlazoCredito() {
        return plazoCredito;
    }

    public Ubicacion getUbicacion() {
		return ubicacion;
	}

    public Genero getGenero() {
        return genero;
    }

    public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

    public CalificacionCliente getCalificacionCliente() {
		return calificacionCliente;
	}

    public OrigenIngreso getOrigenIngreso() {
		return origenIngreso;
	}
    
    public Segmento getSegmento() {
		return segmento;
	}

    @JsonManagedReference
    public List<Dependiente> getDependientes() {return dependientes;}
    
    @JsonManagedReference
    public List<Celular> getCelulares() {
        return celulares;
    }
    @JsonManagedReference
    public List<Telefono> getTelefonos() { return telefonos; }
    @JsonManagedReference
    public List<Correo> getCorreos() {
        return correos;
    }
    @JsonManagedReference
    public List<RetencionCliente> getRetencionesCliente() {
		return retencionesCliente;
	}

    public void setEspecial(String especial) {
		this.especial = especial;
	}
    
    public void setTipoContribuyente(TipoContribuyente tipoContribuyente) {
		this.tipoContribuyente = tipoContribuyente;
    }

    public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
    
    public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

    public void setMontoFinanciamiento(double montoFinanciamiento) {
        this.montoFinanciamiento = montoFinanciamiento;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
    
    public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

    public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setGrupoCliente(GrupoCliente grupoCliente) {
		this.grupoCliente = grupoCliente;
	}

    public void setOrigenIngreso(OrigenIngreso origenIngreso) {
		this.origenIngreso = origenIngreso;
	}
    
    public void setSegmento(Segmento segmento) {
		this.segmento = segmento;
	}

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

	public void setPlazoCredito(PlazoCredito plazoCredito) {
        this.plazoCredito = plazoCredito;
    }

    public void setEstado(String estado) {
		this.estado = estado;
	}
      
    public void normalizar(){
        if(this.tipoIdentificacion == null) this.tipoIdentificacion = new TipoIdentificacion();
        if(this.tipoContribuyente == null) this.tipoContribuyente = new TipoContribuyente();
        if(this.estacion == null) this.estacion = new Estacion();
        if(this.grupoCliente == null) this.grupoCliente = new GrupoCliente();
        if(this.formaPago == null) this.formaPago = new FormaPago();
        if(this.plazoCredito == null) this.plazoCredito = new PlazoCredito();
        if(this.ubicacion == null) this.ubicacion = new Ubicacion();
        if(this.genero == null) this.genero = new Genero();
        if(this.estadoCivil == null) this.estadoCivil = new EstadoCivil();
        if(this.calificacionCliente == null) this.calificacionCliente = new CalificacionCliente();
        if(this.origenIngreso == null) this.origenIngreso = new OrigenIngreso();
        if(this.segmento == null) this.segmento = new Segmento();
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
