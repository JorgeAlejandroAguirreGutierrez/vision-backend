package com.proyecto.sicecuador.modelos.entrega;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.modelos.comprobante.Factura;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "guia_remision")
public class GuiaRemision extends Entidad {
	@JsonProperty("numero")
    @Column(name = "numero", nullable = true)
    private String numero;
	@JsonProperty("fecha")
    @Column(name = "fecha", nullable = true)
    private Date fecha;
	@JsonProperty("referencia")
    @Column(name = "referencia", nullable = true)
    private String referencia;
	@JsonProperty("longitudgeo")
    @Column(name = "longitudgeo", nullable = true)
    private String longitudgeo;
	@JsonProperty("latitudgeo")
    @Column(name = "latitudgeo", nullable = true)
    private String latitudgeo;
	@JsonProperty("estado")
    @Column(name = "estado", nullable = true)
    private boolean estado;
	@JsonProperty("inhabilitar")
    @Column(name = "inhabilitar", nullable = true)
    private boolean inhabilitar;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH, CascadeType.MERGE}, optional = true)
    @JsonProperty("direccion")
    @JoinColumn(name = "direccion_id", nullable= true)
    private Direccion direccion;
    @NotNull
    @ManyToOne
    @JsonProperty("factura")
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
    @ManyToOne
    @JsonProperty("transportista")
    @JoinColumn(name = "transportista_id", nullable = true)
    private Transportista transportista;


    public GuiaRemision(){

    }

    public GuiaRemision(long id){
        super(id);
    }

    public GuiaRemision(List<String> datos){
        numero=datos.get(0)== null ? null: datos.get(0);
        fecha=datos.get(1)== null ? null: new Date(datos.get(1));
        referencia=datos.get(2)== null ? null: datos.get(2);
        longitudgeo=datos.get(3)== null ? null: datos.get(3);
        latitudgeo=datos.get(4)== null ? null: datos.get(4);
        estado=datos.get(5)== null ? null: datos.get(5).equals("S") ? true : false;
        inhabilitar=datos.get(6)== null ? null: datos.get(6).equals("S") ? true : false;
        direccion=datos.get(7)== null ? null:new Direccion((long) Double.parseDouble(datos.get(7)));
        factura=datos.get(8)== null ? null:new Factura((long) Double.parseDouble(datos.get(8)));
        transportista=datos.get(9)== null ? null:new Transportista((long) Double.parseDouble(datos.get(9)));
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public boolean isInhabilitar() {
        return inhabilitar;
    }
}
