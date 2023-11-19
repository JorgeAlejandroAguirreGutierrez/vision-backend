package com.proyecto.vision.modelos.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.usuario.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "equivalencia_medida")
@Getter
@Setter
@AllArgsConstructor
public class EquivalenciaMedida extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "equivalencia", nullable = true)
    private double equivalencia;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "medida_ini_id", nullable = true)
    private Medida medidaIni;
    @ManyToOne
    @JoinColumn(name = "medida_fin_id", nullable = true)
    private Medida medidaFin;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;
    public EquivalenciaMedida(long id){
        super(id);
    }

    public EquivalenciaMedida(){
        super();
        this.codigo = Constantes.vacio;
        this.equivalencia = equivalencia;
        this.estado = Constantes.estadoActivo;
    }

}
