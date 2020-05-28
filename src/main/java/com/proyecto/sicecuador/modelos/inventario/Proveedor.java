package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.ProveedorUtil;

import javax.persistence.*;

@Entity
@Table(name = "proveedor")
@EntityListeners({ProveedorUtil.class})
public class Proveedor extends Entidad {

    public Proveedor(long id){
        super(id);
    }
    public Proveedor(String codigo){
        super(codigo);
    }
    public Proveedor(){
        super();
    }
}
