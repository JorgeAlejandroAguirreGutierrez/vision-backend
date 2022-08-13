package com.proyecto.sicecuador.modelos.comprobante;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Dependiente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.modelos.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "factura")
public class Factura extends Entidad {
	@Column(name = "secuencia", nullable = true)
	private String secuencia;
	@Column(name = "fecha", nullable = true)
	private Date fecha;
	@Column(name = "estado", nullable = true)
	private String estado;
	@Column(name = "clave_acceso_sri")
	private String claveAccesoSri;
	@Column(name = "moneda")
	private String moneda;	
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
	@Column(name = "iva0", nullable = true)
	private double iva0;
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
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name = "cliente_factura_id", nullable = true)
	private Cliente clienteFactura;
	@ManyToOne
	@JoinColumn(name = "auxiliar_id", nullable = true)
	private Dependiente auxiliar;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "vendedor_id")
	private Usuario vendedor;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "sesion_id", nullable = true)
	private Sesion sesion;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinColumn(name = "factura_id")
	private List<FacturaDetalle> facturaDetalles;

	public Factura() {

	}

	public Factura(long id) {
		super(id);
	}

	public Factura(String secuencia) {
		super(0);
		this.secuencia = secuencia;
	}

	public Factura(String codigo, String secuencia, Date fecha, String estado, String claveAccesoSri, String moneda, double subtotal,
			double valorDescuentoSubtotal, double porcentajeDescuentoSubtotal, double valorPorcentajeDescuentoSubtotal,
			double valorDescuentoTotal, double porcentajeDescuentoTotal, double valorPorcentajeDescuentoTotal,
			double descuento, double base12, double base0, double importeIva, double total, String comentario,
			Cliente cliente, Cliente clienteFactura, Dependiente auxiliar, Usuario vendedor, Sesion sesion) {
		super(codigo);
		this.secuencia = secuencia;
		this.fecha = fecha;
		this.estado = estado;
		this.claveAccesoSri = claveAccesoSri;
		this.moneda = moneda;
		this.comentario = comentario;
		this.cliente = cliente;
		this.clienteFactura = clienteFactura;
		this.auxiliar = auxiliar;
		this.vendedor = vendedor;
		this.sesion = sesion;
	}

	public String getSecuencia() {
		return secuencia;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getEstado() {
		return estado;
	}

	public String getClaveAccesoSri() {
		return claveAccesoSri;
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

	public String getComentario() {
		return comentario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Cliente getClienteFactura() {
		return clienteFactura;
	}

	public Dependiente getAuxiliar() {
		return auxiliar;
	}

	public Sesion getSesion() {
		return sesion;
	}

	public Usuario getVendedor() {
		return vendedor;
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

	@JsonManagedReference(value = "factura-detalle-factura")
	public List<FacturaDetalle> getFacturaDetalles() {
		return facturaDetalles;
	}

	public void normalizar() {
		if (this.clienteFactura!=null && this.clienteFactura.getId() == 0) {
			this.clienteFactura = null;
		}
	}
}
