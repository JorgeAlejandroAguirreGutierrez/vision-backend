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
    @Column(name = "nombre_comercial", nullable = true)
    private String nombreComercial;
    @Column(name = "direccion_matriz", nullable = true)
    private String direccionMatriz;
    @Column(name = "contribuyente_especial", nullable = true)
    private String contribuyenteEspecial;
    @Column(name = "obligado_contabilidad", nullable = true)
    private String obligadoContabilidad;
    @Column(name = "logo", nullable = true)
    private String logo;
    
    @Column(name = "estado", nullable = true)
    private String estado;

    public Empresa(){

    }

    public Empresa(long id){
        super(id);
    }

    public Empresa(String codigo, String identificacion, String razonSocial, String nombreComercial, String direccionMatriz, String contribuyenteEspecial, String obligadoContabilidad, String logo,  String estado) {
        super(codigo);
        this.identificacion=identificacion;
        this.razonSocial=razonSocial;
        this.nombreComercial=nombreComercial;
        this.direccionMatriz=direccionMatriz;
        this.contribuyenteEspecial=contribuyenteEspecial;
        this.obligadoContabilidad=obligadoContabilidad;
        this.logo=logo;
        this.estado=estado;
    }
    public Empresa(List<String> datos){
        identificacion=datos.get(0)== null ? null: datos.get(0);
        razonSocial=datos.get(1)== null ? null: datos.get(1);
        nombreComercial=datos.get(2)== null ? null: datos.get(2);
        direccionMatriz=datos.get(3)== null ? null: datos.get(3);
        contribuyenteEspecial=datos.get(4)== null ? null: datos.get(4);
        obligadoContabilidad=datos.get(5)== null ? null: datos.get(5);
        logo=datos.get(6)== null ? null: datos.get(6);       
        estado=datos.get(7)== null ? null: datos.get(7);

    }
    public String getIdentificacion() {
        return identificacion;
    }

    public String getRazonSocial() {
	   return razonSocial;
   }
    
    public String getEstado() {
		return estado;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public String getDireccionMatriz() {
		return direccionMatriz;
	}

	public String getContribuyenteEspecial() {
		return contribuyenteEspecial;
	}

	public String getObligadoContabilidad() {
		return obligadoContabilidad;
	}

	public String getLogo() {
		return logo;
	}
}
