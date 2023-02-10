package com.proyecto.sicecuador.modelos.proveedor;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "proveedor")
@Data
@AllArgsConstructor
public class Proveedor extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "razon_social", nullable = true)
    private String razonSocial;
    @Column(name = "nombre_comercial", nullable = true)
    private String nombreComercial;
    @Column(name = "direccion", nullable = true)
    private String direccion;
    @Column(name = "telefono", nullable = true)
    private String telefono;
    @Column(name = "celular", nullable = true)
    private String celular;
    @Column(name = "correo", nullable = true)
    private String correo;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "tipo_identificacion_id", nullable = true)
    private TipoIdentificacion tipoIdentificacion;
    @ManyToOne
    @JoinColumn(name = "grupo_proveedor_id", nullable = true)
    private GrupoProveedor grupoProveedor;

    public Proveedor(long id){
        super(id);
    }
    public Proveedor(){
        super();
        this.codigo = Constantes.vacio;
        this.identificacion = Constantes.vacio;
        this.razonSocial = Constantes.vacio;
        this.nombreComercial = Constantes.vacio;
        this.direccion = Constantes.vacio;
        this.telefono = Constantes.vacio;
        this.celular = Constantes.vacio;
        this.correo = Constantes.vacio;
        this.estado = Constantes.activo;
        this.tipoIdentificacion = new TipoIdentificacion();
    }

    public void normalizar(){
        if(this.tipoIdentificacion == null) this.tipoIdentificacion = new TipoIdentificacion();
        if(this.grupoProveedor == null) this.grupoProveedor = new GrupoProveedor();
    }
    
}
