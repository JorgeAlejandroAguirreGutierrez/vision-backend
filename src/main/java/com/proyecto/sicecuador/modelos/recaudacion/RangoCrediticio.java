package com.proyecto.sicecuador.modelos.recaudacion;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "rango_crediticio")
public class RangoCrediticio extends Entidad {
    @Column(name = "rango_inicial", nullable = true)
    private double rangoInicial;
    @Column(name = "rango_final", nullable = true)
    private double rangoFinal;
    @Column(name = "tasa_interes_anual", nullable = true)
    private double tasaInteresAnual;
    @Column(name = "tasa_periodo", nullable = true)
    private double tasaPeriodo;

    public RangoCrediticio(){
    }

    public RangoCrediticio(long id){
        super(id);
    }

    public RangoCrediticio(String codigo, double rangoInicial, double rangoFinal, double tasaInteresAnual, double tasaPeriodo){
        super(codigo);
        this.rangoInicial=rangoInicial;
        this.rangoFinal=rangoFinal;
        this.tasaInteresAnual=tasaInteresAnual;
        this.tasaPeriodo=tasaPeriodo;
    }
    public RangoCrediticio(List<String> datos){
        rangoInicial=datos.get(0)== null ? null: Double.parseDouble(datos.get(0));
        rangoFinal=datos.get(1)== null ? null: Double.parseDouble(datos.get(1));
        tasaInteresAnual=datos.get(2)== null ? null: Double.parseDouble(datos.get(2));
        tasaPeriodo=datos.get(3)== null ? null: Double.parseDouble(datos.get(3));
    }

    public double getRangoInicial() {
		return rangoInicial;
	}

    public double getRangoFinal() {
		return rangoFinal;
	}

    public double getTasaInteresAnual() {
		return tasaInteresAnual;
	}

    public double getTasaPeriodo() {
		return tasaPeriodo;
	}
}
