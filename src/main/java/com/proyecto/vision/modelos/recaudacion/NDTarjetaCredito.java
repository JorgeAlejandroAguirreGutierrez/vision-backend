package com.proyecto.vision.modelos.recaudacion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.cajaBanco.Banco;
import com.proyecto.vision.modelos.venta.NotaDebito;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static com.proyecto.vision.Constantes.tabla_nd_tarjeta_credito;

@Entity
@Table(name = tabla_nd_tarjeta_credito)
@Getter
@Setter
@AllArgsConstructor
public class NDTarjetaCredito extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "diferido", nullable = true)
    private String diferido;
    @Column(name = "titular", nullable = true)
    private String titular;
    @Column(name = "identificacion", nullable = true)
    private String identificacion;
    @Column(name = "nombre", nullable = true)
    private String nombre;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_debito_id", nullable = true)
    private NotaDebito notaDebito;

    public NDTarjetaCredito(long id){
        super(id);
    }
    public NDTarjetaCredito(){
        super();
        this.codigo = Constantes.vacio;
        this.diferido = Constantes.no;
        this.titular = Constantes.no;
        this.identificacion = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.lote = Constantes.vacio;
        this.valor = Constantes.cero;
    }
    public void normalizar(){
        if(this.banco == null) this.banco = new Banco();
        if(this.operadorTarjeta == null) this.operadorTarjeta = new OperadorTarjeta();
        if(this.franquiciaTarjeta == null) this.franquiciaTarjeta = new FranquiciaTarjeta();
    }
}
