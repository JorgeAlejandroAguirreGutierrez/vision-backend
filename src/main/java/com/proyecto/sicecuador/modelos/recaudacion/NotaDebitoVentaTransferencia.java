package com.proyecto.sicecuador.modelos.recaudacion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.NotaDebitoVenta;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static com.proyecto.sicecuador.Constantes.tabla_nota_debito_venta_transferencia;

@Entity
@Table(name = tabla_nota_debito_venta_transferencia)
@Data
@AllArgsConstructor
public class NotaDebitoVentaTransferencia extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "tipo_transaccion", nullable = true)
    private String tipoTransaccion;
    @Column(name = "numero_transaccion", nullable = true)
    private String numeroTransaccion;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "nota_debito_venta_id", nullable = true)
    private NotaDebitoVenta notaDebitoVenta;

    public NotaDebitoVentaTransferencia(long id){
        super(id);
    }
    public NotaDebitoVentaTransferencia(){
        super();
        this.codigo = Constantes.vacio;
        this.fecha = new Date();
        this.tipoTransaccion = Constantes.vacio;
        this.numeroTransaccion = Constantes.vacio;
        this.valor = Constantes.cero;
        this.banco = new Banco();
    }
    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.banco == null) this.banco = new Banco();
    }
}
