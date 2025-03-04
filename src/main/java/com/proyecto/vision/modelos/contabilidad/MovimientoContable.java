package com.proyecto.vision.modelos.contabilidad;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.acceso.Empresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_movimiento_contable;

@Entity
@Table(name = tabla_movimiento_contable)
@Getter
@Setter
@AllArgsConstructor
public class MovimientoContable extends Entidad {
	@Column(name = "codigo", nullable = true)
	private String codigo;
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
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;

	public MovimientoContable(long id){
		super(id);
	}
	public MovimientoContable(){
        super();
		this.codigo = Constantes.vacio;
		this.estado = Constantes.estadoActivo;

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
