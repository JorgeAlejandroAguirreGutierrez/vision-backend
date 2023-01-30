package com.proyecto.sicecuador.modelos.recaudacion;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "credito")
public class Credito extends Entidad {
    @Column(name = "saldo", nullable = true)
    private double saldo;
    @Column(name = "unidad_tiempo", nullable = true)
    private String unidadTiempo;
    @Column(name = "plazo", nullable = true)
    private long plazo;

    public Credito(){
        super();
        this.saldo = Constantes.cero;
        this.unidadTiempo = Constantes.vacio;
        this.plazo = Constantes.ceroId;
    }

    public Credito(long id){
        super(id);
    }

    public Credito(String codigo, double saldo, String unidadTiempo, int plazo){
        super(codigo);
        this.saldo=saldo;
        this.unidadTiempo = unidadTiempo;
        this.plazo = plazo;
    }

    public Credito(List<String> datos){
    }
    public double getSaldo() {
        return saldo;
    }

    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    public long getPlazo() {
        return plazo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo;
    }
}
