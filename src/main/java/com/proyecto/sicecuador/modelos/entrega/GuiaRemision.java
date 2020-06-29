package com.proyecto.sicecuador.modelos.entrega;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.otros.entrega.GuiaRemisionUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "guia_remision")
@EntityListeners({GuiaRemisionUtil.class})
public class GuiaRemision extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "referencia", nullable = true)
    private String referencia;
    @Column(name = "longitudgeo", nullable = true)
    private String longitudgeo;
    @Column(name = "latitudgeo", nullable = true)
    private String latitudgeo;
    @Column(name = "estado", nullable = true)
    private boolean estado;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH, CascadeType.MERGE}, optional = true)
    @JoinColumn(name = "direccion_id", nullable= true)
    private Direccion direccion;
    @NotNull(message = "Factura"+ Constantes.mensaje_validacion_not_null)
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "transportista_id", nullable = true)
    private Transportista transportista;

    public GuiaRemision(){

    }

    public GuiaRemision(long id){
        super(id);
    }

    public String getNumero() {
        return numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getLongitudgeo() {
        return longitudgeo;
    }

    public String getLatitudgeo() {
        return latitudgeo;
    }

    public boolean isEstado() {
        return estado;
    }

    public Factura getFactura() {
        return factura;
    }

    public Transportista getTransportista() {
        return transportista;
    }

    public Direccion getDireccion() {
        return direccion;
    }
}
