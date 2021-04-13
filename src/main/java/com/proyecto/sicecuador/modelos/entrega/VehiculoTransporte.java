package com.proyecto.sicecuador.modelos.entrega;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehiculo_transporte")
public class VehiculoTransporte extends Entidad {
	@JsonProperty("placa")
    @Column(name = "placa", nullable = true)
    private String placa;
	@JsonProperty("numero")
    @Column(name = "numero", nullable = true)
    private String numero;
	@JsonProperty("marca")
    @Column(name = "marca", nullable = true)
    private String marca;
	@JsonProperty("modelo")
    @Column(name = "modelo", nullable = true)
    private String modelo;
	@JsonProperty("anio")
    @Column(name = "anio", nullable = true)
    private String anio;
	@JsonProperty("cilindraje")
    @Column(name = "cilindraje", nullable = true)
    private String cilindraje;
	@JsonProperty("clase")
    @Column(name = "clase", nullable = true)
    private String clase;
	@JsonProperty("color")
    @Column(name = "color", nullable = true)
    private String color;
	@JsonProperty("fabricacion")
    @Column(name = "fabricacion", nullable = true)
    private String fabricacion;
	@JsonProperty("activo")
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
    public VehiculoTransporte(List<String> datos) {
        placa=datos.get(0)== null ? null: datos.get(0);
        numero=datos.get(1)== null ? null: datos.get(1);
        marca=datos.get(2)== null ? null: datos.get(2);
        modelo=datos.get(3)== null ? null: datos.get(3);
        anio=datos.get(4)== null ? null: datos.get(4);
        cilindraje=datos.get(5)== null ? null: datos.get(5);
        clase=datos.get(6)== null ? null: datos.get(6);
        color=datos.get(7)== null ? null: datos.get(7);
        fabricacion=datos.get(8)== null ? null: datos.get(8);
        activo=datos.get(9)== null ? null: datos.get(9).equals("S") ? true : false;
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
}
