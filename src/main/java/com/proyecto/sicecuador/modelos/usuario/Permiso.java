package com.proyecto.sicecuador.modelos.usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.MenuOpcion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_permiso;

@Entity
@Table(name = tabla_permiso)
@Data
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
    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = true)
    private Perfil perfil;
    public Permiso(long id){
        super(id);
    }
    public Permiso(){
        super();
        this.codigo = Constantes.vacio;
        this.estado = Constantes.activo;
        this.menuOpcion = new MenuOpcion();
    }
}
