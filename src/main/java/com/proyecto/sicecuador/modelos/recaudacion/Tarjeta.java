package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.recaudacion.TarjetaUtil;

import javax.persistence.*;

@Entity
@Table(name = "tarjeta")
@EntityListeners({TarjetaUtil.class})
public class Tarjeta extends Entidad {
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;

    public Tarjeta(){
    }

    public Tarjeta(long id){
        super(id);
    }

    public Tarjeta(String codigo, String tipo, String nombre, Banco banco){
        super(codigo);
        this.tipo=tipo;
        this.nombre=nombre;
        this.banco=banco;
    }
    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public Banco getBanco() {
        return banco;
    }
}
