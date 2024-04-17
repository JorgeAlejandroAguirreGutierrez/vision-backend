package com.proyecto.vision.modelos.venta;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.acceso.Empresa;
import com.proyecto.vision.modelos.acceso.Sesion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;

import static com.proyecto.vision.Constantes.tabla_cierre_caja;

@Entity
@Table(name = tabla_cierre_caja)
@Getter
@Setter
@AllArgsConstructor
public class CierreCaja extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "billete_100", nullable = true)
    private double billete100;
    @Column(name = "billete_50", nullable = true)
    private double billete50;
    @Column(name = "billete_20", nullable = true)
    private double billete20;
    @Column(name = "billete_10", nullable = true)
    private double billete10;
    @Column(name = "billete_5", nullable = true)
    private double billete5;
    @Column(name = "billete_2", nullable = true)
    private double billete2;
    @Column(name = "billete_1", nullable = true)
    private double billete1;
    @Column(name = "moneda_100", nullable = true)
    private double moneda100;
    @Column(name = "moneda_50", nullable = true)
    private double moneda50;
    @Column(name = "moneda_25", nullable = true)
    private double moneda25;
    @Column(name = "moneda_10", nullable = true)
    private double moneda10;
    @Column(name = "moneda_5", nullable = true)
    private double moneda5;
    @Column(name = "moneda_1", nullable = true)
    private double moneda1;
    @Column(name = "total_billetes", nullable = true)
    private double totalBilletes;
    @Column(name = "total_monedas", nullable = true)
    private double totalMonedas;
    @Column(name = "total_caja", nullable = true)
    private double totalCaja;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "sesion_id", nullable = true)
    private Sesion sesion;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;

    public CierreCaja(long id){
        super(id);
    }
    public CierreCaja(){
        super();
        this.codigo = Constantes.vacio;
        this.fecha = new Date();
        this.billete100 = Constantes.cero;
        this.billete50 = Constantes.cero;
        this.billete20 = Constantes.cero;
        this.billete10 = Constantes.cero;
        this.billete5 = Constantes.cero;
        this.billete2 = Constantes.cero;
        this.billete1 = Constantes.cero;
        this.moneda100 = Constantes.cero;
        this.moneda50 = Constantes.cero;
        this.moneda25 = Constantes.cero;
        this.moneda10 = Constantes.cero;
        this.moneda5 = Constantes.cero;
        this.moneda1 = Constantes.cero;
        this.totalBilletes = Constantes.cero;
        this.totalMonedas = Constantes.cero;
        this.totalCaja = Constantes.cero;
        this.estado = Constantes.estadoActivo;
    }

    public void normalizar(){
        if(this.empresa == null) this.empresa = new Empresa();
    }
}
