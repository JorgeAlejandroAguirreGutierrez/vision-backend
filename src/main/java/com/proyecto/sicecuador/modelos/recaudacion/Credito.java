package com.proyecto.sicecuador.modelos.recaudacion;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "credito")
@Data
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
