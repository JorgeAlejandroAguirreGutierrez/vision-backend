package com.proyecto.sicecuador.modelos.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_estacion;

@Entity
@Table(name = tabla_estacion)
@Data
@AllArgsConstructor
public class Estacion extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
	@Column(name ="codigo_sri", nullable = true)
	private String codigoSRI;
	@Column(name = "descripcion", nullable = true)
    private String descripcion;
	@Column(name = "dispositivo", nullable = true)
    private String dispositivo;
	@Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;

    public Estacion(long id){
        super(id);
    }
    public Estacion(){
        super();
        this.codigo = Constantes.vacio;
        this.codigoSRI = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.dispositivo = Constantes.vacio;
        this.estado = Constantes.activo;
        this.establecimiento = new Establecimiento();
    }

    public void normalizar(){
        if(this.establecimiento == null) this.establecimiento = new Establecimiento();
    }
}
