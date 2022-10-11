package com.proyecto.sicecuador.modelos.contabilidad;
import com.proyecto.sicecuador.modelos.Entidad;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cuenta_contable")
public class CuentaContable extends Entidad {
    @Column(name = "cuenta", nullable = true)
    private String cuenta;
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "clasificacion", nullable = true)
    private String clasificacion;
    @Column(name = "nivel", nullable = true)
    private int nivel;
    @Column(name = "fe", nullable = true)
    private boolean fe;
    @Column(name = "casillero", nullable = true)
    private String casillero;
    @Column(name = "mapeo", nullable = true)
    private String mapeo;
    @Column(name = "estado", nullable = true)
    private String estado;
       
    public CuentaContable(){
        super();
    }

    public CuentaContable(long id){
        super(id);
    }

    public CuentaContable(String codigo, String cuenta, String descripcion, String clasificacion, int nivel, boolean fe, 
    					  String casillero, String mapeo, String estado){
        super(codigo);
        this.cuenta=cuenta;
        this.descripcion=descripcion;
        this.clasificacion=clasificacion;
        this.nivel=nivel;
        this.fe=fe;
        this.casillero=casillero;
        this.mapeo=mapeo;
        this.estado=estado;
    }
    
    public CuentaContable(List<String> datos){
    	cuenta=datos.get(0)== null ? null: datos.get(0);
    	descripcion=datos.get(1)== null ? null: datos.get(1);
    	clasificacion=datos.get(2)== null ? null: datos.get(2);
    	nivel=datos.get(3)== null ? null: Integer.parseInt(datos.get(3));
    	fe=datos.get(4)== null ? null: datos.get(4).equals("S") ? true : false;
    	casillero=datos.get(5)== null ? null: datos.get(5);
    	mapeo=datos.get(6)== null ? null: datos.get(6);
    	estado=datos.get(7)== null ? null: datos.get(7);
    }

	public String getCuenta() {
		return cuenta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public int getNivel() {
		return nivel;
	}

	public boolean isFe() {
		return fe;
	}

	public String getCasillero() {
		return casillero;
	}

	public String getMapeo() {
		return mapeo;
	}

	public String getEstado() {
		return estado;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public void setFe(boolean fe) {
		this.fe = fe;
	}

	public void setCasillero(String casillero) {
		this.casillero = casillero;
	}

	public void setMapeo(String mapeo) {
		this.mapeo = mapeo;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
  
   
}
