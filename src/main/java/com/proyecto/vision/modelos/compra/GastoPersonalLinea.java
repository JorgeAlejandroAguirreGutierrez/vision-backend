package com.proyecto.vision.modelos.compra;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.configuracion.Impuesto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_gasto_personal_linea;

@Entity
@Table(name = tabla_gasto_personal_linea)
@Getter
@Setter
@AllArgsConstructor
public class GastoPersonalLinea extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "posicion", nullable = true)
    private long posicion;
    @Column(name = "nombre_producto", nullable = true)
    private String nombreProducto;
    @Column(name = "medida", nullable = true)
    private String medida;
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
    @Column(name = "costo_unitario", nullable = true)
    private double costoUnitario;
    @Column(name = "valor_descuento_linea", nullable = true)
    private double valorDescuentoLinea;
    @Column(name = "subtotal_linea_sin_descuento", nullable = true)
    private double subtotalLineaSinDescuento;
    @Column(name = "subtotal_linea", nullable = true)
    private double subtotalLinea;
    @Column(name = "importe_iva_linea", nullable = true)
    private double importeIvaLinea;
    @Column(name = "total_linea", nullable = true)
    private double totalLinea;
    @ManyToOne
    @JoinColumn(name = "impuesto_id", nullable = true)
    private Impuesto impuesto;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gasto_personal_id", nullable = true)
    private GastoPersonal gastoPersonal;

    public GastoPersonalLinea(long id){
        super(id);
    }
    public GastoPersonalLinea() {
        super();
        this.codigo = Constantes.vacio;
        this.posicion = Constantes.ceroId;
        this.nombreProducto = Constantes.vacio;
        this.medida = Constantes.vacio;
        this.cantidad = Constantes.ceroId;
        this.costoUnitario = Constantes.cero;
        this.valorDescuentoLinea = Constantes.cero;
        this.subtotalLineaSinDescuento = Constantes.cero;
        this.subtotalLinea = Constantes.cero;
        this.importeIvaLinea = Constantes.cero;
        this.totalLinea = Constantes.cero;
    }

    public void normalizar(){
        if(this.impuesto == null) this.impuesto = new Impuesto();
    }
}
