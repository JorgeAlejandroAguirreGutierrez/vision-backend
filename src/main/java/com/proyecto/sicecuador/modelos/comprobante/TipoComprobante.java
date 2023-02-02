package com.proyecto.sicecuador.modelos.comprobante;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;

@Entity
@Table(name = "tipo_comprobante")
public class TipoComprobante extends Entidad {
    @Column(name = "codigo_sri", nullable = true)
    private String codigoSRI;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "nombre_tabla", nullable = true)
    private String nombreTabla;

    public TipoComprobante(){
        super();
        this.codigoSRI = Constantes.vacio;
        this.descripcion = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.nombreTabla = Constantes.vacio;
    }

    public TipoComprobante(long id){
        super(id);
    }
    public TipoComprobante(String codigo, String codigoSRI, String nombre, String descripcion, String nombreTabla){
        super(codigo);
        this.codigoSRI=codigoSRI;
        this.nombre=nombre;
        this.descripcion=descripcion;
        this.nombreTabla=nombreTabla;
    }

    public String getCodigoSRI() {
        return codigoSRI;
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
}
