package com.proyecto.sicecuador.modelos.compra;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.*;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import static com.proyecto.sicecuador.Constantes.tabla_proveedor;

@Entity
@Table(name = tabla_proveedor)
@Getter
@Setter
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

    public Proveedor(long id){
        super(id);
    }

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
        this.telefonosProveedor = Collections.emptyList();
        this.celularesProveedor = Collections.emptyList();
        this.correosProveedor = Collections.emptyList();
    }
    public void normalizar(){
        if(this.tipoIdentificacion == null) this.tipoIdentificacion = new TipoIdentificacion();
        if(this.tipoContribuyente == null) this.tipoContribuyente = new TipoContribuyente();
        if(this.grupoProveedor == null) this.grupoProveedor = new GrupoProveedor();
        if(this.formaPago == null) this.formaPago = new FormaPago();
        if(this.plazoCredito == null) this.plazoCredito = new PlazoCredito();
        if(this.ubicacion == null) this.ubicacion = new Ubicacion();
        if(this.telefonosProveedor == null) this.telefonosProveedor = Collections.emptyList();
        if(this.telefonosProveedor.isEmpty()) this.telefonosProveedor.add(new TelefonoProveedor());
        if(this.celularesProveedor == null) this.celularesProveedor = Collections.emptyList();
        if(this.celularesProveedor.isEmpty()) this.celularesProveedor.add(new CelularProveedor());
        if(this.correosProveedor == null) this.correosProveedor = Collections.emptyList();
        if(this.correosProveedor.isEmpty()) this.correosProveedor.add(new CorreoProveedor());
    }
}
