package com.proyecto.sicecuador.modelos.inventario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.inventario.MedidaUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "medida")
@EntityListeners({MedidaUtil.class})
public class Medida extends Entidad {
    @Column(name = "codigo_norma", nullable = true)
    private String codigo_norma;
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public Medida(){
        super();
    }

    public Medida(long id){
        super(id);
    }

    public Medida(String codigo, String codigo_norma, String tipo, String descripcion, String abreviatura){
        super(codigo);
        this.codigo_norma=codigo_norma;
        this.tipo=tipo;
        this.descripcion=descripcion;
        this.abreviatura=abreviatura;
    }
    public Medida(List<String> datos){
        codigo_norma=datos.get(0)== null ? null: datos.get(0);
        descripcion=datos.get(1)== null ? null: datos.get(1);
    }
    public String getCodigo_norma() {
        return codigo_norma;
    }

    public String getTipo() {
        return tipo;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
