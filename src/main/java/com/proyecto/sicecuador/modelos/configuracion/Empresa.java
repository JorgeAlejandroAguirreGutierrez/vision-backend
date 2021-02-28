package com.proyecto.sicecuador.modelos.configuracion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.configuracion.EmpresaUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "empresa")
@EntityListeners({EmpresaUtil.class})
public class Empresa extends Entidad {
	@JsonProperty("identificacion")
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
	@JsonProperty("razon_social")
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
	@JsonProperty("logo")
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
