package com.proyecto.vision.modelos.venta;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.cliente.Cliente;
import com.proyecto.vision.modelos.recaudacion.*;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.modelos.usuario.Sesion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.proyecto.vision.Constantes.tabla_factura;

@Entity
@Table(name = tabla_factura)
@Getter
@Setter
@AllArgsConstructor
public class Factura extends Entidad {
	@Column(name = "codigo", nullable = true)
	private String codigo;
	@Column(name = "establecimiento", nullable = true)
	private String establecimiento;
	@Column(name = "punto_venta", nullable = true)
	private String puntoVenta;
	@Column(name = "secuencial", nullable = true)
	private String secuencial;
	@Column(name = "numero_comprobante", nullable = true)
	private String numeroComprobante;
	@Column(name = "codigo_numerico", nullable = true)
	private String codigoNumerico;
	@Column(name = "fecha", nullable = true)
	private Date fecha;
	@Column(name = "clave_acceso", nullable = true)
	private String claveAcceso;
	@Column(name = "fecha_autorizacion", nullable = true)
	private Date fechaAutorizacion;
	@Column(name = "estado_inteno", nullable = true)
	private String estadoInterno;
	@Column(name = "estado", nullable = true)
	private String estado;
	@Column(name = "estado_sri", nullable = true)
	private String estadoSri;

	@Column(name = "subtotal", nullable = true)
	private double subtotal;
	@Column(name = "descuento", nullable = true)
	private double descuento;
	@Column(name = "subtotal_gravado", nullable = true)
	private double subtotalGravado;
	@Column(name = "subtotal_no_gravado", nullable = true)
	private double subtotalNoGravado;
	@Column(name = "importe_iva", nullable = true)
	private double importeIva;
	@Column(name = "total", nullable = true)
	private double total;

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
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;
	@JsonManagedReference
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE })
	@JoinColumn(name = "factura_id", nullable = true)
	private List<FacturaLinea> facturaLineas;

	//RECAUDACION
	@Column(name = "total_recaudacion", nullable = true)
	private double totalRecaudacion;
	@Column(name = "por_pagar", nullable = true)
	private double porPagar;
	@Column(name = "efectivo", nullable = true)
	private double efectivo;
	@Column(name = "cambio", nullable = true)
	private double cambio;
	@Column(name = "total_cheques", nullable = true)
	private double totalCheques;
	@Column(name = "total_depositos", nullable = true)
	private double totalDepositos;
	@Column(name = "total_transferencias", nullable = true)
	private double totalTransferencias;
	@Column(name = "total_tarjetas_debitos", nullable = true)
	private double totalTarjetasDebitos;
	@Column(name = "total_tarjetas_creditos", nullable = true)
	private double totalTarjetasCreditos;
	@Column(name = "total_credito", nullable = true)
	private double totalCredito;
	@JsonManagedReference
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name = "cheque_id", nullable = true)
	private List<Cheque> cheques;
	@JsonManagedReference
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name = "deposito_id", nullable = true)
	private List<Deposito> depositos;
	@JsonManagedReference
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name = "transferencia_id", nullable = true)
	private List<Transferencia> transferencias;
	@JsonManagedReference
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name = "tarjeta_debito_id", nullable = true)
	private List<TarjetaDebito> tarjetasDebitos;
	@JsonManagedReference
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name = "tarjeta_credito_id", nullable = true)
	private List<TarjetaCredito> tarjetasCreditos;
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name = "credito_id", nullable = true)
	private Credito credito;

	public Factura(long id){
		super(id);
	}
	public Factura() {
		super();
		this.codigo = Constantes.vacio;
		this.establecimiento = Constantes.vacio;
		this.puntoVenta = Constantes.vacio;
		this.secuencial = Constantes.vacio;
		this.numeroComprobante = Constantes.vacio;
		this.codigoNumerico = Constantes.vacio;
		this.fecha = new Date();
		this.claveAcceso = Constantes.vacio;
		this.fechaAutorizacion = null;
		this.estado = Constantes.estadoActivo;
		this.estadoInterno = Constantes.estadoInternoEmitida;
		this.estadoSri = Constantes.estadoSriPendiente;
		this.descuento = Constantes.cero;
		this.subtotalGravado = Constantes.cero;
		this.subtotalNoGravado = Constantes.cero;
		this.importeIva = Constantes.cero;
		this.total = Constantes.cero;

		this.comentario = Constantes.vacio;
		this.facturaLineas = Collections.emptyList();

		//RECAUDACION
		this.totalRecaudacion = Constantes.cero;
		this.porPagar = Constantes.cero;
		this.efectivo = Constantes.cero;
		this.cambio = Constantes.cero;
		this.totalCheques = Constantes.cero;
		this.totalDepositos = Constantes.cero;
		this.totalTransferencias = Constantes.cero;
		this.totalTarjetasDebitos = Constantes.cero;
		this.totalTarjetasCreditos = Constantes.cero;
		this.totalCredito = Constantes.cero;
		this.cheques = Collections.emptyList();
		this.depositos = Collections.emptyList();
		this.transferencias = Collections.emptyList();
		this.tarjetasDebitos = Collections.emptyList();
		this.tarjetasCreditos = Collections.emptyList();
	}

	public void normalizar(){
		if(this.fecha == null) this.fecha = new Date();
		if(this.cliente == null) this.cliente = new Cliente();
		if(this.sesion == null) this.sesion = new Sesion();
		if(this.tipoComprobante == null) this.tipoComprobante = new TipoComprobante();
		if(this.facturaLineas.isEmpty()) this.facturaLineas = Collections.emptyList();
		//RECAUDACION
		if(this.cheques == null) this.cheques = Collections.emptyList();
		if(this.depositos == null) this.depositos = Collections.emptyList();
		if(this.transferencias == null) this.transferencias = Collections.emptyList();
		if(this.tarjetasDebitos == null) this.tarjetasDebitos = Collections.emptyList();
		if(this.tarjetasCreditos == null) this.tarjetasCreditos = Collections.emptyList();
		if(this.credito == null) this.credito = new Credito();
	}
}