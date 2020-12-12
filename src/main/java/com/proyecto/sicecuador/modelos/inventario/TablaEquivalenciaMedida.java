package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.BodegaUtil;
import com.proyecto.sicecuador.otros.inventario.TablaEquivalenciaMedidaUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tabla_equivalencia_medida")
@EntityListeners({TablaEquivalenciaMedidaUtil.class})
public class TablaEquivalenciaMedida extends Entidad {
    @ManyToOne
    @JoinColumn(name = "medida1_id", nullable = true)
    private Medida medida1;
    @ManyToOne
    @JoinColumn(name = "medida2_id", nullable = true)
    private Medida medida2;
    @Column(name = "equivalencia", nullable = true)
    private double equivalencia;

    public TablaEquivalenciaMedida(){
    }
    public TablaEquivalenciaMedida(long id){
        super(id);
    }
    public TablaEquivalenciaMedida(String codigo){
        super(codigo);
    }
    public TablaEquivalenciaMedida(String codigo, Medida medida1, Medida medida2, double equivalencia){
        super(codigo);
        this.medida1=medida1;
        this.medida2=medida2;
        this.equivalencia=equivalencia;
    }

    public TablaEquivalenciaMedida(Medida medida1, Medida medida2){
        super(null);
        this.medida1=medida1;
        this.medida2=medida2;
    }

    public TablaEquivalenciaMedida(List<String>datos){

    }

    public Medida getMedida1() {
        return medida1;
    }

    public Medida getMedida2() {
        return medida2;
    }

    public double getEquivalencia() {
        return equivalencia;
    }
}
