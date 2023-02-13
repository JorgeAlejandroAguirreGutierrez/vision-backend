package com.proyecto.sicecuador.modelos.compra;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.*;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "proveedor")
@Data
@AllArgsConstructor
public class Proveedor extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
    @Column(name = "nombre_comercial", nullable = true)
    private String nombreComercial;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "referencia", nullable = true)
    private String referencia;
    @Column(name = "latitudgeo", nullable = true)
    private double latitudgeo;
    @Column(name = "longitudgeo", nullable = true)
    private double longitudgeo;
    @Column(name = "monto_financiamiento", nullable = true)
    private double montoFinanciamiento;
    @Column(name = "especial", nullable = true)
    private String especial;
    @Column(name = "obligado_contabilidad", nullable = true)
    private String obligadoContabilidad;
    @Column(name = "fantasma", nullable = true)
    private String fantasma;
    @Column(name = "relacionado", nullable = true)
    private String relacionado;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "tipo_identificacion_id", nullable = true)
    private TipoIdentificacion tipoIdentificacion;
    @ManyToOne
    @JoinColumn(name = "tipo_contribuyente_id", nullable = true)
    private TipoContribuyente tipoContribuyente;
    @ManyToOne
    @JoinColumn(name = "grupo_proveedor_id", nullable = true)
    private GrupoProveedor grupoProveedor;
    @ManyToOne
    @JoinColumn(name = "forma_pago_id", nullable = true)
    private FormaPago formaPago;
    @ManyToOne
    @JoinColumn(name = "plazo_credito_id", nullable = true)
    private PlazoCredito plazoCredito;
    @ManyToOne
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;
    @JsonManagedReference
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = true)
    private List<TelefonoProveedor> telefonosProveedor;
    @JsonManagedReference
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = true)
    private List<CelularProveedor> celularesProveedor;
    @JsonManagedReference
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = true)
    private List<CorreoProveedor> correosProveedor;

    public Proveedor(){
        super();
        this.identificacion = Constantes.vacio;
        this.razonSocial = Constantes.vacio;
        this.nombreComercial = Constantes.vacio;
        this.direccion = Constantes.vacio;
        this.referencia = Constantes.vacio;
        this.latitudgeo = Constantes.cero;
        this.longitudgeo = Constantes.cero;
        this.montoFinanciamiento = Constantes.cero;
        this.especial = Constantes.no;
        this.obligadoContabilidad = Constantes.no;
        this.fantasma = Constantes.no;
        this.estado = Constantes.activo;
        this.tipoIdentificacion = new TipoIdentificacion();
        this.tipoContribuyente = new TipoContribuyente();
        this.grupoProveedor = new GrupoProveedor();
        this.formaPago = new FormaPago();
        this.plazoCredito = new PlazoCredito();
        this.ubicacion = new Ubicacion();
        this.telefonosProveedor = Collections.emptyList();
        this.celularesProveedor = Collections.emptyList();
        this.correosProveedor = Collections.emptyList();
    }

	public Proveedor(long id){
        super(id);
    }

    public Proveedor(String razon_social){
        super(0);
        this.razonSocial=razon_social;
    }

    public Proveedor(TipoIdentificacion tipoIdentificacion, TipoContribuyente tipoContribuyente){
        super(0);
        this.tipoIdentificacion=tipoIdentificacion;
        this.tipoContribuyente=tipoContribuyente;
    }

    public Proveedor(String codigo, String identificacion, String razonSocial, String nombreComercial,
                     String direccion, String referencia, double latitudgeo, double longitudgeo, double montoFinanciamiento,
                     String especial, String obligadoContabilidad, String fantasma, String relacionado, String estado,
                     TipoIdentificacion tipoIdentificacion, TipoContribuyente tipoContribuyente,
                     GrupoProveedor grupoProveedor, FormaPago formaPago, PlazoCredito plazoCredito,
                     Ubicacion ubicacion){
        super();
        this.codigo = Constantes.vacio;
    	this.tipoIdentificacion = tipoIdentificacion;
        this.tipoContribuyente=tipoContribuyente;
    	this.identificacion = identificacion;
    	this.razonSocial = razonSocial;
    	this.nombreComercial = nombreComercial;
    	this.estado = estado;
    	this.direccion = direccion;
        this.referencia = referencia;
        this.latitudgeo = latitudgeo;
        this.longitudgeo = longitudgeo;
        this.montoFinanciamiento = montoFinanciamiento;
    	this.especial = especial;
        this.obligadoContabilidad = obligadoContabilidad;
    	this.fantasma = fantasma;
        this.relacionado = relacionado;
        this.grupoProveedor=grupoProveedor;
        this.formaPago = formaPago;
        this.plazoCredito = plazoCredito;
        this.ubicacion = ubicacion;
    }
    
    public Proveedor(List<String> datos) {
    	super();
        tipoIdentificacion=datos.get(0)== null ? null: new TipoIdentificacion(Long.parseLong(datos.get(0)));
        identificacion=datos.get(1)== null ? null: datos.get(1);
        razonSocial=datos.get(2)== null ? null: datos.get(2);
        nombreComercial=datos.get(3)== null ? null: datos.get(3);
        estado=datos.get(4)== null ? null: datos.get(4);
        direccion= datos.get(6)== null ? null: datos.get(6);
        especial= datos.get(7)== null ? null: datos.get(7);
        obligadoContabilidad = datos.get(8)== null ? null: datos.get(8);
        fantasma = datos.get(9)== null ? null: datos.get(9);
        relacionado = datos.get(10)== null ? null: datos.get(10);
        
    }

    public TipoIdentificacion getTipoIdentificacion() {
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
    
    public String getNombreComercial() {
    	return nombreComercial;
    }

    public String getEstado() {
        return estado;
    }

    public String getDireccion() {
        return direccion;
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

    public String getEspecial() {
        return especial;
    }

    public String getObligadoContabilidad() {
        return obligadoContabilidad;
    }

    public String getFantasma() {
        return fantasma;
    }

    public String getRelacionado() {
        return relacionado;
    }

    public GrupoProveedor getGrupoProveedor() {
        return grupoProveedor;
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

    @JsonManagedReference
    public List<CelularProveedor> getCelularesProveedor() {
        return celularesProveedor;
    }
    @JsonManagedReference
    public List<TelefonoProveedor> getTelefonosProveedor() { return telefonosProveedor; }
    @JsonManagedReference
    public List<CorreoProveedor> getCorreosProveedor() {
        return correosProveedor;
    }
    //@JsonManagedReference
    //public List<RetencionCliente> getRetencionesProveedor() {
    //    return retencionesProveedor;
    //}

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public void setTipoContribuyente(TipoContribuyente tipoContribuyente) {
        this.tipoContribuyente = tipoContribuyente;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
    
    public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
    
    public void setEstado(String estado) {
        this.estado = estado;
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

    public void setEspecial(String especial) {
        this.especial = especial;
    }
    
    public void setObligadoContabilidad(String obligadoContabilidad) {
		this.obligadoContabilidad = obligadoContabilidad;
	}

    public void setFantasma(String fantasma) {
        this.fantasma = fantasma;
    }

    public void setRelacionado(String relacionado) {
        this.relacionado = relacionado;
    }

    public void setGrupoProveedor(GrupoProveedor grupoProveedor) {
        this.grupoProveedor = grupoProveedor;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public void setPlazoCredito(PlazoCredito plazoCredito) {
        this.plazoCredito = plazoCredito;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void normalizar(){
        if(this.tipoIdentificacion == null) this.tipoIdentificacion = new TipoIdentificacion();
        if(this.tipoContribuyente == null) this.tipoContribuyente = new TipoContribuyente();
        if(this.grupoProveedor == null) this.grupoProveedor = new GrupoProveedor();
        if(this.formaPago == null) this.formaPago = new FormaPago();
        if(this.plazoCredito == null) this.plazoCredito = new PlazoCredito();
        if(this.ubicacion == null) this.ubicacion = new Ubicacion();
        if(this.telefonosProveedor == null) this.telefonosProveedor = Collections.emptyList();
        if(this.celularesProveedor == null) this.celularesProveedor = Collections.emptyList();
        if(this.correosProveedor == null) this.correosProveedor = Collections.emptyList();
    }
}
