package com.proyecto.sicecuador.modelos.comprobante;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
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
	@JsonProperty("numero")
	@Column(name = "numero", nullable = true)
	private String numero;
	@JsonProperty("fecha")
	@Column(name = "fecha", nullable = true)
	private Date fecha;
	@JsonProperty("estado")
	@Column(name = "estado", nullable = true)
	private boolean estado;
	@JsonProperty("subtotal_sin_descuento")
	@Column(name = "subtotal_sin_descuento", nullable = true)
	private double subtotalSinDescuento;
	@JsonProperty("subtotal_con_descuento")
	@Column(name = "subtotal_con_descuento", nullable = true)
	private double subtotalConDescuento;
	@JsonProperty("descuento_total")
	@Column(name = "descuento_total", nullable = true)
	private double descuentoTotal;
	@JsonProperty("subtotal_base12_sin_descuento")
	@Column(name = "subtotal_base12_sin_descuento", nullable = true)
	private double subtotalBase12SinDescuento;
	@JsonProperty("subtotal_base0_sin_descuento")
	@Column(name = "subtotal_base0_sin_descuento", nullable = true)
	private double subtotalBase0SinDescuento;
	@JsonProperty("subtotal_base12_con_descuento")
	@Column(name = "subtotal_base12_con_descuento", nullable = true)
	private double subtotalBase12ConDescuento;
	@JsonProperty("subtotal_base0_con_descuento")
	@Column(name = "subtotal_base0_con_descuento", nullable = true)
	private double subtotalBase0ConDescuento;
	@JsonProperty("importe_iva_sin_descuento")
	@Column(name = "importe_iva_sin_descuento", nullable = true)
	private double importeIvaSinDescuento;
	@JsonProperty("importe_iva_con_descuento")
	@Column(name = "importe_iva_con_descuento", nullable = true)
	private double importeIvaConDescuento;
	@JsonProperty("total_sin_descuento")
	@Column(name = "total_sin_descuento", nullable = true)
	private double totalSinDescuento;
	@JsonProperty("total_con_descuento")
	@Column(name = "total_con_descuento", nullable = true)
	private double totalConDescuento;

	// GENERAL
	@JsonProperty("valor_descuento_subtotal")
	@Column(name = "valor_descuento_subtotal", nullable = true)
	private double valorDescuentoSubtotal;
	@JsonProperty("porcentaje_descuento_subtotal")
	@Column(name = "porcentaje_descuento_subtotal", nullable = true)
	private double porcentajeDescuentoSubtotal;
	@JsonProperty("valor_descuento_total")
	@Column(name = "valor_descuento_total", nullable = true)
	private double valorDescuentoTotal;
	@JsonProperty("porcentaje_descuento_total")
	@Column(name = "porcentaje_descuento_total", nullable = true)
	private double porcentajeDescuentoTotal;
	@JsonProperty("valor_porcentaje_descuento_total")
	@Column(name = "valor_porcentaje_descuento_total", nullable = true)
	private double valorPorcentajeDescuentoTotal;

	@JsonProperty("comentario")
	@Column(name = "comentario", nullable = true)
	private String comentario;
	@NotNull
	@ManyToOne
	@JsonProperty("cliente")
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	@ManyToOne
	@JsonProperty("cliente_factura")
	@JoinColumn(name = "cliente_factura_id", nullable = true)
	private Cliente clienteFactura;
	@ManyToOne
	@JsonProperty("auxiliar")
	@JoinColumn(name = "auxiliar_id", nullable = true)
	private Auxiliar auxiliar;
	@NotNull
	@ManyToOne
	@JsonProperty("vendedor")
	@JoinColumn(name = "vendedor_id")
	private Usuario vendedor;
	@NotNull
	@ManyToOne
	@JsonProperty("sesion")
	@JoinColumn(name = "sesion_id", nullable = true)
	private Sesion sesion;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JsonProperty("factura_detalles")
	@JoinColumn(name = "factura_id")
	private List<FacturaDetalle> facturaDetalles;

	public Factura() {

	}

	public Factura(long id) {
		super(id);
	}

	public Factura(String numero) {
		super(0);
		this.numero = numero;
	}

	public Factura(String codigo, String numero, Date fecha, boolean estado, double subtotal,
			double valorDescuentoSubtotal, double porcentajeDescuentoSubtotal, double valorPorcentajeDescuentoSubtotal,
			double valorDescuentoTotal, double porcentajeDescuentoTotal, double valorPorcentajeDescuentoTotal,
			double descuento, double base12, double base0, double importeIva, double total, String comentario,
			Cliente cliente, Cliente clienteFactura, Auxiliar auxiliar, Usuario vendedor, Sesion sesion) {
		super(codigo);
		this.numero = numero;
		this.fecha = fecha;
		this.estado = estado;
		this.comentario = comentario;
		this.cliente = cliente;
		this.clienteFactura = clienteFactura;
		this.auxiliar = auxiliar;
		this.vendedor = vendedor;
		this.sesion = sesion;
	}

	public String getNumero() {
		return numero;
	}

	public Date getFecha() {
		return fecha;
	}

	public boolean isEstado() {
		return estado;
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

	public double getImporteIvaConDescuento() {
		return importeIvaConDescuento;
	}

	public double getImporteIvaSinDescuento() {
		return importeIvaSinDescuento;
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

	public void setImporteIvaSinDescuento(double importeIvaSinDescuento) {
		this.importeIvaSinDescuento = importeIvaSinDescuento;
	}

	public void setImporteIvaConDescuento(double importeIvaConDescuento) {
		this.importeIvaConDescuento = importeIvaConDescuento;
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

	public String getComentario() {
		return comentario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Cliente getClienteFactura() {
		return clienteFactura;
	}

	public Auxiliar getAuxiliar() {
		return auxiliar;
	}

	public Sesion getSesion() {
		return sesion;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setNumero(String numero) {
		this.numero = numero;
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
		if (this.clienteFactura.getId() == 0) {
			this.clienteFactura = null;
		}
	}
}
