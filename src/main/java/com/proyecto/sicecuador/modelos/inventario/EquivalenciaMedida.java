package com.proyecto.sicecuador.modelos.inventario;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "equivalencia_medida")
public class EquivalenciaMedida extends Entidad {
    @ManyToOne
    @JoinColumn(name = "medida1_id", nullable = true)
    private Medida medida1;
    @ManyToOne
    @JoinColumn(name = "medida2_id", nullable = true)
    private Medida medida2;
    @Column(name = "equivalencia", nullable = true)
    private double equivalencia;

    public EquivalenciaMedida(){
    }
    public EquivalenciaMedida(long id){
        super(id);
    }
    public EquivalenciaMedida(String codigo){
        super(codigo);
    }
    public EquivalenciaMedida(String codigo, Medida medida1, Medida medida2, double equivalencia){
        super(codigo);
        this.medida1=medida1;
        this.medida2=medida2;
        this.equivalencia=equivalencia;
    }

    public EquivalenciaMedida(Medida medida1, Medida medida2){
        super(null);
        this.medida1=medida1;
        this.medida2=medida2;
    }

    public EquivalenciaMedida(List<String>datos){

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
    
    public void setEquivalencia(double equivalencia) {
		this.equivalencia = equivalencia;
	}
    
    public void setMedida1(Medida medida1) {
		this.medida1 = medida1;
	}
    public void setMedida2(Medida medida2) {
		this.medida2 = medida2;
	}
}
