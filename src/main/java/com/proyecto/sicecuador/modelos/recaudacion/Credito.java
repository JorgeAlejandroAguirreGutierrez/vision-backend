package com.proyecto.vision.modelos.recaudacion;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_credito;

@Entity
@Table(name = tabla_credito)
@Getter
@Setter
@AllArgsConstructor
public class Credito extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "saldo", nullable = true)
    private double saldo;
    @Column(name = "unidad_tiempo", nullable = true)
    private String unidadTiempo;
    @Column(name = "plazo", nullable = true)
    private long plazo;

    public Credito(long id){
        super(id);
    }
    public Credito(){
        super();
        this.codigo = Constantes.vacio;
        this.saldo = Constantes.cero;
        this.unidadTiempo = Constantes.vacio;
        this.plazo = Constantes.ceroId;
    }
}
