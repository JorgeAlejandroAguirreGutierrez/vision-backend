package com.proyecto.sicecuador.modelos.entrega;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
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
    @Column(name = "direccion_cliente", nullable = true)
    private Date direccion_cliente;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "telefono", nullable = true)
    private String celular;
    @Column(name = "correo", nullable = true)
    private String correo;
    @Column(name = "referencia", nullable = true)
    private String referencia;
    @Column(name = "longitudgeo", nullable = true)
    private String longitudgeo;
    @Column(name = "latitudgeo", nullable = true)
    private String latitudgeo;
    @Column(name = "estado", nullable = true)
    private boolean estado;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;
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
}
