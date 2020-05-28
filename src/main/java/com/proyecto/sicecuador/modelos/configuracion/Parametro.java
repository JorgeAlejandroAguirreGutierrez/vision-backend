package com.proyecto.sicecuador.modelos.configuracion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.configuracion.ParametroUtil;

import javax.persistence.*;

@Entity
@Table(name = "parametro")
@EntityListeners({ParametroUtil.class})
public class Parametro extends Entidad {
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "tabla", nullable = true)
    private String tabla;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;

    public Parametro(){

    }

    public Parametro(long id){
        super(id);
    }

    public Parametro(String tipo){
        this.tipo=tipo;
    }

    public Parametro(String codigo, String tipo, String nombre, String tabla, String abreviatura){
        super(codigo);
        this.tipo=tipo;
        this.nombre=nombre;
        this.tabla=tabla;
        this.abreviatura=abreviatura;
    }
    public Parametro(String tabla,String tipo){
        this.tipo=tipo;
        this.tabla=tabla;
    }
    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTabla() {
        return tabla;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
}
