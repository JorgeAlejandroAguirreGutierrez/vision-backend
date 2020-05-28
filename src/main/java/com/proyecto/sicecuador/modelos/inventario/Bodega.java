package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.TipoPagoUtil;
import com.proyecto.sicecuador.otros.inventario.BodegaUtil;

import javax.persistence.*;

@Entity
@Table(name = "bodega")
@EntityListeners({BodegaUtil.class})
public class Bodega extends Entidad {

    public Bodega(){
        super();
    }
    public Bodega(long id){
        super(id);
    }
    public Bodega(String codigo){
        super(codigo);
    }
}
