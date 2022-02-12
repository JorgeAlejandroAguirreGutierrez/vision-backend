package com.proyecto.sicecuador.modelos.configuracion;

import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ubicacion")
public class Ubicacion extends Entidad {
    @Column(name = "codigo_norma", nullable = true)
    private String codigoNorma;
    @Column(name = "provincia", nullable = true)
    private String provincia;
    @Column(name = "canton", nullable = true)
    private String canton;
    @Column(name = "parroquia", nullable = true)
    private String parroquia;

    public Ubicacion(){

    }

    public Ubicacion(long id){
        super(id);
    }

    public Ubicacion(String codigo, String codigoNorma, String provincia, String canton, String parroquia){
        super(codigo);
        this.codigoNorma=codigoNorma;
        this.provincia=provincia;
        this.canton=canton;
        this.parroquia=parroquia;
    }

    public Ubicacion(String codigoNorma, String provincia, String canton, String parroquia){
        this.codigoNorma=codigoNorma;
        this.provincia=provincia;
        this.canton=canton;
        this.parroquia=parroquia;
    }

    public Ubicacion(String provincia, String canton, String parroquia){
        this.provincia=provincia;
        this.canton=canton;
        this.parroquia=parroquia;
    }

    public Ubicacion(List<String> datos){
        codigoNorma=datos.get(0)== null ? null: datos.get(0);
        provincia=datos.get(1)== null ? null: datos.get(1);
        canton=datos.get(2)== null ? null: datos.get(2);
        parroquia=datos.get(3)== null ? null: datos.get(3);
    }
    
    public String getCodigoNorma() {
		return codigoNorma;
	}

    public String getProvincia() {
        return provincia;
    }

    public String getCanton() {
        return canton;
    }

    public String getParroquia() {
        return parroquia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }
}
