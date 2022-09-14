package com.proyecto.sicecuador.modelos.configuracion;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Direccion;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Column(name = "logo", nullable = true)
    private String logo;
    @Column(name = "estado", nullable = true)
    private String estado;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "tipo_identificacion_id", nullable = false)
    private TipoIdentificacion tipoIdentificacion;
    @ManyToOne
    @JoinColumn(name = "direccion_id", nullable = true)
    private Direccion direccion;
    
    public Empresa(){

    }

    public Empresa(long id){
        super(id);
    }

    public Empresa(String codigo, String identificacion, String razonSocial, String nombreComercial, String logo,  String estado, TipoIdentificacion tipoIdentificacion, Direccion direccion) {
        super(codigo);
        this.identificacion=identificacion;
        this.razonSocial=razonSocial;
        this.nombreComercial=nombreComercial;
        this.tipoIdentificacion=tipoIdentificacion;
        this.direccion=direccion;
        this.logo=logo;
        this.estado=estado;
        this.tipoIdentificacion=tipoIdentificacion;
    }
    public Empresa(List<String> datos){
        identificacion=datos.get(0)== null ? null: datos.get(0);
        razonSocial=datos.get(1)== null ? null: datos.get(1);
        nombreComercial=datos.get(2)== null ? null: datos.get(2);
        direccion=datos.get(3)== null ? null:new Direccion(Long.parseLong(datos.get(3)));
        logo=datos.get(4)== null ? null: datos.get(4);       
        estado=datos.get(5)== null ? null: datos.get(5);

    }
    
    public TipoIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
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
	
	public Direccion getDireccion() {
		return direccion;
	}

	public String getLogo() {
		return logo;
	}
}
