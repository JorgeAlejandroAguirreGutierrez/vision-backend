package com.proyecto.sicecuador.modelos.configuracion;
import com.proyecto.sicecuador.Constantes;
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
    @Column(name = "codigo_sri", nullable = true)
    private String codigoSRI;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "porcentaje", nullable = true)
    private double porcentaje;
    @Column(name = "estado", nullable = true)
    private String estado;

    public TipoRetencion(){
        super();
        this.impuestoRetencion = Constantes.vacio;
        this.tipoRetencion = Constantes.vacio;
        this.codigoSRI = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.porcentaje = Constantes.cero;
        this.estado = Constantes.activo;
    }

    public TipoRetencion(long id){
        super(id);
    }

    public TipoRetencion(String codigo, String impuestoRetencion, String tipoRetencion, String codigoSRI, String descripcion, double porcentaje, String estado){
        super(codigo);
        this.impuestoRetencion=impuestoRetencion;
        this.tipoRetencion=tipoRetencion;
        this.codigoSRI=codigoSRI;
        this.descripcion=descripcion;
        this.porcentaje=porcentaje;
        this.estado=estado;
    }
    public TipoRetencion(List<String> datos){
        impuestoRetencion=datos.get(0)== null ? null: datos.get(0);
        tipoRetencion=datos.get(1)== null ? null: datos.get(1);
        codigoSRI=datos.get(2)== null ? null: datos.get(2);
        descripcion=datos.get(4)== null ? null: datos.get(3);
        porcentaje=datos.get(5)== null ? null: Double.parseDouble(datos.get(4));
    }
    
    public String getImpuestoRetencion() {
		return impuestoRetencion;
	}

    public String getTipoRetencion() {
		return tipoRetencion;
	}
    
    public String getCodigoSRI() {
		return codigoSRI;
	}

    public String getDescripcion() {
        return descripcion;
    }

    public double getPorcentaje() {
        return porcentaje;
    }
    
    public String getEstado() {
		return estado;
	}
    
    public void setEstado(String estado) {
		this.estado = estado;
	}
}
