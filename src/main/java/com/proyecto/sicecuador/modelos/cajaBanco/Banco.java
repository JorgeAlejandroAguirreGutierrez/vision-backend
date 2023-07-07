package com.proyecto.vision.modelos.cajaBanco;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.usuario.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_banco;

@Entity
@Table(name = tabla_banco)
@Getter
@Setter
@AllArgsConstructor
public class Banco extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "ruc", nullable = true)
    private String ruc;
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
    @Column(name = "subsistema", nullable = true)
    private String subsistema;
    @Column(name = "calificacion", nullable = true)
    private String calificacion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;

    public Banco(long id){
        super(id);
    }
    public Banco(){
        super();
        this.codigo = Constantes.vacio;
        this.ruc = Constantes.vacio;
        this.razonSocial = Constantes.vacio;
        this.subsistema = Constantes.vacio;
        this.calificacion = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.activo;
    }
}
