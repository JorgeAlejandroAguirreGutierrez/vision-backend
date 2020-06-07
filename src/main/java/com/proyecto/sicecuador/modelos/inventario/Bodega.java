package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.cliente.TipoPagoUtil;
import com.proyecto.sicecuador.otros.inventario.BodegaUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bodega")
@EntityListeners({BodegaUtil.class})
public class Bodega extends Entidad {

    /*@OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bodega_id")
    private List<Caracteristica> caracteristicas;*/


    public Bodega(){
    }
    public Bodega(long id){
        super(id);
    }
    public Bodega(String codigo){
        super(codigo);
    }

    /*@JsonManagedReference
    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }*/

}
