package com.proyecto.sicecuador.modelos.comprobante;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;

@Entity
@Table(name = "tipo_comprobante")
public class TipoComprobante extends Entidad {
    @Column(name = "codigo_sri", nullable = true)
    private String codigoSri;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "nombre_tabla", nullable = true)
    private String nombreTabla;
    @Column(name = "estado", nullable = true)
    private String estado;

    public TipoComprobante(){

    }

    public TipoComprobante(long id){
        super(id);
    }
    public TipoComprobante(String codigo, String codigoSri, String nombre, String descripcion, String nombreTabla, String estado){
        super(codigo);
        this.codigoSri=codigoSri;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.nombreTabla=nombreTabla;
        this.estado=estado;
    }
    
    public String getCodigoSri() {
        return codigoSri;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public String getNombreTabla() {
		return nombreTabla;
	}

    public String getNombre() {
        return nombre;
    }

    public String getEstado() {
        return estado;
    }
    
}
