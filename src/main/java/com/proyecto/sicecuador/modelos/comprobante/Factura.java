package com.proyecto.sicecuador.modelos.comprobante;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.usuario.Sesion;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "factura")
public class Factura extends Entidad {
	@Column(name = "secuencia", nullable = true)
	private String secuencia;
	@Column(name = "codigo_numerico", nullable = true)
	private String codigoNumerico;
	@Column(name = "fecha", nullable = true)
	private Date fecha;
	@Column(name = "clave_acceso", nullable = true)
	private String claveAcceso;
	@Column(name = "moneda", nullable = true)
	private String moneda;
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
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "factura_id", nullable = true)
	private List<FacturaDetalle> facturaDetalles;
    
	public Factura() {
		super();
		this.secuencia = Constantes.vacio;
		this.codigoNumerico = Constantes.vacio;
		this.fecha = new Date();
		this.claveAcceso = Constantes.vacio;
		this.moneda = Constantes.vacio;
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
		this.facturaDetalles = Collections.emptyList();
	}

	public Factura(long id) {
		super(id);
	}

	public Factura(String codigo, String secuencia, Date fecha, String estado, String claveAcceso, String moneda, double subtotalSinDescuento,
			double subtotalConDescuento, double descuentoTotal, double subtotalBase12SinDescuento, double subtotalBase0SinDescuento,
		    double subtotalBase12ConDescuento, double subtotalBase0ConDescuento, double ivaSinDescuento, double ivaConDescuento,
		    double totalSinDescuento, double totalConDescuento, double valorDescuentoSubtotal, double porcentajeDescuentoSubtotal,
		    double valorDescuentoTotal, double porcentajeDescuentoTotal, double valorPorcentajeDescuentoTotal, String comentario,
			Cliente cliente, Sesion sesion, TipoComprobante tipoComprobante) {
		super(codigo);
		this.secuencia = secuencia;
		this.fecha = fecha;
		this.estado = estado;
		this.claveAcceso = claveAcceso;
		this.moneda = moneda;
		this.subtotalSinDescuento = subtotalSinDescuento;
		this.subtotalConDescuento = subtotalConDescuento;
		this.descuentoTotal = descuentoTotal;
		this.subtotalBase12SinDescuento = subtotalBase12SinDescuento;
		this.subtotalBase0SinDescuento = subtotalBase0SinDescuento;
		this.subtotalBase12ConDescuento = subtotalBase12ConDescuento;
		this.subtotalBase0ConDescuento = subtotalBase0ConDescuento;
		this.ivaSinDescuento = ivaSinDescuento;
		this.ivaConDescuento = ivaConDescuento;
		this.totalSinDescuento = totalSinDescuento;
		this.totalConDescuento = totalConDescuento;
		this.valorDescuentoSubtotal = valorDescuentoSubtotal;
		this.porcentajeDescuentoSubtotal = porcentajeDescuentoSubtotal;
		this.valorDescuentoTotal = valorDescuentoTotal;
		this.porcentajeDescuentoTotal = porcentajeDescuentoTotal;
		this.valorPorcentajeDescuentoTotal = valorPorcentajeDescuentoTotal;
		this.comentario = comentario;
		this.cliente = cliente;
		this.sesion = sesion;
		this.tipoComprobante = tipoComprobante;
	}

	public String getSecuencia() {
		return secuencia;
	}
	
	public String getCodigoNumerico() {
		return codigoNumerico;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getEstado() {
		return estado;
	}

	public String getClaveAcceso() {
		return claveAcceso;
	}

	public String getMoneda() {
		return moneda;
	}
	
	public double getSubtotalSinDescuento() {
		return subtotalSinDescuento;
	}

	public double getSubtotalConDescuento() {
		return subtotalConDescuento;
	}

	public double getDescuentoTotal() {
		return descuentoTotal;
	}

	public double getSubtotalBase12ConDescuento() {
		return subtotalBase12ConDescuento;
	}

	public double getSubtotalBase0ConDescuento() {
		return subtotalBase0ConDescuento;
	}

	public double getSubtotalBase12SinDescuento() {
		return subtotalBase12SinDescuento;
	}

	public double getSubtotalBase0SinDescuento() {
		return subtotalBase0SinDescuento;
	}
	
	public double getIvaSinDescuento() {
		return ivaSinDescuento;
	}

	public double getIvaConDescuento() {
		return ivaConDescuento;
	}

	public double getTotalSinDescuento() {
		return totalSinDescuento;
	}

	public double getTotalConDescuento() {
		return totalConDescuento;
	}

	public double getValorDescuentoSubtotal() {
		return valorDescuentoSubtotal;
	}

	public double getPorcentajeDescuentoSubtotal() {
		return porcentajeDescuentoSubtotal;
	}

	public double getValorDescuentoTotal() {
		return valorDescuentoTotal;
	}

	public double getPorcentajeDescuentoTotal() {
		return porcentajeDescuentoTotal;
	}

	public double getValorPorcentajeDescuentoTotal() {
		return valorPorcentajeDescuentoTotal;
	}
	
	public String getComentario() {
		return comentario;
	}	

	public Cliente getCliente() {
		return cliente;
	}

	public Sesion getSesion() {
		return sesion;
	}
	
	public TipoComprobante getTipoComprobante() {
		return tipoComprobante;
	}
	
	public void setCodigoNumerico(String codigoNumerico) {
		this.codigoNumerico = codigoNumerico;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setSubtotalSinDescuento(double subtotalSinDescuento) {
		this.subtotalSinDescuento = subtotalSinDescuento;
	}

	public void setSubtotalConDescuento(double subtotalConDescuento) {
		this.subtotalConDescuento = subtotalConDescuento;
	}

	public void setDescuentoTotal(double descuentoTotal) {
		this.descuentoTotal = descuentoTotal;
	}

	public void setSubtotalBase12ConDescuento(double subtotalBase12ConDescuento) {
		this.subtotalBase12ConDescuento = subtotalBase12ConDescuento;
	}

	public void setSubtotalBase0ConDescuento(double subtotalBase0ConDescuento) {
		this.subtotalBase0ConDescuento = subtotalBase0ConDescuento;
	}

	public void setSubtotalBase0SinDescuento(double subtotalBase0SinDescuento) {
		this.subtotalBase0SinDescuento = subtotalBase0SinDescuento;
	}

	public void setSubtotalBase12SinDescuento(double subtotalBase12SinDescuento) {
		this.subtotalBase12SinDescuento = subtotalBase12SinDescuento;
	}

	public void setIvaSinDescuento(double ivaSinDescuento) {
		this.ivaSinDescuento = ivaSinDescuento;
	}

	public void setIvaConDescuento(double ivaConDescuento) {
		this.ivaConDescuento = ivaConDescuento;
	}

	public void setTotalConDescuento(double totalConDescuento) {
		this.totalConDescuento = totalConDescuento;
	}

	public void setValorDescuentoSubtotal(double valorDescuentoSubtotal) {
		this.valorDescuentoSubtotal = valorDescuentoSubtotal;
	}

	public void setPorcentajeDescuentoSubtotal(double porcentajeDescuentoSubtotal) {
		this.porcentajeDescuentoSubtotal = porcentajeDescuentoSubtotal;
	}

	public void setValorDescuentoTotal(double valorDescuentoTotal) {
		this.valorDescuentoTotal = valorDescuentoTotal;
	}

	public void setPorcentajeDescuentoTotal(double porcentajeDescuentoTotal) {
		this.porcentajeDescuentoTotal = porcentajeDescuentoTotal;
	}

	public void setValorPorcentajeDescuentoTotal(double valorPorcentajeDescuentoTotal) {
		this.valorPorcentajeDescuentoTotal = valorPorcentajeDescuentoTotal;
	}
	
	public void setTotalSinDescuento(double totalSinDescuento) {
		this.totalSinDescuento = totalSinDescuento;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}
	
	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

	public void setSesion(Sesion sesion) {
		this.sesion = sesion;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@JsonManagedReference
	public List<FacturaDetalle> getFacturaDetalles() {
		return facturaDetalles;
	}

	public void normalizar(){
		if(this.fecha == null) this.fecha = new Date();
		this.cliente = new Cliente();
		this.sesion = new Sesion();
		this.tipoComprobante = new TipoComprobante();
		this.facturaDetalles = Collections.emptyList();
	}
}
