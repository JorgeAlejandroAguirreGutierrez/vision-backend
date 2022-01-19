package com.proyecto.sicecuador.modelos.configuracion;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tipo_retencion")
public class TipoRetencion extends Entidad {
    @Column(name = "impuesto_retencion", nullable = true)
    private String impuestoRetencion;
    @Column(name = "tipo_retencion", nullable = true)
    private String tipoRetencion;
    @Column(name = "codigo_norma", nullable = true)
    private String codigoNorma;
    @Column(name = "homologacion_f_e", nullable = true)
    private String homologacionFE;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "porcentaje", nullable = true)
    private double porcentaje;

    public TipoRetencion(){

    }

    public TipoRetencion(long id){
        super(id);
    }

    public TipoRetencion(String codigo, String impuestoRetencion, String tipoRetencion, String codigoNorma, String homologacionFE, String descripcion,double porcentaje){
        super(codigo);
        this.impuestoRetencion=impuestoRetencion;
        this.tipoRetencion=tipoRetencion;
        this.codigoNorma=codigoNorma;
        this.homologacionFE=homologacionFE;
        this.descripcion=descripcion;
        this.porcentaje=porcentaje;
    }
    public TipoRetencion(List<String> datos){
        impuestoRetencion=datos.get(0)== null ? null: datos.get(0);
        tipoRetencion=datos.get(1)== null ? null: datos.get(1);
        codigoNorma=datos.get(2)== null ? null: datos.get(2).substring(0,datos.get(2).length()-1);
        homologacionFE=datos.get(3)== null ? null: datos.get(3);
        descripcion=datos.get(4)== null ? null: datos.get(4);
        porcentaje=datos.get(5)== null ? null: Double.parseDouble(datos.get(5));
    }
    
    public String getImpuestoRetencion() {
		return impuestoRetencion;
	}

    public String getTipoRetencion() {
		return tipoRetencion;
	}

    public String getCodigoNorma() {
		return codigoNorma;
	}

    public String getHomologacionFE() {
    	return homologacionFE;
   }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPorcentaje() {
        return porcentaje;
    }
}
