package com.proyecto.sicecuador.modelos.recaudacion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.NotaDebitoVenta;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

import static com.proyecto.sicecuador.Constantes.tabla_nota_debito_venta_tarjeta_credito;

@Entity
@Table(name = tabla_nota_debito_venta_tarjeta_credito)
@Data
@AllArgsConstructor
public class NotaDebitoVentaTarjetaCredito extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "diferido", nullable = true)
    private String diferido;
    @Column(name = "titular", nullable = true)
    private String titular;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "nombre_titular", nullable = true)
    private String nombreTitular;
    @Column(name = "lote", nullable = true)
    private String lote;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @ManyToOne
    @JoinColumn(name = "operador_tarjeta_id", nullable = true)
    private OperadorTarjeta operadorTarjeta;
    @ManyToOne
    @JoinColumn(name = "franquicia_tarjeta_id", nullable = true)
    private FranquiciaTarjeta franquiciaTarjeta;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "nota_debito_venta_id", nullable = true)
    private NotaDebitoVenta notaDebitoVenta;

    public NotaDebitoVentaTarjetaCredito(long id){
        super(id);
    }
    public NotaDebitoVentaTarjetaCredito(){
        super();
        this.codigo = Constantes.vacio;
        this.diferido = Constantes.no;
        this.titular = Constantes.no;
        this.identificacion = Constantes.vacio;
        this.nombreTitular = Constantes.vacio;
        this.lote = Constantes.vacio;
        this.valor = Constantes.cero;
        this.operadorTarjeta = new OperadorTarjeta();
        this.franquiciaTarjeta = new FranquiciaTarjeta();
    }
    public void normalizar(){
        if(this.banco == null) this.banco = new Banco();
        if(this.operadorTarjeta == null) this.operadorTarjeta = new OperadorTarjeta();
        if(this.franquiciaTarjeta == null) this.franquiciaTarjeta = new FranquiciaTarjeta();
    }
}
