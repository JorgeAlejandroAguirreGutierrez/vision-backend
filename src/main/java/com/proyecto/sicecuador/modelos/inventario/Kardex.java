package com.proyecto.sicecuador.modelos.inventario;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "kardex")
@Data
@AllArgsConstructor
public class Kardex extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "documento", nullable = true)
    private String documento;
    @Column(name = "numero", nullable = true)
    private String numero;
    @Column(name = "operacion", nullable = true)
    private String operacion;
    @Column(name = "entrada", nullable = true)
    private double entrada;
    @Column(name = "salida", nullable = true)
    private double salida;
    @Column(name = "debe", nullable = true)
    private double debe;
    @Column(name = "haber", nullable = true)
    private double haber;
    @Column(name = "cantidad", nullable = true)
    private long cantidad;
    @Column(name = "costo_unitario", nullable = true)
    private double costoUnitario;
    @Column(name = "costo_total", nullable = true)
    private double costoTotal;
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
        this.documento = Constantes.vacio;
        this.numero = Constantes.vacio;
        this.operacion = Constantes.vacio;
        this.entrada = Constantes.cero;
        this.salida = Constantes.cero;
        this.debe = Constantes.cero;
        this.haber = Constantes.cero;
        this.cantidad = Constantes.ceroId;
        this.costoUnitario = Constantes.cero;
        this.costoTotal = Constantes.cero;
    }
}
