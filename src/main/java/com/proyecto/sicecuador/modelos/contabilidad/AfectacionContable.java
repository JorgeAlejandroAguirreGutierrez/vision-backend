package com.proyecto.sicecuador.modelos.contabilidad;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "afectacion_contable")
public class AfectacionContable extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public AfectacionContable(){
        super();
    }

    public AfectacionContable(long id){
        super(id);
    }

    public AfectacionContable(String codigo, String descripcion, String abreviatura){
        super(codigo);
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
    }
    
    public AfectacionContable(List<String> datos){
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
