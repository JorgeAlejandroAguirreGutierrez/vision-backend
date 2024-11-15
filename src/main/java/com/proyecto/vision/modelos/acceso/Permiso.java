package com.proyecto.vision.modelos.acceso;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.configuracion.MenuOpcion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_permiso;

@Entity
@Table(name = tabla_permiso)
@Getter
@Setter
@AllArgsConstructor
public class Permiso extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "menu_opcion_id", nullable = true)
    private MenuOpcion menuOpcion;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id", nullable = true)
    private Perfil perfil;
    public Permiso(long id){
        super(id);
    }
    public Permiso(){
        super();
        this.codigo = Constantes.vacio;
        this.estado = Constantes.estadoActivo;
        this.menuOpcion = new MenuOpcion();
    }
}
