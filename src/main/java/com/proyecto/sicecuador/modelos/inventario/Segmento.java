package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.BodegaUtil;
import com.proyecto.sicecuador.otros.inventario.SegmentoUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "segmento")
@EntityListeners({SegmentoUtil.class})
public class Segmento extends Entidad {
    @Column(name = "nombre", nullable = true)
    private String nombre;
    public Segmento(){
    }
    public Segmento(long id){
        super(id);
    }
    public Segmento(String codigo){
        super(codigo);
    }
    public Segmento(String codigo, String nombre){
        super(codigo);
        this.nombre=nombre;
    }

    public Segmento(List<String>datos){

    }

    public String getNombre() {
        return nombre;
    }
}
