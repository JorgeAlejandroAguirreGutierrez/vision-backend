package com.proyecto.sicecuador.modelos.cliente;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
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
    @Column(name = "identificacion")
    private String identificacion;
    @NotNull
    @NotEmpty
    @Column(name = "razon_social")
    private String razonSocial;
    @Column(name = "especial")
    private boolean especial;
    @Column(name = "estado")
    private String estado;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "tipo_identificacion_id", nullable = false)
    private TipoIdentificacion tipoIdentificacion;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "tipo_contribuyente_id", nullable = false)
    private TipoContribuyente tipoContribuyente;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "punto_venta_id")
    private PuntoVenta puntoVenta;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "grupo_cliente_id")
    private GrupoCliente grupoCliente;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "direccion_id", nullable = true)
    private Direccion direccion;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "financiamiento_id", nullable = true)
    private Financiamiento financiamiento;
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

    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<Dependiente> dependientes;
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<Telefono> telefonos;
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<Celular> celulares;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<Correo> correos;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<RetencionCliente> retencionesCliente;

    public Cliente(){

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

    public Cliente(String codigo, String identificacion, String razonSocial, boolean especial, String estado, 
    		boolean eliminado, TipoIdentificacion tipoIdentificacion, PuntoVenta puntoVenta, 
    		GrupoCliente grupoCliente, TipoContribuyente tipoContribuyente, Direccion direccion, 
    		Financiamiento financiamiento, Genero genero, EstadoCivil estadoCivil,
            CalificacionCliente calificacionCliente, OrigenIngreso origenIngreso, Segmento segmento){
        super(codigo);
        this.tipoIdentificacion=tipoIdentificacion;
        this.identificacion=identificacion;
        this.razonSocial=razonSocial;
        this.especial=especial;
        this.estado=estado;
        this.puntoVenta=puntoVenta;
        this.grupoCliente=grupoCliente;
        this.tipoContribuyente=tipoContribuyente;
        this.direccion=direccion;
        this.financiamiento=financiamiento;
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
        especial=datos.get(3)== null ? null: datos.get(3).equals("S") ? true : false;
        estado= datos.get(4)== null ? null: datos.get(4);
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

    public boolean isEspecial() {
        return especial;
    }

    public String getEstado() {
        return estado;
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

    public void setEspecial(boolean especial) {
        this.especial = especial;
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
        for (int i=0; i<dependientes.size(); i++){
            if (dependientes.get(i).getDireccion().getUbicacion().getId()==0) dependientes.get(i).getDireccion().setUbicacion(null);
        }
    }
}
