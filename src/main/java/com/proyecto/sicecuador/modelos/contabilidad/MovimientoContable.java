package com.proyecto.sicecuador.modelos.contabilidad;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "movimiento_contable")
public class MovimientoContable extends Entidad {
	@Column(name = "estado", nullable = true)
	private String estado;
	@ManyToOne
    @JoinColumn(name = "afectacion_contable_id", nullable = true)
    private AfectacionContable afectacionContable;
	@ManyToOne
	@JoinColumn(name = "inventario_id", nullable = true)
	private CuentaContable inventario;
	@ManyToOne
	@JoinColumn(name = "costo_venta_id", nullable = true)
	private CuentaContable costoVenta;
    @ManyToOne
	@JoinColumn(name = "devolucion_compra_id", nullable = true)
	private CuentaContable devolucionCompra;
    @ManyToOne
	@JoinColumn(name = "descuento_compra_id", nullable = true)
	private CuentaContable descuentoCompra;
    @ManyToOne
	@JoinColumn(name = "venta_id", nullable = true)
	private CuentaContable venta;
    @ManyToOne
	@JoinColumn(name = "devolucion_venta_id", nullable = true)
	private CuentaContable devolucionVenta;
    @ManyToOne
	@JoinColumn(name = "descuento_venta_id", nullable = true)
	private CuentaContable descuentoVenta;
    @ManyToOne
	@JoinColumn(name = "devolucion_costo_venta_id", nullable = true)
	private CuentaContable devolucionCostoVenta;
   
    public MovimientoContable(){
        super();
		this.estado = Constantes.activo;
		this.afectacionContable = new AfectacionContable();
		this.inventario = new CuentaContable();
		this.costoVenta = new CuentaContable();
		this.devolucionCompra = new CuentaContable();
		this.descuentoCompra = new CuentaContable();
		this.venta = new CuentaContable();
		this.devolucionVenta = new CuentaContable();
		this.descuentoVenta = new CuentaContable();
		this.devolucionCostoVenta = new CuentaContable();

    }

    public MovimientoContable(long id){
        super(id);
    }

    public MovimientoContable(String codigo, AfectacionContable afectacionContable, CuentaContable inventario,
    						  CuentaContable costoVenta, CuentaContable devolucionCompra, CuentaContable descuentoCompra,
    						  CuentaContable venta, CuentaContable devolucionVenta, CuentaContable descuentoVenta,
    						  CuentaContable devolucionCostoVenta, String estado){
        super(codigo);
        this.afectacionContable=afectacionContable;
        this.inventario=inventario;
        this.costoVenta=costoVenta;
        this.devolucionCompra=devolucionCompra;
        this.descuentoCompra=descuentoCompra;
        this.venta=venta;
        this.devolucionVenta=devolucionVenta;
        this.descuentoVenta=descuentoVenta;
        this.devolucionCostoVenta=devolucionCostoVenta;
        this.estado=estado;
    }
    
    public MovimientoContable(List<String> datos){
    	afectacionContable=datos.get(0)== null ? null: new AfectacionContable((long) Double.parseDouble(datos.get(0)));
    	inventario=datos.get(1)== null ? null: new CuentaContable((long) Double.parseDouble(datos.get(1)));
    	costoVenta=datos.get(2)== null ? null: new CuentaContable((long) Double.parseDouble(datos.get(2)));
    	devolucionCompra=datos.get(1)== null ? null: new CuentaContable((long) Double.parseDouble(datos.get(1)));
    	descuentoCompra=datos.get(1)== null ? null: new CuentaContable((long) Double.parseDouble(datos.get(1)));
    	venta=datos.get(1)== null ? null: new CuentaContable((long) Double.parseDouble(datos.get(1)));
    	devolucionVenta=datos.get(1)== null ? null: new CuentaContable((long) Double.parseDouble(datos.get(1)));
    	descuentoVenta=datos.get(1)== null ? null: new CuentaContable((long) Double.parseDouble(datos.get(1)));
    	devolucionCostoVenta=datos.get(7)== null ? null: new CuentaContable((long) Double.parseDouble(datos.get(7)));
    	estado=datos.get(8)== null ? null: datos.get(8);
    	
    }

	public AfectacionContable getAfectacionContable() {
		return afectacionContable;
	}

	public CuentaContable getInventario() {
		return inventario;
	}

	public CuentaContable getCostoVenta() {
		return costoVenta;
	}

	public CuentaContable getDevolucionCompra() {
		return devolucionCompra;
	}

	public CuentaContable getDescuentoCompra() {
		return descuentoCompra;
	}

	public CuentaContable getVenta() {
		return venta;
	}

	public CuentaContable getDevolucionVenta() {
		return devolucionVenta;
	}

	public CuentaContable getDescuentoVenta() {
		return descuentoVenta;
	}

	public CuentaContable getDevolucionCostoVenta() {
		return devolucionCostoVenta;
	}

	public String getEstado() {
		return estado;
	}

	public void setAfectacionContable(AfectacionContable afectacionContable) {
		this.afectacionContable = afectacionContable;
	}

	public void setInventario(CuentaContable inventario) {
		this.inventario = inventario;
	}

	public void setCostoVenta(CuentaContable costoVenta) {
		this.costoVenta = costoVenta;
	}

	public void setDevolucionCompra(CuentaContable devolucionCompra) {
		this.devolucionCompra = devolucionCompra;
	}

	public void setDescuentoCompra(CuentaContable descuentoCompra) {
		this.descuentoCompra = descuentoCompra;
	}

	public void setVenta(CuentaContable venta) {
		this.venta = venta;
	}

	public void setDevolucionVenta(CuentaContable devolucionVenta) {
		this.devolucionVenta = devolucionVenta;
	}

	public void setDescuentoVenta(CuentaContable descuentoVenta) {
		this.descuentoVenta = descuentoVenta;
	}

	public void setDevolucionCostoVenta(CuentaContable devolucionCostoVenta) {
		this.devolucionCostoVenta = devolucionCostoVenta;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void normalizar(){
		if(this.afectacionContable == null) this.afectacionContable = new AfectacionContable();
		if(this.inventario == null) this.inventario = new CuentaContable();
		if(this.costoVenta == null) this.costoVenta = new CuentaContable();
		if(this.devolucionCompra == null) this.devolucionCompra = new CuentaContable();
		if(descuentoCompra == null) this.descuentoCompra = new CuentaContable();
		if(this.venta == null) this.venta = new CuentaContable();
		if(this.devolucionVenta == null) this.devolucionVenta = new CuentaContable();
		if(this.descuentoVenta == null) this.descuentoVenta = new CuentaContable();
		if(this.devolucionCostoVenta == null) this.devolucionCostoVenta = new CuentaContable();
	}


    
}
