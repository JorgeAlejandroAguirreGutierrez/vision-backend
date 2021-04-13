package com.proyecto.sicecuador.modelos.comprobante;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.usuario.Usuario;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pedido")
public class Pedido extends Entidad {
	@JsonProperty("numero_interno")
    @Column(name = "numero_interno", nullable = true)
    private String numeroInterno;
	@JsonProperty("fecha")
    @Column(name = "fecha", nullable = true)
    private Date fecha;
	@JsonProperty("fecha_entrega")
    @Column(name = "fecha_entrega", nullable = true)
    private Date fechaEntrega;
	@JsonProperty("estado")
    @Column(name = "estado", nullable = true)
    private String estado;
	@JsonProperty("subtotal")
    @Column(name = "subtotal", nullable = true)
    private double subtotal;
	@JsonProperty("subdescuento")
    @Column(name = "subdescuento", nullable = true)
    private double subdescuento;
	@JsonProperty("base_iva")
    @Column(name = "base_iva", nullable = true)
    private double baseIva;
	@JsonProperty("base_0")
    @Column(name = "base_0", nullable = true)
    private double base0;
	@JsonProperty("importe_iva")
    @Column(name = "importe_iva", nullable = true)
    private double importeIva;
	@JsonProperty("total")
    @Column(name = "total", nullable = true)
    private double total;
	@JsonProperty("descuento_porcentaje")
    @Column(name = "descuento_porcentaje", nullable = true)
    private double descuentoPorcentaje;
	@JsonProperty("descuento")
    @Column(name = "descuento", nullable = true)
    private double descuento;
	@JsonProperty("comentario")
    @Column(name = "comentario", nullable = true)
    private String comentario;
    @ManyToOne
    @JsonProperty("cliente")
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;
    @ManyToOne
    @JsonProperty("vendedor")
    @JoinColumn(name = "vendedor_id", nullable = true)
    private Usuario vendedor;

    public Pedido(){

    }

    public Pedido(long id){
        super(id);
    }

    public Pedido(String codigo, String numeroInterno, Date fecha,Date fechaEntrega, String estado, double subtotal, double subdescuento,
                  double baseIva, double base0, double importeIva, double total, double descuentoPorcentaje,
                  double descuento, String comentario, Cliente cliente, Usuario vendedor){
        super(codigo);
        this.numeroInterno=numeroInterno;
        this.fecha=fecha;
        this.fechaEntrega=fechaEntrega;
        this.estado=estado;
        this.subtotal=subtotal;
        this.subdescuento=subdescuento;
        this.baseIva=baseIva;
        this.base0=base0;
        this.importeIva=importeIva;
        this.total=total;
        this.descuentoPorcentaje=descuentoPorcentaje;
        this.descuento=descuento;
        this.comentario=comentario;
        this.cliente=cliente;
        this.vendedor=vendedor;
    }
    
    
    public String getNumeroInterno() {
		return numeroInterno;
	}

    public Date getFecha() {
        return fecha;
    }

    public Date getFechaEntrega() {
		return fechaEntrega;
	}

    public String getEstado() {
        return estado;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getSubdescuento() {
        return subdescuento;
    }

    public double getBaseIva() {
		return baseIva;
	}

    public double getBase0() {
		return base0;
	}

    public double getImporteIva() {
		return importeIva;
	}

    public double getTotal() {
        return total;
    }

    public double getDescuentoPorcentaje() {
		return descuentoPorcentaje;
	}

    public double getDescuento() {
        return descuento;
    }

    public String getComentario() {
        return comentario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Usuario getVendedor() {
        return vendedor;
    }
}
