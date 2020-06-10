package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import com.proyecto.sicecuador.otros.recaudacion.CompensacionUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "compensacion")
@EntityListeners({CompensacionUtil.class})
public class Compensacion extends Entidad {
    @Column(name = "comprobante", nullable = true)
    private String comprobante;
    @Column(name = "fecha_comprobante", nullable = true)
    private Date fecha_comprobante;
    @Column(name = "origen", nullable = true)
    private String origen;
    @Column(name = "motivo", nullable = true)
    private String motivo;
    @Column(name = "fecha_vencimiento", nullable = true)
    private Date fecha_vencimiento;
    @Column(name = "valor_origen", nullable = true)
    private double valor_origen;
    @Column(name = "saldo_anterior", nullable = true)
    private double saldo_anterior;
    @Column(name = "valor_compensado", nullable = true)
    private double valor_compensado;
    @Column(name = "saldo", nullable = true)
    private double saldo;
    @Column(name = "compensado", nullable = true)
    private double compensado;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tipo_comprobante_id", nullable = true)
    private TipoComprobante tipo_comprobante;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public Compensacion(){
        super();
    }

    public Compensacion(long id){
        super(id);
    }
    @JsonBackReference
    public Recaudacion getRecaudacion() {
        return recaudacion;
    }

}
