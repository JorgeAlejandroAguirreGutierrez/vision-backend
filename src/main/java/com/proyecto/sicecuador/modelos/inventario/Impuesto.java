package com.proyecto.sicecuador.modelos.inventario;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "impuesto")
public class Impuesto extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "porcentaje", nullable = true)
    private double porcentaje;

    public Impuesto(){
        super();
    }

    public Impuesto(long id){
        super(id);
    }
    public Impuesto(double porcentaje){
        super();
        this.porcentaje=porcentaje;
    }

    public Impuesto(String codigo, String descripcion,double porcentaje){
        super(codigo);
        this.descripcion=descripcion;
        this.porcentaje=porcentaje;
    }

    public Impuesto(List<String> datos){
        descripcion=datos.get(0)== null ? null: datos.get(0);
        porcentaje=datos.get(1)== null ? null: Double.parseDouble(datos.get(1));
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
