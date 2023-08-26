package com.proyecto.vision.modelos.entrega;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.modelos.venta.Factura;
import com.proyecto.vision.modelos.usuario.Sesion;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static com.proyecto.vision.Constantes.tabla_guia_remision;

@Entity
@Table(name = tabla_guia_remision)
@Getter
@Setter
@AllArgsConstructor
public class GuiaRemision extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "establecimiento", nullable = true)
    private String establecimiento;
    @Column(name = "punto_venta", nullable = true)
    private String puntoVenta;
    @Column(name = "secuencial", nullable = true)
    private String secuencial;
    @Column(name = "numero_comprobante", nullable = true)
    private String numeroComprobante;
    @Column(name = "codigo_numerico", nullable = true)
    private String codigoNumerico;
    @Column(name = "clave_acceso", nullable = true)
    private String claveAcceso;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "fecha_autorizacion", nullable = true)
    private Date fechaAutorizacion;
    @Column(name = "fecha_inicio_transporte", nullable = true)
    private Date fechaInicioTransporte;
    @Column(name = "fecha_fin_transporte", nullable = true)
    private Date fechaFinTransporte;
    @Column(name = "motivo_traslado", nullable = true)
    private String motivoTraslado;
    @Column(name = "ruta", nullable = true)
    private String ruta;
    @Column(name = "identificacion_destinatario", nullable = true)
    private String identificacionDestinatario;
    @Column(name = "razon_social_destinatario", nullable = true)
    private String razonSocialDestinatario;
    @Column(name = "direccion_destinatario", nullable = true)
    private String direccionDestinatario;
    @Column(name = "telefono_destinatario", nullable = true)
    private String telefonoDestinatario;
    @Column(name = "celular_destinatario", nullable = true)
    private String celularDestinatario;
    @Column(name = "correo_destinatario", nullable = true)
    private String correoDestinatario;
    @Column(name = "opcion_guia", nullable = true)
    private String opcionGuia;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "estado_sri", nullable = true)
    private String estadoSRI;
    @ManyToOne
    @JoinColumn(name = "tipo_comprobante_id", nullable = true)
    private TipoComprobante tipoComprobante;
    @ManyToOne
    @JoinColumn(name = "sesion_id", nullable = true)
    private Sesion sesion;
    @ManyToOne
    @JoinColumn(name = "transportista_id", nullable = true)
    private Transportista transportista;
    @ManyToOne
    @JoinColumn(name = "vehiculo_transporte_id", nullable = true)
    private Vehiculo vehiculo;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    public GuiaRemision(long id){
        super(id);
    }
    public GuiaRemision(){
        super();
        this.codigo = Constantes.vacio;
        this.secuencial = Constantes.vacio;
        this.codigoNumerico = Constantes.vacio;
        this.fecha = new Date();
        this.fechaInicioTransporte = new Date();
        this.fechaFinTransporte = new Date();
        this.motivoTraslado = Constantes.vacio;
        this.ruta = Constantes.vacio;
        this.identificacionDestinatario = Constantes.vacio;
        this.razonSocialDestinatario = Constantes.vacio;
        this.direccionDestinatario = Constantes.vacio;
        this.telefonoDestinatario = Constantes.vacio;
        this.celularDestinatario = Constantes.vacio;
        this.correoDestinatario = Constantes.vacio;
        this.opcionGuia = Constantes.vacio;
        this.estado = Constantes.vacio;
        this.estadoSRI =  Constantes.vacio;
    }

    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.fechaInicioTransporte == null) this.fechaInicioTransporte = new Date();
        if(this.fechaFinTransporte == null) this.fechaFinTransporte = new Date();
        if(this.factura == null) this.factura = new Factura();
        if(this.transportista == null) this.transportista = new Transportista();
    }
}
