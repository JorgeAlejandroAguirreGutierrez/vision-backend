package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static com.proyecto.sicecuador.Constantes.tabla_kardex;

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
    @Column(name = "documento", nullable = true)
    private String documento;
    @Column(name = "operacion", nullable = true)
    private String operacion;
    @Column(name = "secuencial", nullable = true)
    private String secuencial;
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
    @Column(name = "costo_unitario", nullable = true)
    private double costoUnitario;
    @Column(name = "costo_total", nullable = true)
    private double costoTotal;
    @ManyToOne
    @JoinColumn(name = "bodega_id", nullable = true)
    private Bodega bodega;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = true)
    private Producto producto;

    public Kardex(long id){
        super(id);
    }
    public Kardex(){
        super();
        this.codigo = Constantes.vacio;
        this.fecha = new Date();
        this.documento = Constantes.vacio;
        this.operacion = Constantes.vacio;
        this.secuencial = Constantes.vacio;
        this.entrada = Constantes.cero;
        this.salida = Constantes.cero;
        this.saldo = Constantes.cero;
        this.debe = Constantes.cero;
        this.haber = Constantes.cero;
        this.costoUnitario = Constantes.cero;
        this.costoTotal = Constantes.cero;
    }
}
