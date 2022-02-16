package com.proyecto.sicecuador.modelos.comprobante;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.inventario.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "factura_detalle")
public class FacturaDetalle extends Entidad {
    @Column(name = "posicion", nullable = true)
    private long posicion;
    @Column(name = "comentario", nullable = true)
    private String comentario;
    @Column(name = "entregado", nullable = true)
    private boolean entregado;
    @Column(name = "consignacion", nullable = true)
    private boolean consignacion;
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
    @Column(name = "subsidio", nullable = true)
    private double subsidio;
    @Column(name = "sin_subsidio", nullable = true)
    private double sinSubsidio;
    //LINEA
    @Column(name = "valor_descuento_linea", nullable = true)
    private double valorDescuentoLinea;
    @Column(name = "porcentaje_descuento_linea", nullable = true)
    private double porcentajeDescuentoLinea;
    @Column(name = "valor_porcentaje_descuento_linea", nullable = true)
    private double valorPorcentajeDescuentoLinea;
    @Column(name = "valor_descuento_total_linea", nullable = true)
    private double valorDescuentoTotalLinea;
    @Column(name = "porcentaje_descuento_total_linea", nullable = true)
    private double porcentajeDescuentoTotalLinea;
    @Column(name = "valor_porcentaje_descuento_total_linea", nullable = true)
    private double valorPorcentajeDescuentoTotalLinea;
    @Column(name = "total_descuento_linea", nullable = true)
    private double totalDescuentoLinea;
    @Column(name = "subtotal_sin_descuento_linea", nullable = true)
    private double subtotalSinDescuentoLinea;
    @Column(name = "iva_sin_descuento_linea", nullable = true)
    private double ivaSinDescuentoLinea;
    @Column(name = "subtotal_con_descuento_linea", nullable = true)
    private double subtotalConDescuentoLinea;
    @Column(name = "iva_con_descuento_linea", nullable = true)
    private double ivaConDescuentoLinea;
    @Column(name = "total_con_descuento_linea", nullable = true)
    private double totalConDescuentoLinea;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @ManyToOne
    @JoinColumn(name = "precio_id", nullable = true)
    private Precio precio;
    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;
    @OneToMany(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_detalle_id")
    private List<Caracteristica> caracteristicas;


    public FacturaDetalle(){

    }

    public FacturaDetalle(long id){
        super(id);
    }

    public FacturaDetalle(String codigo, long posicion, boolean entregado, boolean consignacion, String medida, long cantidad,
                          double subsidio, double valorSubsidiado, double valorDescuentoIndividual, double porcentajeDescuentoIndividual,
                          double valorPorcentajeDescuentoIndividual, double valorDescuentoTotal, double porcentajeDescuentoTotal,
                          double valorPorcentajeDescuentoTotal, double totalDescuento, double subtotal, double porcentajeIva, double valorIva,
                          Producto producto, Precio precio, Factura factura){
        super(codigo);

    }

    public long getPosicion() {
        return posicion;
    }

    public String getComentario() {
		return comentario;
	}

    public boolean isEntregado() {
        return entregado;
    }

    public boolean isConsignacion() {
        return consignacion;
    }

    public long getCantidad() {
        return cantidad;
    }

    public double getSubsidio() {
        return subsidio;
    }

    public double getSinSubsidio() {
		return sinSubsidio;
	}
    
    public double getValorDescuentoLinea() {
		return valorDescuentoLinea;
	}
    
    public double getPorcentajeDescuentoLinea() {
		return porcentajeDescuentoLinea;
	}
    
    public double getValorPorcentajeDescuentoLinea() {
		return valorPorcentajeDescuentoLinea;
	}
    
    public double getValorDescuentoTotalLinea() {
		return valorDescuentoTotalLinea;
	}
    
    public double getPorcentajeDescuentoTotalLinea() {
		return porcentajeDescuentoTotalLinea;
	}
    
    public double getValorPorcentajeDescuentoTotalLinea() {
		return valorPorcentajeDescuentoTotalLinea;
	}
    
    public double getTotalDescuentoLinea() {
		return totalDescuentoLinea;
	}
    
    public double getSubtotalSinDescuentoLinea() {
		return subtotalSinDescuentoLinea;
	}
    
    public double getIvaSinDescuentoLinea() {
		return ivaSinDescuentoLinea;
	}
    
    public double getSubtotalConDescuentoLinea() {
		return subtotalConDescuentoLinea;
	}
    
    public double getIvaConDescuentoLinea() {
		return ivaConDescuentoLinea;
	}
    
    public double getTotalConDescuentoLinea() {
		return totalConDescuentoLinea;
	}

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
    }
    
    public Bodega getBodega() {
		return bodega;
	}
    
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Precio getPrecio() {
        return precio;
    }
    
    public void setValorDescuentoLinea(double valorDescuentoLinea) {
		this.valorDescuentoLinea = valorDescuentoLinea;
	}
    
    public void setPorcentajeDescuentoLinea(double porcentajeDescuentoLinea) {
		this.porcentajeDescuentoLinea = porcentajeDescuentoLinea;
	}
    
    public void setValorPorcentajeDescuentoLinea(double valorPorcentajeDescuentoLinea) {
		this.valorPorcentajeDescuentoLinea = valorPorcentajeDescuentoLinea;
	}
    
    public void setValorDescuentoTotalLinea(double valorDescuentoTotalLinea) {
		this.valorDescuentoTotalLinea = valorDescuentoTotalLinea;
	}
    
    public void setPorcentajeDescuentoTotalLinea(double porcentajeDescuentoTotalLinea) {
		this.porcentajeDescuentoTotalLinea = porcentajeDescuentoTotalLinea;
	}
    
    public void setValorPorcentajeDescuentoTotalLinea(double valorPorcentajeDescuentoTotalLinea) {
		this.valorPorcentajeDescuentoTotalLinea = valorPorcentajeDescuentoTotalLinea;
	}
    
    public void setTotalDescuentoLinea(double totalDescuentoLinea) {
		this.totalDescuentoLinea = totalDescuentoLinea;
	}
    
    public void setSubtotalSinDescuentoLinea(double subtotalSinDescuentoLinea) {
		this.subtotalSinDescuentoLinea = subtotalSinDescuentoLinea;
	}
    
    public void setIvaSinDescuentoLinea(double ivaSinDescuentoLinea) {
		this.ivaSinDescuentoLinea = ivaSinDescuentoLinea;
	}
    
    public void setSubtotalConDescuentoLinea(double subtotalConDescuentoLinea) {
		this.subtotalConDescuentoLinea = subtotalConDescuentoLinea;
	}
    
    public void setIvaConDescuentoLinea(double ivaConDescuentoLinea) {
		this.ivaConDescuentoLinea = ivaConDescuentoLinea;
	}
    
    public void setTotalConDescuentoLinea(double totalConDescuentoLinea) {
		this.totalConDescuentoLinea = totalConDescuentoLinea;
	}
    
    public void setPrecio(Precio precio) {
        this.precio = precio;
    }
    @JsonBackReference(value="factura-detalle-factura")
    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    @JsonManagedReference(value="detalle-factura-caracteristica")
    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

}

