package com.proyecto.sicecuador.modelos.comprobante;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.usuario.Usuario;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "proforma")
public class Proforma extends Entidad {
    @Column(name = "numero_interno", nullable = true)
    private String numeroInterno;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "fecha_caducidad", nullable = true)
    private Date fechaCaducidad;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "subtotal", nullable = true)
    private double subtotal;
    @Column(name = "subdescuento", nullable = true)
    private double subdescuento;
    @Column(name = "base_iva", nullable = true)
    private double baseIva;
    @Column(name = "base_0", nullable = true)
    private double base0;
    @Column(name = "importe_iva", nullable = true)
    private double importeIva;
    @Column(name = "total", nullable = true)
    private double total;
    @Column(name = "descuento_porcentaje", nullable = true)
    private double descuentoPorcentaje;
    @Column(name = "descuento", nullable = true)
    private double descuento;
    @Column(name = "comentario", nullable = true)
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = true)
    private Usuario vendedor;

    public Proforma(){

    }

    public Proforma(long id){
        super(id);
    }

    public Proforma(String codigo, String numeroInterno, Date fecha, Date fechaCaducidad, String estado, double subtotal, double subdescuento,
                  double baseIva, double base0, double importeIva, double total, double descuentoPorcentaje,
                  double descuento, String comentario, Cliente cliente, Usuario vendedor){
        super(codigo);
        this.numeroInterno=numeroInterno;
        this.fecha=fecha;
        this.fechaCaducidad=fechaCaducidad;
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

    public Date getFechaCaducidad() {
		return fechaCaducidad;
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
