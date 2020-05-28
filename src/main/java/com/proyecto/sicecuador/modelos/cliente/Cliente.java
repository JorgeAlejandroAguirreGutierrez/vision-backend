package com.proyecto.sicecuador.modelos.cliente;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.usuario.PuntoVenta;
import com.proyecto.sicecuador.otros.cliente.ClienteUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cliente")
@EntityListeners({ClienteUtil.class})
public class Cliente extends Entidad {
    @Column(name = "tipo_identificacion", nullable = false)
    private String tipo_identificacion;
    @Column(name = "identificacion", nullable = false)
    private String identificacion;
    @Column(name = "razon_social", nullable = false)
    private String razon_social;
    @Column(name = "especial", nullable = false)
    private boolean especial;
    @Column(name = "estado", nullable = false)
    private boolean estado;
    @Column(name = "eliminado", nullable = false)
    private boolean eliminado;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tipo_contribuyente_id", nullable = false)
    private TipoContribuyente tipo_contribuyente;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "punto_venta_id", nullable = false)
    private PuntoVenta punto_venta;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "grupo_cliente_id", nullable = false)
    private GrupoCliente grupo_cliente;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "direccion_id", nullable = true)
    private Direccion direccion;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "financiamiento_id", nullable = true)
    private Financiamiento financiamiento;
    @ManyToOne(cascade = CascadeType.MERGE, optional = true)
    @JoinColumn(name = "genero_id", nullable = true)
    private Genero genero;
    @ManyToOne(cascade = CascadeType.MERGE,optional = true)
    @JoinColumn(name = "estado_civil_id", nullable = true)
    private EstadoCivil estado_civil;
    @ManyToOne(cascade = CascadeType.MERGE, optional = true)
    @JoinColumn(name = "categoria_cliente_id", nullable = true)
    private CategoriaCliente categoria_cliente;
    @ManyToOne(cascade = CascadeType.MERGE, optional = true)
    @JoinColumn(name = "origen_ingreso_id", nullable = true)
    private OrigenIngreso origen_ingreso;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<Auxiliar> auxiliares;
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<Telefono> telefonos;
    @OneToMany(cascade ={CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<Celular> celulares;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<Correo> correos;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private List<RetencionCliente> retenciones_cliente;

    public Cliente(){

    }

    public Cliente(String tipo_identificacion, TipoContribuyente tipo_contribuyente){
        this.tipo_identificacion=tipo_identificacion;
        this.tipo_contribuyente=tipo_contribuyente;
    }

    public Cliente(long id){
        super(id);
    }
    public Cliente(String razon_social){
        super(0);
        this.razon_social=razon_social;
    }

    public Cliente(String codigo, String tipo_identificacion, String identificacion,
                   String razon_social, boolean especial, boolean estado, boolean eliminado,
                   PuntoVenta punto_venta, GrupoCliente grupo_cliente, TipoContribuyente tipo_contribuyente,
                   Direccion direccion, Financiamiento financiamiento, Genero genero, EstadoCivil estado_civil,
                   CategoriaCliente categoria_cliente, OrigenIngreso origen_ingreso){
        super(codigo);
        this.tipo_identificacion=tipo_identificacion;
        this.identificacion=identificacion;
        this.razon_social=razon_social;
        this.especial=especial;
        this.estado=estado;
        this.eliminado=eliminado;
        this.punto_venta=punto_venta;
        this.grupo_cliente=grupo_cliente;
        this.tipo_contribuyente=tipo_contribuyente;
        this.direccion=direccion;
        this.financiamiento=financiamiento;
        this.genero=genero;
        this.estado_civil=estado_civil;
        this.categoria_cliente=categoria_cliente;
        this.origen_ingreso=origen_ingreso;
    }
    public String getTipo_identificacion() {
        return tipo_identificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getRazon_social() {
        return razon_social;
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

    public PuntoVenta getPunto_venta() {
        return punto_venta;
    }

    public GrupoCliente getGrupo_cliente() {
        return grupo_cliente;
    }

    public TipoContribuyente getTipo_contribuyente() {
        return tipo_contribuyente;
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

    public EstadoCivil getEstado_civil() {
        return estado_civil;
    }

    public CategoriaCliente getCategoria_cliente() {
        return categoria_cliente;
    }

    public OrigenIngreso getOrigen_ingreso() {
        return origen_ingreso;
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
    public List<RetencionCliente> getRetenciones_cliente() {
        return retenciones_cliente;
    }

    public void setEspecial(boolean especial) {
        this.especial = especial;
    }

    public void setTipo_identificacion(String tipo_identificacion) {
        this.tipo_identificacion = tipo_identificacion;
    }

    public void setTipo_contribuyente(TipoContribuyente tipo_contribuyente) {
        this.tipo_contribuyente = tipo_contribuyente;
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

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public void setCategoria_cliente(CategoriaCliente categoria_cliente) {
        this.categoria_cliente = categoria_cliente;
    }

    public void setEstado_civil(EstadoCivil estado_civil) {
        this.estado_civil = estado_civil;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public void setGrupo_cliente(GrupoCliente grupo_cliente) {
        this.grupo_cliente = grupo_cliente;
    }

    public void setOrigen_ingreso(OrigenIngreso origen_ingreso) {
        this.origen_ingreso = origen_ingreso;
    }

    public void setPunto_venta(PuntoVenta punto_venta) {
        this.punto_venta = punto_venta;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void normalizar(){
        if (this.direccion.getUbicacion().getId()==0) this.direccion.setUbicacion(null);
        if (this.financiamiento.getForma_pago().getId()==0) this.financiamiento.setForma_pago(null);
        if (this.financiamiento.getTipo_pago().getId()==0) this.financiamiento.setTipo_pago(null);
        if (this.financiamiento.getPlazo_credito() != null && this.financiamiento.getPlazo_credito().getId()==0) this.financiamiento.setPlazo_credito(null);
        if (this.categoria_cliente.getId()==0) this.categoria_cliente=null;
        if (this.genero.getId()==0) this.genero=null;
        if (this.estado_civil.getId()==0) this.estado_civil=null;
        if (this.origen_ingreso.getId()==0) this.origen_ingreso=null;
        for (int i=0; i<retenciones_cliente.size(); i++){
            if (retenciones_cliente.get(i).getTipo_retencion().getId()==0){
                retenciones_cliente.get(i).setTipo_retencion(null);
            }
        }
        for (int i=0; i<auxiliares.size(); i++){
            if (auxiliares.get(i).getDireccion().getUbicacion().getId()==0) auxiliares.get(i).getDireccion().setUbicacion(null);
        }
    }
}
