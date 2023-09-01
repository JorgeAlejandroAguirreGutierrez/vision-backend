package com.proyecto.vision.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static com.proyecto.vision.Constantes.tabla_kardex;

@Entity
@Table(name = tabla_kardex)
@Getter
@Setter
@AllArgsConstructor
public class Kardex extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "referencia", nullable = true)
    private String referencia;
    @Column(name = "id_linea", nullable = true)
    private long idLinea;
    @Column(name = "entrada", nullable = true)
    private double entrada;
    @Column(name = "salida", nullable = true)
    private double salida;
    @Column(name = "saldo", nullable = true)
    private double saldo;
    @Column(name = "debe", nullable = true)
    private double debe;
    @Column(name = "haber", nullable = true)
    private double haber;
    @Column(name = "costo_promedio", nullable = true)
    private double costoPromedio;
    @Column(name = "costo_total", nullable = true)
    private double costoTotal;
    @ManyToOne
    @JoinColumn(name = "tipo_comprobante_id", nullable = true)
    private TipoComprobante tipoComprobante;
    @ManyToOne
    @JoinColumn(name = "tipo_operacion_id", nullable = true)
    private TipoOperacion tipoOperacion;
    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;

    public Kardex(long id){
        super(id);
    }
    public Kardex(){
        super();
        this.codigo = Constantes.vacio;
        this.fecha = new Date();
        this.referencia = Constantes.vacio;
        this.idLinea = Constantes.ceroId;
        this.entrada = Constantes.cero;
        this.salida = Constantes.cero;
        this.saldo = Constantes.cero;
        this.debe = Constantes.cero;
        this.haber = Constantes.cero;
        this.costoPromedio = Constantes.cero;
        this.costoTotal = Constantes.cero;
    }
}
