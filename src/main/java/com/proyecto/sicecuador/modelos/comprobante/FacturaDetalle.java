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
    private long comentario;
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
    //INDIVIDUALES
    @Column(name = "valor_descuento_individual", nullable = true)
    private double valorDescuentoIndividual;
    @Column(name = "porcentaje_descuento_individual", nullable = true)
    private double porcentajeDescuentoIndividual;
    @Column(name = "valor_porcentaje_descuento_individual", nullable = true)
    private double valorPorcentajeDescuentoIndividual;
    @Column(name = "total_descuento_individual", nullable = true)
    private double totalDescuentoIndividual;
    //FIN INDIVIDUALES

    //SUBTOTALES
    @Column(name = "valor_descuento_individual_subtotales", nullable = true)
    private double valorDescuentoIndividualSubtotales;
    @Column(name = "porcentaje_descuento_individual_subtotales", nullable = true)
    private double porcentajeDescuentoIndividualSubtotales;
    @Column(name = "valor_porcentaje_descuento_individual_subtotales", nullable = true)
    private double valorPorcentajeDescuentoIndividualSubtotales;

    //TOTALES
    @Column(name = "valor_descuento_individual_totales", nullable = true)
    private double valorDescuentoIndividualTotales;
    @Column(name = "porcentaje_descuento_individual_totales", nullable = true)
    private double porcentajeDescuentoIndividualTotales;
    @Column(name = "valor_porcentaje_descuento_individual_totales", nullable = true)
    private double valorPorcentajeDescuentoIndividualTotales;

    @Column(name = "valor_descuento_totales", nullable = true)
    private double valorDescuentoTotales;
    @Column(name = "porcentaje_descuento_totales", nullable = true)
    private double porcentajeDescuentoTotales;
    @Column(name = "valor_porcentaje_descuento_totales", nullable = true)
    private double valorPorcentajeDescuentoTotales;
    
    //FIN TOTALES
    @Column(name = "total_sin_descuento", nullable = true)
    private double totalSinDescuento;
    @Column(name = "total_con_descuento", nullable = true)
    private double totalConDescuento;
    @Column(name = "valor_iva_sin_descuento", nullable = true)
    private double valorIvaSinDescuento;
    @Column(name = "valor_iva_con_descuento", nullable = true)
    private double valorIvaConDescuento;

    @ManyToOne
    @JoinColumn(name = "medida_id", nullable = true)
    private Medida medida;
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
    @OneToMany(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "detalle_factura_id")
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

    public long getComentario() {
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

    public double getValorDescuentoIndividual() {
		return valorDescuentoIndividual;
	}

    public double getPorcentajeDescuentoIndividual() {
		return porcentajeDescuentoIndividual;
	}

    public double getValorPorcentajeDescuentoIndividual() {
		return valorPorcentajeDescuentoIndividual;
	}

    public double getValorDescuentoIndividualTotales() {
		return valorDescuentoIndividualTotales;
	}

    public double getPorcentajeDescuentoTotales() {
		return porcentajeDescuentoTotales;
	}

    public double getValorPorcentajeDescuentoIndividualSubtotales() {
		return valorPorcentajeDescuentoIndividualSubtotales;
	}

    public double getTotalConDescuento() {
		return totalConDescuento;
	}

    public double getTotalSinDescuento() {
		return totalSinDescuento;
	}

    public double getValorIvaConDescuento() {
		return valorIvaConDescuento;
	}

    public double getValorIvaSinDescuento() {
		return valorIvaSinDescuento;
	}

    public double getValorDescuentoIndividualSubtotales() {
		return valorDescuentoIndividualSubtotales;
	}

    public double getPorcentajeDescuentoIndividualSubtotales() {
		return porcentajeDescuentoIndividualSubtotales;
	}

    public double getValorPorcentajeDescuentoIndividualTotales() {
		return valorPorcentajeDescuentoIndividualTotales;
	}

    public double getValorDescuentoTotales() {
		return valorDescuentoTotales;
	}

    public double getValorPorcentajeDescuentoTotales() {
		return valorPorcentajeDescuentoTotales;
	}

    public double getPorcentajeDescuentoIndividualTotales() {
		return porcentajeDescuentoIndividualTotales;
	}

    public double getTotalDescuentoIndividual() {
		return totalDescuentoIndividual;
	}
    
    
    public Medida getMedida() {
        return medida;
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
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
    
    /* SETS**/
    
    public void setPorcentajeDescuentoIndividual(double porcentajeDescuentoIndividual) {
		this.porcentajeDescuentoIndividual = porcentajeDescuentoIndividual;
	}
    
    public void setPorcentajeDescuentoIndividualSubtotales(double porcentajeDescuentoIndividualSubtotales) {
		this.porcentajeDescuentoIndividualSubtotales = porcentajeDescuentoIndividualSubtotales;
	}
    
    public void setPorcentajeDescuentoIndividualTotales(double porcentajeDescuentoIndividualTotales) {
		this.porcentajeDescuentoIndividualTotales = porcentajeDescuentoIndividualTotales;
	}
    
    public void setPorcentajeDescuentoTotales(double porcentajeDescuentoTotales) {
		this.porcentajeDescuentoTotales = porcentajeDescuentoTotales;
	}
    
    public void setTotalConDescuento(double totalConDescuento) {
		this.totalConDescuento = totalConDescuento;
	}
    
    public void setTotalDescuentoIndividual(double totalDescuentoIndividual) {
		this.totalDescuentoIndividual = totalDescuentoIndividual;
	}
    
    public void setTotalSinDescuento(double totalSinDescuento) {
		this.totalSinDescuento = totalSinDescuento;
	}
    
    public void setValorDescuentoIndividual(double valorDescuentoIndividual) {
		this.valorDescuentoIndividual = valorDescuentoIndividual;
	}
    
    public void setValorDescuentoIndividualSubtotales(double valorDescuentoIndividualSubtotales) {
		this.valorDescuentoIndividualSubtotales = valorDescuentoIndividualSubtotales;
	}
    
    public void setValorDescuentoIndividualTotales(double valorDescuentoIndividualTotales) {
		this.valorDescuentoIndividualTotales = valorDescuentoIndividualTotales;
	}
    
    public void setValorDescuentoTotales(double valorDescuentoTotales) {
		this.valorDescuentoTotales = valorDescuentoTotales;
	}
    
    public void setValorIvaConDescuento(double valorIvaConDescuento) {
		this.valorIvaConDescuento = valorIvaConDescuento;
	}
    
    public void setValorIvaSinDescuento(double valorIvaSinDescuento) {
		this.valorIvaSinDescuento = valorIvaSinDescuento;
	}
    
    public void setValorPorcentajeDescuentoIndividual(double valorPorcentajeDescuentoIndividual) {
		this.valorPorcentajeDescuentoIndividual = valorPorcentajeDescuentoIndividual;
	}
    
    public void setValorPorcentajeDescuentoIndividualSubtotales(double valorPorcentajeDescuentoIndividualSubtotales) {
		this.valorPorcentajeDescuentoIndividualSubtotales = valorPorcentajeDescuentoIndividualSubtotales;
	}
    
    public void setValorPorcentajeDescuentoIndividualTotales(double valorPorcentajeDescuentoIndividualTotales) {
		this.valorPorcentajeDescuentoIndividualTotales = valorPorcentajeDescuentoIndividualTotales;
	}
    
    public void setValorPorcentajeDescuentoTotales(double valorPorcentajeDescuentoTotales) {
		this.valorPorcentajeDescuentoTotales = valorPorcentajeDescuentoTotales;
	}

}

