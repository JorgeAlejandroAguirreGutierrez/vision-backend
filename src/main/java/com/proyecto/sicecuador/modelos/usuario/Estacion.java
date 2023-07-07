package com.proyecto.vision.modelos.usuario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.configuracion.Regimen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_estacion;

@Entity
@Table(name = tabla_estacion)
@Getter
@Setter
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
    @Column(name = "ip", nullable = true)
    private String ip;
    @Column(name = "punto_venta", nullable = true)
    private String puntoVenta;
	@Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "regimen_id", nullable = true)
    private Regimen regimen;
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
        this.ip = Constantes.vacio;
        this.puntoVenta = Constantes.no;
        this.estado = Constantes.activo;
    }

    public void normalizar(){
        if(this.regimen == null) this.regimen = new Regimen();
        if(this.establecimiento == null) this.establecimiento = new Establecimiento();
    }
}
