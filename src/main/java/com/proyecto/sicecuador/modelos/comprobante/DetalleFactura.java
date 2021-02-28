package com.proyecto.sicecuador.modelos.comprobante;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.inventario.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "detalle_factura")
public class DetalleFactura extends Entidad {
	@JsonProperty("posicion")
    @Column(name = "posicion", nullable = true)
    private long posicion;
	@JsonProperty("comentario")
    @Column(name = "comentario", nullable = true)
    private long comentario;
	@JsonProperty("entregado")
    @Column(name = "entregado", nullable = true)
    private boolean entregado;
	@JsonProperty("consignacion")
    @Column(name = "consignacion", nullable = true)
    private boolean consignacion;
	@JsonProperty("cantidad")
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
	@JsonProperty("subsidio")
    @Column(name = "subsidio", nullable = true)
    private double subsidio;
	@JsonProperty("sin_subsidio")
    @Column(name = "sin_subsidio", nullable = true)
    private double sinSubsidio;
    //INDIVIDUALES
	@JsonProperty("valor_descuento_individual")
    @Column(name = "valor_descuento_individual", nullable = true)
    private double valorDescuentoIndividual;
	@JsonProperty("porcentaje_descuento_individual")
    @Column(name = "porcentaje_descuento_individual", nullable = true)
    private double porcentajeDescuentoIndividual;
	@JsonProperty("valor_porcentaje_descuento_individual")
    @Column(name = "valor_porcentaje_descuento_individual", nullable = true)
    private double valorPorcentajeDescuentoIndividual;
	@JsonProperty("total_descuento_individual")
    @Column(name = "total_descuento_individual", nullable = true)
    private double totalDescuentoIndividual;
    //FIN INDIVIDUALES

    //SUBTOTALES
	@JsonProperty("valor_descuento_individual_subtotales")
    @Column(name = "valor_descuento_individual_subtotales", nullable = true)
    private double valorDescuentoIndividualSubtotales;
	@JsonProperty("porcentaje_descuento_individual_subtotales")
    @Column(name = "porcentaje_descuento_individual_subtotales", nullable = true)
    private double porcentajeDescuentoIndividualSubtotales;
	@JsonProperty("valor_porcentaje_descuento_individual_subtotales")
    @Column(name = "valor_porcentaje_descuento_individual_subtotales", nullable = true)
    private double valorPorcentajeDescuentoIndividualSubtotales;

    //TOTALES
	@JsonProperty("valor_descuento_individual_totales")
    @Column(name = "valor_descuento_individual_totales", nullable = true)
    private double valorDescuentoIndividualTotales;
	@JsonProperty("porcentaje_descuento_individual_totales")
    @Column(name = "porcentaje_descuento_individual_totales", nullable = true)
    private double porcentajeDescuentoIndividualTotales;
	@JsonProperty("valor_porcentaje_descuento_individual_totales")
    @Column(name = "valor_porcentaje_descuento_individual_totales", nullable = true)
    private double valorPorcentajeDescuentoIndividualTotales;

	@JsonProperty("valor_descuento_totales")
    @Column(name = "valor_descuento_totales", nullable = true)
    private double valorDescuentoTotales;
	@JsonProperty("porcentaje_descuento_totales")
    @Column(name = "porcentaje_descuento_totales", nullable = true)
    private double porcentajeDescuentoTotales;
	@JsonProperty("valor_porcentaje_descuento_totales")
    @Column(name = "valor_porcentaje_descuento_totales", nullable = true)
    private double valorPorcentajeDescuentoTotales;
    
    //FIN TOTALES
	@JsonProperty("total_sin_descuento")
    @Column(name = "total_sin_descuento", nullable = true)
    private double totalSinDescuento;
	@JsonProperty("total_con_descuento")
    @Column(name = "total_con_descuento", nullable = true)
    private double totalConDescuento;
	@JsonProperty("valor_iva_sin_descuento")
    @Column(name = "valor_iva_sin_descuento", nullable = true)
    private double valorIvaSinDescuento;
	@JsonProperty("valor_iva_con_descuento")
    @Column(name = "valor_iva_con_descuento", nullable = true)
    private double valorIvaConDescuento;

    @ManyToOne
    @JsonProperty("medida")
    @JoinColumn(name = "medida_id", nullable = true)
    private Medida medida;
    @ManyToOne
    @JsonProperty("producto")
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;
    @ManyToOne
    @JsonProperty("impuesto")
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @ManyToOne
    @JsonProperty("precio")
    @JoinColumn(name = "precio_id", nullable = true)
    private Precio precio;
    @ManyToOne
    @JsonProperty("factura")
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
    @OneToMany(cascade =CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JsonProperty("caracteristicas")
    @JoinColumn(name = "detalle_factura_id")
    private List<Caracteristica> caracteristicas;


    public DetalleFactura(){

    }

    public DetalleFactura(long id){
        super(id);
    }

    public DetalleFactura(String codigo, long posicion, boolean entregado, boolean consignacion, String medida, long cantidad,
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


}

