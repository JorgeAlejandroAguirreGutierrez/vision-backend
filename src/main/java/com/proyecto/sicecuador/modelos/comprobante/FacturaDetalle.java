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
    private double sin_subsidio;
    //INDIVIDUALES
    @Column(name = "valor_descuento_individual", nullable = true)
    private double valor_descuento_individual;
    @Column(name = "porcentaje_descuento_individual", nullable = true)
    private double porcentaje_descuento_individual;
    @Column(name = "valor_porcentaje_descuento_individual", nullable = true)
    private double valor_porcentaje_descuento_individual;
    @Column(name = "total_descuento_individual", nullable = true)
    private double total_descuento_individual;
    //FIN INDIVIDUALES

    //SUBTOTALES
    @Column(name = "valor_descuento_individual_subtotales", nullable = true)
    private double valor_descuento_individual_subtotales;
    @Column(name = "porcentaje_descuento_individual_subtotales", nullable = true)
    private double porcentaje_descuento_individual_subtotales;
    @Column(name = "valor_porcentaje_descuento_individual_subtotales", nullable = true)
    private double valor_porcentaje_descuento_individual_subtotales;

    //TOTALES
    @Column(name = "valor_descuento_individual_totales", nullable = true)
    private double valor_descuento_individual_totales;
    @Column(name = "porcentaje_descuento_individual_totales", nullable = true)
    private double porcentaje_descuento_individual_totales;
    @Column(name = "valor_porcentaje_descuento_individual_totales", nullable = true)
    private double valor_porcentaje_descuento_individual_totales;

    @Column(name = "valor_descuento_totales", nullable = true)
    private double valor_descuento_totales;
    @Column(name = "porcentaje_descuento_totales", nullable = true)
    private double porcentaje_descuento_totales;
    @Column(name = "valor_porcentaje_descuento_totales", nullable = true)
    private double valor_porcentaje_descuento_totales;
    
    //FIN TOTALES
    @Column(name = "total_sin_descuento", nullable = true)
    private double total_sin_descuento;
    @Column(name = "total_con_descuento", nullable = true)
    private double total_con_descuento;
    @Column(name = "valor_iva_sin_descuento", nullable = true)
    private double valor_iva_sin_descuento;
    @Column(name = "valor_iva_con_descuento", nullable = true)
    private double valor_iva_con_descuento;

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
    @JoinColumn(name = "factura_detalle_id")
    private List<Caracteristica> caracteristicas;


    public FacturaDetalle(){

    }

    public FacturaDetalle(long id){
        super(id);
    }

    public FacturaDetalle(String codigo, long posicion, boolean entregado, boolean consignacion, String medida, long cantidad,
                          double subsidio, double valor_subsidiado, double valor_descuento_individual, double porcentaje_descuento_individual,
                          double valor_porcentaje_descuento_individual, double valor_descuento_total, double porcentaje_descuento_total,
                          double valor_porcentaje_descuento_total, double total_descuento, double subtotal, double porcentaje_iva, double valor_iva,
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

    public double getSin_subsidio() {
        return sin_subsidio;
    }

    public double getValor_descuento_individual() {
        return valor_descuento_individual;
    }

    public double getPorcentaje_descuento_individual() {
        return porcentaje_descuento_individual;
    }

    public double getValor_porcentaje_descuento_individual() {
        return valor_porcentaje_descuento_individual;
    }

    public double getValor_descuento_totales() {
        return valor_descuento_totales;
    }

    public double getPorcentaje_descuento_totales() {
        return porcentaje_descuento_totales;
    }

    public double getValor_porcentaje_descuento_totales() {
        return valor_porcentaje_descuento_totales;
    }

    public double getTotal_con_descuento() {
        return total_con_descuento;
    }

    public double getTotal_sin_descuento() {
        return total_sin_descuento;
    }

    public double getValor_iva_con_descuento() {
        return valor_iva_con_descuento;
    }

    public double getValor_iva_sin_descuento() {
        return valor_iva_sin_descuento;
    }

    public double getValor_descuento_individual_totales() {
        return valor_descuento_individual_totales;
    }

    public double getPorcentaje_descuento_individual_totales() {
        return porcentaje_descuento_individual_totales;
    }

    public double getValor_porcentaje_descuento_individual_totales() {
        return valor_porcentaje_descuento_individual_totales;
    }

    public double getValor_descuento_individual_subtotales() {
        return valor_descuento_individual_subtotales;
    }

    public double getPorcentaje_descuento_individual_subtotales() {
        return porcentaje_descuento_individual_subtotales;
    }

    public double getValor_porcentaje_descuento_individual_subtotales() {
        return valor_porcentaje_descuento_individual_subtotales;
    }

    public double getTotal_descuento_individual() {
        return total_descuento_individual;
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
    @JsonBackReference(value="factura-factura-detalle")
    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    @JsonManagedReference(value="factura-detalle-caracteristica")
    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }


}

