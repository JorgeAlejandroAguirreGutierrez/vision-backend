package com.proyecto.sicecuador.modelos.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "entrega")
public class Entrega extends Entidad {
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

    public Entrega(){
        super();
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

    public Entrega(long id){
        super(id);
    }

    public Entrega(List<String> datos){
        guiaNumero=datos.get(0)== null ? null: datos.get(0);
        fecha=datos.get(1)== null ? null: new Date(datos.get(1));
        referencia=datos.get(2)== null ? null: datos.get(2);
        longitudgeo=datos.get(3)== null ? null: datos.get(3);
        latitudgeo=datos.get(4)== null ? null: datos.get(4);
        estado=datos.get(5)== null ? null: datos.get(5);
        direccion=datos.get(6)== null ? null: datos.get(6);
        factura=datos.get(7)== null ? null:new Factura((long) Double.parseDouble(datos.get(7)));
        transportista=datos.get(8)== null ? null:new Transportista((long) Double.parseDouble(datos.get(8)));
    }

    public String getGuiaNumero() {
		return guiaNumero;
	}
    
    public void setGuiaNumero(String guiaNumero) {
		this.guiaNumero = guiaNumero;
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
    
    public String getOpcionGuia() {
		return opcionGuia;
	}
    
    public String getEstado() {
		return estado;
	}
    
    public void setCelular(String celular) {
		this.celular = celular;
	}
    
    public void setCorreo(String correo) {
		this.correo = correo;
	}
    
    public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
    
    public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
    
    public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
    
    public void setOpcionGuia(String opcionGuia) {
		this.opcionGuia = opcionGuia;
	}
    
    public void setEstado(String estado) {
		this.estado = estado;
	}
    
    public void setTransportista(Transportista transportista) {
		this.transportista = transportista;
	}
    
    public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.ubicacion == null) this.ubicacion = new Ubicacion();
        if(this.factura == null) this.factura = new Factura();
        if(this.transportista == null) this.transportista = new Transportista();
    }
}
