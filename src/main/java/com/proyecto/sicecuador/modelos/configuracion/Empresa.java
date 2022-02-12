package com.proyecto.sicecuador.modelos.configuracion;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "empresa")
public class Empresa extends Entidad {
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
    @Column(name = "logo", nullable = true)
    private String logo;

    public Empresa(){

    }

    public Empresa(long id){
        super(id);
    }

    public Empresa(String codigo, String identificacion, String razonSocial, String logo) {
        super(codigo);
        this.identificacion=identificacion;
        this.razonSocial=razonSocial;
        this.logo=logo;
    }
    public Empresa(List<String> datos){
        identificacion=datos.get(0)== null ? null: datos.get(0);
        razonSocial=datos.get(1)== null ? null: datos.get(1);
        logo=datos.get(2)== null ? null: datos.get(2);

    }
    public String getIdentificacion() {
        return identificacion;
    }

    public String getRazonSocial() {
	   return razonSocial;
   }

    public String getLogo() {
        return logo;
    }
}
