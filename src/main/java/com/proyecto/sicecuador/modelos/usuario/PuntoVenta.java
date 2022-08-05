package com.proyecto.sicecuador.modelos.usuario;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "punto_venta")
public class PuntoVenta extends Entidad {
	@NotNull
	@Column(name ="codigo_sri", nullable = true)
	private String codigoSri;
	@Column(name = "descripcion", nullable = true)
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;

    public PuntoVenta(){
        super();
    }

    public PuntoVenta(long id) {
        super(id);
    }
    public PuntoVenta(String codigo, String codigoSri, String descripcion, Establecimiento establecimiento){
        super(codigo);
        this.codigoSri=codigoSri;
        this.descripcion=descripcion;
        this.establecimiento=establecimiento;
    }
    public PuntoVenta(List<String> datos){
    	codigoSri=datos.get(0)==null ? null: datos.get(0);
        descripcion=datos.get(1)== null ? null: datos.get(1);
        establecimiento=datos.get(1)== null ? null:new Establecimiento((long) Double.parseDouble(datos.get(1)));
    }
    public String getCodigoSri() {
    	return codigoSri;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }
}
