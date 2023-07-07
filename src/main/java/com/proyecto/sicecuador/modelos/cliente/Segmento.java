package com.proyecto.vision.modelos.cliente;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.contabilidad.CuentaContable;
import com.proyecto.vision.modelos.usuario.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_segmento;

@Entity
@Table(name = tabla_segmento)
@Getter
@Setter
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
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

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

    public void normalizar(){
        if(this.empresa == null) this.empresa = new Empresa();
    }
}
