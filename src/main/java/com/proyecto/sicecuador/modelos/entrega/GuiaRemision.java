package com.proyecto.sicecuador.modelos.entrega;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.otros.entrega.GuiaRemisionUtil;

import javax.persistence.*;
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
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "direccion_id", nullable = true)
    private Direccion direccion;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "transportista_id", nullable = true)
    private Transportista transportista;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "vehiculo_transporte_id", nullable = true)
    private VehiculoTransporte vehiculo_transporte;

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

    public Cliente getCliente() {
        return cliente;
    }

    public Transportista getTransportista() {
        return transportista;
    }

    public VehiculoTransporte getVehiculo_transporte() {
        return vehiculo_transporte;
    }

    public Direccion getDireccion() {
        return direccion;
    }
}
