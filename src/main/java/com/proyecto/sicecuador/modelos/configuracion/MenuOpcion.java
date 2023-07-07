package com.proyecto.vision.modelos.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.proyecto.vision.Constantes.tabla_menu_opcion;

@Entity
@Table(name = tabla_menu_opcion)
@Getter
@Setter
@AllArgsConstructor
public class MenuOpcion extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "modulo", nullable = true)
    private String modulo;
    @Column(name = "opcion", nullable = true)
    private String opcion;
    @Column(name = "operacion", nullable = true)
    private String operacion;
    @Column(name = "menu", nullable = true)
    private String menu;
    @Column(name = "tabla", nullable = true)
    private String tabla;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;

    public MenuOpcion(long id){
        super(id);
    }
    public MenuOpcion(){
        super();
        this.codigo = Constantes.vacio;
        this.modulo = Constantes.vacio;
        this.opcion = Constantes.vacio;
        this.operacion = Constantes.vacio;
        this.menu = Constantes.no;
        this.tabla = Constantes.vacio;
        this.abreviatura = Constantes.vacio;
        this.estado = Constantes.activo;
    }
}
