package com.proyecto.sicecuador.modelos.comprobante;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.proyecto.sicecuador.Constantes.tabla_factura;

@Entity
@Table(name = tabla_factura)
@Data
@AllArgsConstructor
public class Factura extends Entidad {
	@Column(name = "codigo", nullable = true)
	private String codigo;
	@Column(name = "secuencia", nullable = true)
	private String secuencia;
	@Column(name = "codigo_numerico", nullable = true)
	private String codigoNumerico;
	@Column(name = "fecha", nullable = true)
	private Date fecha;
	@Column(name = "clave_acceso", nullable = true)
	private String claveAcceso;
	@Column(name = "estado", nullable = true)
	private String estado;
	@Column(name = "subtotal_sin_descuento", nullable = true)
	private double subtotalSinDescuento;
	@Column(name = "subtotal_con_descuento", nullable = true)
	private double subtotalConDescuento;
	@Column(name = "descuento_total", nullable = true)
	private double descuentoTotal;
	@Column(name = "subtotal_base12_sin_descuento", nullable = true)
	private double subtotalBase12SinDescuento;
	@Column(name = "subtotal_base0_sin_descuento", nullable = true)
	private double subtotalBase0SinDescuento;
	@Column(name = "subtotal_base12_con_descuento", nullable = true)
	private double subtotalBase12ConDescuento;
	@Column(name = "subtotal_base0_con_descuento", nullable = true)
	private double subtotalBase0ConDescuento;
	@Column(name = "iva_sin_descuento", nullable = true)
	private double ivaSinDescuento;
	@Column(name = "iva_con_descuento", nullable = true)
	private double ivaConDescuento;
	@Column(name = "total_sin_descuento", nullable = true)
	private double totalSinDescuento;
	@Column(name = "total_con_descuento", nullable = true)
	private double totalConDescuento;
	// GENERAL
	@Column(name = "valor_descuento_subtotal", nullable = true)
	private double valorDescuentoSubtotal;
	@Column(name = "porcentaje_descuento_subtotal", nullable = true)
	private double porcentajeDescuentoSubtotal;
	@Column(name = "valor_descuento_total", nullable = true)
	private double valorDescuentoTotal;
	@Column(name = "porcentaje_descuento_total", nullable = true)
	private double porcentajeDescuentoTotal;
	@Column(name = "valor_porcentaje_descuento_total", nullable = true)
	private double valorPorcentajeDescuentoTotal;
	@Column(name = "comentario", nullable = true)
	private String comentario;
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = true)
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name = "sesion_id", nullable = true)
	private Sesion sesion;
	@ManyToOne
	@JoinColumn(name = "tipo_comprobante_id", nullable = true)
	private TipoComprobante tipoComprobante;
	@JsonManagedReference
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "factura_id", nullable = true)
	private List<FacturaLinea> facturaLineas;

	public Factura(long id){
		super(id);
	}
	public Factura() {
		super();
		this.codigo = Constantes.vacio;
		this.secuencia = Constantes.vacio;
		this.codigoNumerico = Constantes.vacio;
		this.fecha = new Date();
		this.claveAcceso = Constantes.vacio;
		this.estado = Constantes.activo;
		this.subtotalSinDescuento = Constantes.cero;
		this.subtotalConDescuento = Constantes.cero;
		this.descuentoTotal = Constantes.cero;
		this.subtotalBase12SinDescuento = Constantes.cero;
		this.subtotalBase0SinDescuento = Constantes.cero;
		this.subtotalBase12ConDescuento = Constantes.cero;
		this.subtotalBase0ConDescuento = Constantes.cero;
		this.ivaSinDescuento = Constantes.cero;
		this.ivaConDescuento = Constantes.cero;
		this.totalSinDescuento = Constantes.cero;
		this.totalConDescuento = Constantes.cero;
		this.valorDescuentoSubtotal = Constantes.cero;
		this.porcentajeDescuentoSubtotal = Constantes.cero;
		this.valorDescuentoTotal = Constantes.cero;
		this.porcentajeDescuentoTotal = Constantes.cero;
		this.valorPorcentajeDescuentoTotal = Constantes.cero;
		this.comentario = Constantes.vacio;
		this.cliente = new Cliente();
		this.sesion = new Sesion();
		this.tipoComprobante = new TipoComprobante();
		this.facturaLineas = Collections.emptyList();
	}

	public void normalizar(){
		if(this.fecha == null) this.fecha = new Date();
		if(this.cliente == null) this.cliente = new Cliente();
		if(this.sesion == null) this.sesion = new Sesion();
		if(this.tipoComprobante == null) this.tipoComprobante = new TipoComprobante();
		if(this.facturaLineas.isEmpty()) this.facturaLineas = Collections.emptyList();
	}
}
