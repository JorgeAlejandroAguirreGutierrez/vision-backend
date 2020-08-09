package com.proyecto.sicecuador.modelos.cliente;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.GeneroUtil;
import com.proyecto.sicecuador.otros.cliente.TipoPagoUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_pago")
@EntityListeners({TipoPagoUtil.class})
public class TipoPago extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public TipoPago(){
        super();
    }

    public TipoPago(long id) {
        super(id);
    }

    public TipoPago(String codigo, String descripcion, String abreviatura){
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
    }

    public TipoPago(List<String> datos){
        descripcion=datos.get(0)== null ? null: datos.get(0);
        abreviatura=datos.get(1)== null ? null: datos.get(1);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
