package com.proyecto.sicecuador.modelos.cliente;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.inventario.Segmento;
import com.proyecto.sicecuador.modelos.usuario.PuntoVenta;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente extends Entidad {
    @NotNull
    @NotEmpty
    @JsonProperty("tipo_identificacion")
    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;
    @NotNull
    @NotEmpty
    @JsonProperty("identificacion")
    @Column(name = "identificacion")
    private String identificacion;
    @NotNull
    @NotEmpty
    @JsonProperty("razon_social")
    @Column(name = "razon_social")
    private String razonSocial;
    @JsonProperty("especial")
    @Column(name = "especial")
    private boolean especial;
    @JsonProperty("estado")
    @Column(name = "estado")
    private String estado;
    @JsonProperty("eliminado")
    @Column(name = "eliminado")
    private boolean eliminado;
    @NotNull
    @JsonProperty("tipo_contribuyente")
    @ManyToOne
    @JoinColumn(name = "tipo_contribuyente_id", nullable = false)
    private TipoContribuyente tipoContribuyente;
    @NotNull
    @JsonProperty("punto_venta")
    @ManyToOne
    @JoinColumn(name = "punto_venta_id")
    private PuntoVenta puntoVenta;
    @NotNull
    @JsonProperty("grupo_cliente")
    @ManyToOne
    @JoinColumn(name = "grupo_cliente_id")
    private GrupoCliente grupoCliente;
    @JsonProperty("direccion")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "direccion_id", nullable = true)
    private Direccion direccion;
    @JsonProperty("financiamiento")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "financiamiento_id", nullable = true)
    private Financiamiento financiamiento;
    @JsonProperty("genero")
    @ManyToOne
    @JoinColumn(name = "genero_id", nullable = true)
    private Genero genero;
    @JsonProperty("estado_civil")
    @ManyToOne
    @JoinColumn(name = "estado_civil_id", nullable = true)
    private EstadoCivil estadoCivil;
    @JsonProperty("calificacion_cliente")
    @ManyToOne
    @JoinColumn(name = "calificacion_cliente_id", nullable = true)
    private CalificacionCliente calificacionCliente;
    @JsonProperty("origen_ingreso")
    @ManyToOne
    @JoinColumn(name = "origen_ingreso_id", nullable = true)
    private OrigenIngreso origenIngreso;
    @JsonProperty("segmento")
    @ManyToOne
    @JoinColumn(name = "segmento_id", nullable = true)
    private Segmento segmento;

    @JsonProperty("auxiliares")
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<Auxiliar> auxiliares;
    @JsonProperty("telefonos")
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<Telefono> telefonos;
    @JsonProperty("celulares")
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<Celular> celulares;
    @JsonProperty("correos")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<Correo> correos;
    @JsonProperty("retenciones_cliente")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<RetencionCliente> retencionesCliente;

    public Cliente(){

    }

    public Cliente(String tipoIdentificacion, TipoContribuyente tipoContribuyente){
        this.tipoIdentificacion=tipoIdentificacion;
        this.tipoContribuyente=tipoContribuyente;
    }

    public Cliente(long id){
        super(id);
    }
    public Cliente(String razon_social){
        super(0);
        this.razonSocial=razon_social;
    }

    public Cliente(String codigo, String tipo_identificacion, String identificacion,
                   String razon_social, boolean especial, String estado, boolean eliminado,
                   PuntoVenta punto_venta, GrupoCliente grupo_cliente, TipoContribuyente tipo_contribuyente,
                   Direccion direccion, Financiamiento financiamiento, Genero genero, EstadoCivil estado_civil,
                   CalificacionCliente calificacion_cliente, OrigenIngreso origen_ingreso, Segmento segmento){
        super(codigo);
        this.tipoIdentificacion=tipo_identificacion;
        this.identificacion=identificacion;
        this.razonSocial=razon_social;
        this.especial=especial;
        this.estado=estado;
        this.eliminado=eliminado;
        this.puntoVenta=punto_venta;
        this.grupoCliente=grupo_cliente;
        this.tipoContribuyente=tipo_contribuyente;
        this.direccion=direccion;
        this.financiamiento=financiamiento;
        this.genero=genero;
        this.estadoCivil=estado_civil;
        this.calificacionCliente=calificacion_cliente;
        this.origenIngreso=origen_ingreso;
        this.segmento=segmento;
    }

    public Cliente(List<String> datos){
        super(null);
        tipoIdentificacion=datos.get(0)== null ? null: datos.get(0);
        identificacion=datos.get(1)== null ? null: datos.get(1);
        razonSocial=datos.get(2)== null ? null: datos.get(2);
        especial=datos.get(3)== null ? null: datos.get(3).equals("S") ? true : false;
        estado= datos.get(4)== null ? null: datos.get(4);
        eliminado=datos.get(5)== null ? null: datos.get(5).equals("S") ? true : false;
        tipoContribuyente= datos.get(6)== null ? null: new TipoContribuyente((long) Double.parseDouble(datos.get(6)));
        puntoVenta= datos.get(7)== null ? null: new PuntoVenta((long) Double.parseDouble(datos.get(7)));
        grupoCliente= datos.get(8)== null ? null: new GrupoCliente((long) Double.parseDouble(datos.get(8)));
        direccion= datos.get(9)== null ? null: new Direccion((long) Double.parseDouble(datos.get(9)));
        financiamiento=datos.get(10)== null ? null:new Financiamiento((long) Double.parseDouble(datos.get(10)));
        genero=datos.get(11)== null ? null:new Genero((long) Double.parseDouble(datos.get(11)));
        estadoCivil=datos.get(12)== null ? null:new EstadoCivil((long) Double.parseDouble(datos.get(12)));
        calificacionCliente=datos.get(13)== null ? null:new CalificacionCliente((long) Double.parseDouble(datos.get(13)));
        origenIngreso=datos.get(14)== null ? null:new OrigenIngreso((long) Double.parseDouble(datos.get(14)));
                
        segmento=datos.get(15)== null ? null:new Segmento((long) Double.parseDouble(datos.get(15)));
    }
    
    public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}
    public TipoContribuyente getTipoContribuyente() {
		return tipoContribuyente;
	}

    public String getIdentificacion() {
        return identificacion;
    }

    public String getRazonSocial() {
		return razonSocial;
	}

    public boolean isEspecial() {
        return especial;
    }

    public String getEstado() {
        return estado;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

    public GrupoCliente getGrupoCliente() {
		return grupoCliente;
	}


    public Direccion getDireccion() {
        return direccion;
    }

    public Financiamiento getFinanciamiento() {
        return financiamiento;
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
    public List<Auxiliar> getAuxiliares() {return auxiliares;}
    
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

    public void setEspecial(boolean especial) {
        this.especial = especial;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
    
    public void setTipoContribuyente(TipoContribuyente tipoContribuyente) {
		this.tipoContribuyente = tipoContribuyente;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void setFinanciamiento(Financiamiento financiamiento) {
        this.financiamiento = financiamiento;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public void setCalificacionCliente(CalificacionCliente calificacionaCliente) {
		this.calificacionCliente = calificacionCliente;
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

    public void setEstado(String estado) {
		this.estado = estado;
	}
    
    
    public void normalizar(){
        if (this.direccion.getUbicacion().getId()==0) this.direccion.setUbicacion(null);
        if (this.financiamiento.getFormaPago().getId()==0) this.financiamiento.setFormaPago(null);
        if (this.financiamiento.getTipoPago().getId()==0) this.financiamiento.setTipoPago(null);
        if (this.financiamiento.getPlazoCredito() != null && this.financiamiento.getPlazoCredito().getId()==0) this.financiamiento.setPlazoCredito(null);
        if (this.calificacionCliente.getId()==0) this.calificacionCliente=null;
        if (this.genero.getId()==0) this.genero=null;
        if (this.estadoCivil.getId()==0) this.estadoCivil=null;
        if (this.origenIngreso.getId()==0) this.origenIngreso=null;
      //  if (this.segmento.getId()==0) this.segmento=null;
        
        for (int i=0; i<retencionesCliente.size(); i++){
            if (retencionesCliente.get(i).getTipoRetencion().getId()==0){
                retencionesCliente.get(i).setTipoRetencion(null);
            }
        }
        for (int i=0; i<auxiliares.size(); i++){
            if (auxiliares.get(i).getDireccion().getUbicacion().getId()==0) auxiliares.get(i).getDireccion().setUbicacion(null);
        }
    }
}
