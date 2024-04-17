package com.proyecto.vision.modelos.acceso;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.proyecto.vision.Constantes.tabla_nuevo;

@Entity
@Table(name = tabla_nuevo)
@Getter
@Setter
@AllArgsConstructor
public class Nuevo extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
    @Column(name = "nombre_comercial", nullable = true)
    private String nombreComercial;
    @Column(name = "genero", nullable = true)
    private String genero;
    @Column(name = "apodo", nullable = true)
    private String apodo;
    @Column(name = "obligado", nullable = true)
    private String obligado;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "especial", nullable = true)
    private String especial;
    @Column(name = "numero_resolucion_especial", nullable = true)
    private String numeroResolucionEspecial;
    @Column(name = "agente_retencion", nullable = true)
    private String agenteRetencion;
    @Column(name = "numero_agente_retencion", nullable = true)
    private String numeroAgenteRetencion;
    @Column(name = "regimen", nullable = true)
    private String regimen;
    @Column(name = "gran_contribuyente", nullable = true)
    private String granContribuyente;
    @Column(name = "artesano_calificado", nullable = true)
    private String artesanoCalificado;
    @Column(name = "facturacion_interna", nullable = true)
    private String facturacionInterna;
    @Column(name = "provincia", nullable = true)
    private String provincia;
    @Column(name = "canton", nullable = true)
    private String canton;
    @Column(name = "parroquia", nullable = true)
    private String parroquia;
    @Column(name = "celular", nullable = true)
    private String celular;
    @Column(name = "correo", nullable = true)
    private String correo;
    @Column(name = "establecimiento_sri", nullable = true)
    private String establecimientoSRI;
    @Column(name = "estacion_sri", nullable = true)
    private String estacionSRI;
    @Column(name = "contrasena_certificado", nullable = true)
    private String contrasenaCertificado;
    @Column(name = "contrasena_sri", nullable = true)
    private String contrasenaSRI;
    @Column(name = "secuencial_factura_venta", nullable = true)
    private long secuencialFacturaVenta;
    @Column(name = "secuencial_factura_interna", nullable = true)
    private long secuencialFacturaInterna;
    @Column(name = "secuencial_retencion", nullable = true)
    private long secuencialRetencion;
    @Column(name = "secuencial_guia_remision", nullable = true)
    private long secuencialGuiaRemision;
    @Column(name = "secuencial_nota_debito", nullable = true)
    private long secuencialNotaDebito;
    @Column(name = "secuencial_nota_credito", nullable = true)
    private long secuencialNotaCredito;

    public Nuevo(long id){
        super(id);
    }

    public Nuevo(){
        super();
        this.codigo = Constantes.vacio;
        this.identificacion = Constantes.vacio;
        this.razonSocial = Constantes.vacio;
        this.nombreComercial = Constantes.vacio;
        this.genero = Constantes.vacio;
        this.apodo = Constantes.vacio;
        this.obligado = Constantes.no;
        this.direccion = Constantes.vacio;
        this.especial = Constantes.no;
        this.numeroResolucionEspecial = Constantes.vacio;
        this.agenteRetencion = Constantes.no;
        this.numeroAgenteRetencion = Constantes.vacio;
        this.regimen = Constantes.vacio;
        this.granContribuyente = Constantes.no;
        this.artesanoCalificado = Constantes.no;
        this.facturacionInterna = Constantes.no;
        this.provincia = Constantes.vacio;
        this.canton = Constantes.vacio;
        this.parroquia = Constantes.vacio;
        this.celular = Constantes.vacio;
        this.correo = Constantes.vacio;
        this.establecimientoSRI = Constantes.vacio;
        this.estacionSRI = Constantes.vacio;
        this.contrasenaCertificado = Constantes.vacio;
        this.contrasenaSRI = Constantes.vacio;
        this.secuencialFacturaVenta = Constantes.uno;
        this.secuencialFacturaInterna = Constantes.uno;
        this.secuencialNotaCredito = Constantes.uno;
        this.secuencialNotaDebito = Constantes.uno;
        this.secuencialGuiaRemision = Constantes.uno;
        this.secuencialRetencion = Constantes.uno;
    }

    public void normalizar(){
    }
}
