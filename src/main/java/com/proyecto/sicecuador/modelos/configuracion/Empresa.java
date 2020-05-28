package com.proyecto.sicecuador.modelos.configuracion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.otros.configuracion.EmpresaUtil;

import javax.persistence.*;

@Entity
@Table(name = "empresa")
@EntityListeners({EmpresaUtil.class})
public class Empresa extends Entidad {
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "razon_social", nullable = true)
    private String razon_social;
    @Column(name = "logo", nullable = true)
    private String logo;

    public Empresa(){

    }

    public Empresa(long id){
        super(id);
    }

    public Empresa(String codigo, String identificacion, String razon_social, String logo) {
        super(codigo);
        this.identificacion=identificacion;
        this.razon_social=razon_social;
        this.logo=logo;
    }
    public String getIdentificacion() {
        return identificacion;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public String getLogo() {
        return logo;
    }
}
