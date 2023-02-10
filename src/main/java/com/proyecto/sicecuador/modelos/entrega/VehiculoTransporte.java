package com.proyecto.sicecuador.modelos.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vehiculo_transporte")
@Data
@AllArgsConstructor
public class VehiculoTransporte extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
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
    @Column(name = "estado", nullable = true)
    private String estado;

    public VehiculoTransporte(long id){
        super(id);
    }
    public VehiculoTransporte(){
        super();
        this.codigo = Constantes.vacio;
        this.placa = Constantes.vacio;
        this.numero = Constantes.vacio;
        this.marca = Constantes.vacio;
        this.modelo = Constantes.vacio;
        this.anio = Constantes.vacio;
        this.cilindraje = Constantes.vacio;
        this.clase = Constantes.vacio;
        this.color = Constantes.vacio;
        this.fabricacion = Constantes.vacio;
        this.estado = Constantes.activo;
    }
}
