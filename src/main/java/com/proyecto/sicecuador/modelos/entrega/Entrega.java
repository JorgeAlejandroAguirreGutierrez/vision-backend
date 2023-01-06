package com.proyecto.sicecuador.modelos.entrega;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "entrega")
public class Entrega extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
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
    @Column(name = "inhabilitar", nullable = true)
    private String inhabilitar;
    
    @ManyToOne
    @JoinColumn(name = "ubicacion_id", nullable = true)
    private Ubicacion ubicacion;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
    @ManyToOne
    @JoinColumn(name = "transportista_id", nullable = true)
    private Transportista transportista;


    public Entrega(){

    }

    public Entrega(long id){
        super(id);
    }

    public Entrega(List<String> datos){
        numero=datos.get(0)== null ? null: datos.get(0);
        fecha=datos.get(1)== null ? null: new Date(datos.get(1));
        referencia=datos.get(2)== null ? null: datos.get(2);
        longitudgeo=datos.get(3)== null ? null: datos.get(3);
        latitudgeo=datos.get(4)== null ? null: datos.get(4);
        inhabilitar=datos.get(5)== null ? null: datos.get(5);
        direccion=datos.get(6)== null ? null: datos.get(6);
        factura=datos.get(7)== null ? null:new Factura((long) Double.parseDouble(datos.get(7)));
        transportista=datos.get(8)== null ? null:new Transportista((long) Double.parseDouble(datos.get(8)));
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
    
    public String getTelefono() {
		return telefono;
	}
    
    public String getCelular() {
		return celular;
	}
    
    public String getCorreo() {
		return correo;
	}

    public Factura getFactura() {
        return factura;
    }

    public Transportista getTransportista() {
        return transportista;
    }

    public String getDireccion() {
		return direccion;
	}
    
    public Ubicacion getUbicacion() {
		return ubicacion;
	}
    
    public String getInhabilitar() {
		return inhabilitar;
	}
}
