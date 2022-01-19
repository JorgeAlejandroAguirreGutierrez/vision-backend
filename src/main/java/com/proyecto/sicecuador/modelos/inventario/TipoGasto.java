package com.proyecto.sicecuador.modelos.inventario;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_gasto")
public class TipoGasto extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;

    public TipoGasto(){

    }

    public TipoGasto(long id){
        super(id);
    }

    public TipoGasto(String codigo, String nombre){
        super(codigo);
        this.nombre=nombre;
    }

    public TipoGasto(List<String> datos){

    }

    public String getNombre() {
        return nombre;
    }
}
