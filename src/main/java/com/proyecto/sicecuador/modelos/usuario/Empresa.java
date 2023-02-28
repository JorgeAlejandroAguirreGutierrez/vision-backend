package com.proyecto.sicecuador.modelos.usuario;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_empresa;

@Entity
@Table(name = tabla_empresa)
@Data
@AllArgsConstructor
public class Empresa extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
	@Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
    @Column(name = "nombre_comercial", nullable = true)
    private String nombreComercial;
    @Transient
    private String logo64;
    @Lob
    @Column(name = "logo", nullable = true)
    private byte[] logo;
    @Column(name = "obligado_contabilidad", nullable = true)
    private String obligadoContabilidad;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "tipo_identificacion_id", nullable = true)
    private TipoIdentificacion tipoIdentificacion;
    //@ManyToOne
    //@JoinColumn(name = "ubicacion_id", nullable = true)
    //private Ubicacion ubicacion;

    public Empresa(long id){
        super(id);
    }
    public Empresa(){
        super();
        this.codigo = Constantes.vacio;
        this.identificacion = Constantes.vacio;
        this.razonSocial = Constantes.vacio;
        this.nombreComercial = Constantes.vacio;
        this.logo64 = Constantes.vacio;
        this.logo = null;
        this.obligadoContabilidad = Constantes.no;
        this.direccion = Constantes.vacio;
        this.estado = Constantes.activo;
        this.tipoIdentificacion = new TipoIdentificacion();
        //this.ubicacion = new Ubicacion();
    }

    public void normalizar(){
        if(this.tipoIdentificacion == null) this.tipoIdentificacion = new TipoIdentificacion();
        //if(this.ubicacion == null) this.ubicacion = new Ubicacion();
    }
}
