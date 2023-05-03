package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.venta.NotaDebitoVenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static com.proyecto.sicecuador.Constantes.tabla_nota_debito_venta_cheque;

@Entity
@Table(name = tabla_nota_debito_venta_cheque)
@Getter
@Setter
@AllArgsConstructor
public class NotaDebitoVentaCheque extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "numero", nullable = true)
    private String numero;
    @Column(name = "tipo", nullable = true)
    private String tipo;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "fecha_efectivizacion", nullable = true)
    private Date fechaEfectivizacion;
	@Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_debito_venta_id", nullable = true)
    private NotaDebitoVenta notaDebitoVenta;

    public NotaDebitoVentaCheque(long id){
        super(id);
    }
    public NotaDebitoVentaCheque(){
        super();
        this.codigo = Constantes.vacio;
        this.numero = Constantes.vacio;
        this.tipo = Constantes.vacio;
        this.fecha = new Date();
        this.fechaEfectivizacion = new Date();
        this.valor = Constantes.cero;
    }

    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.fechaEfectivizacion == null) this.fechaEfectivizacion = new Date();
        if(this.banco == null) this.banco = new Banco();
    }

}
