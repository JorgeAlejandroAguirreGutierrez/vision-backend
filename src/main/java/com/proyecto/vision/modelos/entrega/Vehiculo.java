package com.proyecto.vision.modelos.entrega;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.usuario.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_vehiculo;

@Entity
@Table(name = tabla_vehiculo)
@Getter
@Setter
@AllArgsConstructor
public class Vehiculo extends Entidad {
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
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    public Vehiculo(long id){
        super(id);
    }
    public Vehiculo(){
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
        this.estado = Constantes.estadoActivo;
    }
}
