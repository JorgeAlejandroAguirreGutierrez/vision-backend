package com.proyecto.vision.modelos.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.usuario.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_plazo_credito;

@Entity
@Table(name = tabla_plazo_credito)
@Getter
@Setter
@AllArgsConstructor
public class PlazoCredito extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "plazo", nullable = true)
    private double plazo;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    public PlazoCredito(long id){
        super(id);
    }

    public PlazoCredito(){
        super();
        this.codigo = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.plazo = Constantes.cero;
        this.estado = Constantes.estadoActivo;
    }

    public void normalizar(){
        if(this.empresa == null) this.empresa = new Empresa();
    }
}
