package com.proyecto.sicecuador.modelos.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static com.proyecto.sicecuador.Constantes.tabla_guia_remision;

@Entity
@Table(name = tabla_guia_remision)
@Data
@AllArgsConstructor
public class GuiaRemision extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "guia_numero", nullable = true)
    private String guiaNumero;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "referencia", nullable = true)
    private String referencia;
    @Column(name = "longitudgeo", nullable = true)
    private String longitudgeo;
    @Column(name = "latitudgeo", nullable = true)
    private String latitudgeo;
    @Column(name = "telefono", nullable = true)
    private String telefono;
    @Column(name = "celular", nullable = true)
    private String celular;
    @Column(name = "correo", nullable = true)
    private String correo;
    @Column(name = "opcion_guia", nullable = true)
    private String opcionGuia;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;
    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
    @ManyToOne
    @JoinColumn(name = "transportista_id", nullable = true)
    private Transportista transportista;

    public GuiaRemision(long id){
        super(id);
    }
    public GuiaRemision(){
        super();
        this.codigo = Constantes.vacio;
        this.guiaNumero = Constantes.vacio;
        this.fecha = new Date();
        this.direccion = Constantes.vacio;
        this.referencia = Constantes.vacio;
        this.longitudgeo = Constantes.vacio;
        this.latitudgeo = Constantes.vacio;
        this.telefono = Constantes.vacio;
        this.celular = Constantes.vacio;
        this.correo = Constantes.vacio;
        this.opcionGuia = Constantes.vacio;
        this.estado = Constantes.activo;
        this.ubicacion = new Ubicacion();
        this.factura = new Factura();
        this.transportista = new Transportista();
    }

    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.ubicacion == null) this.ubicacion = new Ubicacion();
        if(this.factura == null) this.factura = new Factura();
        if(this.transportista == null) this.transportista = new Transportista();
    }
}
