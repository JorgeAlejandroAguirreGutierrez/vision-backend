package com.proyecto.sicecuador.modelos.recaudacion;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "operador_tarjeta")
public class OperadorTarjeta extends Entidad {
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "nombre", nullable = true)
    private String nombre;
    @Column(name = "abreviatura", nullable = true)
    private String abreviatura;
    @Column(name = "estado", nullable = true)
    private String estado;

    public OperadorTarjeta(){
    }

    public OperadorTarjeta(long id){
        super(id);
    }

    public OperadorTarjeta(String codigo, String tipo, String nombre, String abreviatura, String estado){
        super(codigo);
        this.tipo=tipo;
        this.nombre=nombre;
        this.abreviatura=abreviatura;
        this.estado=estado;
    }
    public OperadorTarjeta(String tipo){
        super();
        this.tipo=tipo;
    }
    public OperadorTarjeta(List<String> datos){
        tipo=datos.get(0)== null ? null: datos.get(0);
        nombre=datos.get(1)== null ? null: datos.get(1);
        abreviatura=datos.get(2)== null ? null: datos.get(2);
    }
    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }
    public String getEstado() {
		return estado;
	}
    
    public void setEstado(String estado) {
		this.estado = estado;
	}
}
