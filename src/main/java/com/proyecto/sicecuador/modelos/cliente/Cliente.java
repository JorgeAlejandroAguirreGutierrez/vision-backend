package com.proyecto.sicecuador.modelos.cliente;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
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
    private boolean estado;
    @JsonProperty("eliminado")
    @Column(name = "eliminado")
    private boolean eliminado;
    @NotNull
    @ManyToOne
    @JsonProperty("tipo_contribuyente")
    @JoinColumn(name = "tipo_contribuyente_id", nullable = false)
    private TipoContribuyente tipoContribuyente;
    @NotNull
    @ManyToOne
    @JsonProperty("punto_venta")
    @JoinColumn(name = "punto_venta_id")
    private PuntoVenta puntoVenta;
    @NotNull
    @ManyToOne
    @JsonProperty("grupo_cliente")
    @JoinColumn(name = "grupo_cliente_id")
    private GrupoCliente grupoCliente;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("direccion")
    @JoinColumn(name = "direccion_id", nullable = true)
    private Direccion direccion;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JsonProperty("financiamiento")
    @JoinColumn(name = "financiamiento_id", nullable = true)
    private Financiamiento financiamiento;
    @ManyToOne
    @JsonProperty("genero")
    @JoinColumn(name = "genero_id", nullable = true)
    private Genero genero;
    @ManyToOne
    @JsonProperty("estado_civil")
    @JoinColumn(name = "estado_civil_id", nullable = true)
    private EstadoCivil estadoCivil;
    @ManyToOne
    @JsonProperty("categoria_cliente")
    @JoinColumn(name = "categoria_cliente_id", nullable = true)
    private CategoriaCliente categoriaCliente;
    @ManyToOne
    @JsonProperty("origen_ingreso")
    @JoinColumn(name = "origen_ingreso_id", nullable = true)
    private OrigenIngreso origenIngreso;
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonProperty("auxiliares")
    @JoinColumn(name = "cliente_id")
    private List<Auxiliar> auxiliares;
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonProperty("telefonos")
    @JoinColumn(name = "cliente_id")
    private List<Telefono> telefonos;
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonProperty("celulares")
    @JoinColumn(name = "cliente_id")
    private List<Celular> celulares;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonProperty("correos")
    @JoinColumn(name = "cliente_id")
    private List<Correo> correos;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonProperty("retenciones_cliente")
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
                   String razon_social, boolean especial, boolean estado, boolean eliminado,
                   PuntoVenta punto_venta, GrupoCliente grupo_cliente, TipoContribuyente tipo_contribuyente,
                   Direccion direccion, Financiamiento financiamiento, Genero genero, EstadoCivil estado_civil,
                   CategoriaCliente categoria_cliente, OrigenIngreso origen_ingreso){
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
        this.categoriaCliente=categoria_cliente;
        this.origenIngreso=origen_ingreso;
    }

    public Cliente(List<String> datos){
        super(null);
        tipoIdentificacion=datos.get(0)== null ? null: datos.get(0);
        identificacion=datos.get(1)== null ? null: datos.get(1);
        razonSocial=datos.get(2)== null ? null: datos.get(2);
        especial=datos.get(3)== null ? null: datos.get(3).equals("S") ? true : false;
        estado= datos.get(4)== null ? null: datos.get(4).equals("S") ? true : false;
        eliminado=datos.get(5)== null ? null: datos.get(5).equals("S") ? true : false;
        tipoContribuyente= datos.get(6)== null ? null: new TipoContribuyente((long) Double.parseDouble(datos.get(6)));
        puntoVenta= datos.get(7)== null ? null: new PuntoVenta((long) Double.parseDouble(datos.get(7)));
        grupoCliente= datos.get(8)== null ? null: new GrupoCliente((long) Double.parseDouble(datos.get(8)));
        direccion= datos.get(9)== null ? null: new Direccion((long) Double.parseDouble(datos.get(9)));
        financiamiento=datos.get(10)== null ? null:new Financiamiento((long) Double.parseDouble(datos.get(10)));
        genero=datos.get(11)== null ? null:new Genero((long) Double.parseDouble(datos.get(11)));
        estadoCivil=datos.get(12)== null ? null:new EstadoCivil((long) Double.parseDouble(datos.get(12)));
        categoriaCliente=datos.get(13)== null ? null:new CategoriaCliente((long) Double.parseDouble(datos.get(13)));
        origenIngreso=datos.get(14)== null ? null:new OrigenIngreso((long) Double.parseDouble(datos.get(14)));
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

    public boolean isEstado() {
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

    public CategoriaCliente getCategoriaCliente() {
		return categoriaCliente;
	}

    public OrigenIngreso getOrigenIngreso() {
		return origenIngreso;
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

    public void setCategoriaCliente(CategoriaCliente categoriaCliente) {
		this.categoriaCliente = categoriaCliente;
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
    
    public void setEstado(boolean estado) {
		this.estado = estado;
	}
    
    
    public void normalizar(){
        if (this.direccion.getUbicacion().getId()==0) this.direccion.setUbicacion(null);
        if (this.financiamiento.getFormaPago().getId()==0) this.financiamiento.setFormaPago(null);
        if (this.financiamiento.getTipoPago().getId()==0) this.financiamiento.setTipoPago(null);
        if (this.financiamiento.getPlazoCredito() != null && this.financiamiento.getPlazoCredito().getId()==0) this.financiamiento.setPlazoCredito(null);
        if (this.categoriaCliente.getId()==0) this.categoriaCliente=null;
        if (this.genero.getId()==0) this.genero=null;
        if (this.estadoCivil.getId()==0) this.estadoCivil=null;
        if (this.origenIngreso.getId()==0) this.origenIngreso=null;
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
