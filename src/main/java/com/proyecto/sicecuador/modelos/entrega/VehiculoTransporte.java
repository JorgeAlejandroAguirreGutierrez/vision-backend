package com.proyecto.sicecuador.modelos.entrega;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.otros.entrega.VehiculoTransporteUtil;

import javax.persistence.*;

@Entity
@Table(name = "vehiculo_transporte")
@EntityListeners({VehiculoTransporteUtil.class})
public class VehiculoTransporte extends Entidad {
    @Column(name = "placa", nullable = true)
    private String placa;
    @Column(name = "numero", nullable = true)
    private String numero;
    @Column(name = "marca", nullable = true)
    private String marca;
    @Column(name = "modelo", nullable = true)
    private String modelo;
    @Column(name = "anio", nullable = true)
    private String anio;
    @Column(name = "cilindraje", nullable = true)
    private String cilindraje;
    @Column(name = "clase", nullable = true)
    private String clase;
    @Column(name = "color", nullable = true)
    private String color;
    @Column(name = "fabricacion", nullable = true)
    private String fabricacion;
    @Column(name = "activo", nullable = true)
    private boolean activo;
    @OneToOne
    private Transportista transportista;

    public VehiculoTransporte(){

    }

    public VehiculoTransporte(long id){
        super(id);
    }

    public VehiculoTransporte(String codigo, String placa, String numero, String marca, String modelo,
                              String anio, String cilindraje, String clase, String color, String fabricacion,
                              boolean activo){
        super(codigo);
        this.placa=placa;
        this.numero=numero;
        this.marca=marca;
        this.modelo=modelo;
        this.anio=anio;
        this.cilindraje=cilindraje;
        this.clase=clase;
        this.color=color;
        this.fabricacion=fabricacion;
        this.activo=activo;
    }
    public String getPlaca() {
        return placa;
    }

    public String getNumero() {
        return numero;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getAnio() {
        return anio;
    }

    public String getCilindraje() {
        return cilindraje;
    }

    public String getClase() {
        return clase;
    }

    public String getColor() {
        return color;
    }

    public String getFabricacion() {
        return fabricacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public Transportista getTransportista() {
        return transportista;
    }
}
