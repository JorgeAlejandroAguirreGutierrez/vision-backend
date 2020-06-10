package com.proyecto.sicecuador.modelos.recaudacion;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.PlazoCredito;
import com.proyecto.sicecuador.otros.recaudacion.CreditoUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "credito")
@EntityListeners({CreditoUtil.class})
public class Credito extends Entidad {
    @Column(name = "saldo", nullable = true)
    private double saldo;
    @Column(name = "valor_seguro", nullable = true)
    private double valor_seguro;
    @Column(name = "interes_periodo", nullable = true)
    private double interes_periodo;
    @Column(name = "interes_anual", nullable = true)
    private double interes_anual;
    @Column(name = "primera_cuota", nullable = true)
    private Date primera_cuota;
    @Column(name = "vencimiento", nullable = true)
    private Date vencimiento;
    @Column(name = "cuota", nullable = true)
    private double cuota;
    @Column(name = "intereses", nullable = true)
    private double intereses;
    @Column(name = "recargos", nullable = true)
    private double recargos;
    @Column(name = "total", nullable = true)
    private double total;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "plazo_credito_id", nullable = true)
    private PlazoCredito plazo_credito;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "amortizacion_id", nullable = true)
    private Amortizacion amortizacion;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "modelo_tabla_id", nullable = true)
    private ModeloTabla modelo_tabla;

    public Credito(){
    }

    public Credito(long id){
        super(id);
    }

    public Credito(String codigo, double saldo, double valor_seguro, double interes_periodo, double recargos,
                   double interes_anual, Date primera_cuota, Date vencimiento,double cuota, double intereses,
                   double total, String estado,PlazoCredito plazo_credito, Amortizacion amortizacion, ModeloTabla modelo_tabla){
        super(codigo);
        this.saldo=saldo;
        this.valor_seguro=valor_seguro;
        this.recargos=recargos;
        this.interes_periodo=interes_periodo;
        this.interes_anual=interes_anual;
        this.primera_cuota=primera_cuota;
        this.vencimiento=vencimiento;
        this.cuota=cuota;
        this.intereses=intereses;
        this.total=total;
        this.estado=estado;
        this.plazo_credito=plazo_credito;
        this.modelo_tabla=modelo_tabla;
        this.amortizacion=amortizacion;
    }
    public double getSaldo() {
        return saldo;
    }

    public PlazoCredito getPlazo_credito() {
        return plazo_credito;
    }

    public double getValor_seguro() {
        return valor_seguro;
    }

    public double getRecargos() {
        return recargos;
    }

    public ModeloTabla getModelo_tabla() {
        return modelo_tabla;
    }

    public double getInteres_periodo() {
        return interes_periodo;
    }

    public double getInteres_anual() {
        return interes_anual;
    }

    public Date getPrimera_cuota() {
        return primera_cuota;
    }

    public Date getVencimiento() {
        return vencimiento;
    }

    public double getCuota() {
        return cuota;
    }

    public double getIntereses() {
        return intereses;
    }

    public double getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    public Amortizacion getAmortizacion() {
        return amortizacion;
    }
}
