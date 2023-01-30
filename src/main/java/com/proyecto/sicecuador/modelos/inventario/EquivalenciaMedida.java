package com.proyecto.sicecuador.modelos.inventario;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "equivalencia_medida")
public class EquivalenciaMedida extends Entidad {
    @Column(name = "equivalencia", nullable = true)
    private double equivalencia;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "medidaIni_id", nullable = true)
    private Medida medidaIni;
    @ManyToOne
    @JoinColumn(name = "medidaEqui_id", nullable = true)
    private Medida medidaEqui;

    public EquivalenciaMedida(){
        super();
        this.equivalencia = Constantes.cero;
        this.estado = Constantes.activo;
        this.medidaIni = new Medida();
        this.medidaEqui = new Medida();
    }
    public EquivalenciaMedida(long id){
        super(id);
    }
    public EquivalenciaMedida(String codigo){
        super(codigo);
    }
    public EquivalenciaMedida(String codigo, Medida medidaIni, Medida medidaEqui, double equivalencia, String estado){
        super(codigo);
        this.medidaIni=medidaIni;
        this.medidaEqui=medidaEqui;
        this.equivalencia=equivalencia;
        this.estado=estado;
    }

    public EquivalenciaMedida(Medida medidaIni, Medida medidaEqui){
        super(null);
        this.medidaIni=medidaIni;
        this.medidaIni=medidaEqui;
    }

    public EquivalenciaMedida(List<String>datos){

    }

    public Medida getMedidaIni() {
        return medidaIni;
    }

    public Medida getMedidaEqui() {
        return medidaEqui;
    }

    public double getEquivalencia() {
        return equivalencia;
    }

    public String getEstado() {
        return estado;
    }
    
    public void setEquivalencia(double equivalencia) {
		this.equivalencia = equivalencia;
	}
    
    public void setMedidaIni(Medida medidaIni) {
		this.medidaIni = medidaIni;
	}
    public void setMedidaEqui(Medida medidaEqui) {
		this.medidaEqui = medidaEqui;
	}
    public void setEstado(String estado) {
		this.estado = estado;
	}

    public void normalizar(){
        if(this.medidaIni == null) this.medidaIni = new Medida();
        if(this.medidaEqui == null) this.medidaEqui = new Medida();
    }
    
}
