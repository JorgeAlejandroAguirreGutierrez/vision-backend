package com.proyecto.sicecuador.modelos.cliente;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "segmento")
@Data
@AllArgsConstructor
public class Segmento extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "margen_ganancia", nullable = true)
    private double margenGanancia;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
	@Column(name = "abreviatura", nullable = true)
    private String abreviatura;
	@Column(name = "estado", nullable = true)
    private String estado;

    public Segmento(long id){
        super(id);
    }
    public Segmento(){
    	super();
        this.codigo = Constantes.vacio;
        this.margenGanancia = Constantes.cero;
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.activo;
    }
}
