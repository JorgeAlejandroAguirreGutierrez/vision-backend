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

import static com.proyecto.vision.Constantes.tabla_nd_tarjeta_debito;

@Entity
@Table(name = tabla_nd_tarjeta_debito)
@Getter
@Setter
@AllArgsConstructor
public class NDTarjetaDebito extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
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
    @JoinColumn(name = "nota_debito_venta_id", nullable = true)
    private NotaDebito notaDebito;

    public NDTarjetaDebito(long id){
        super(id);
    }
    public NDTarjetaDebito(){
        super();
        this.codigo = Constantes.vacio;
        this.titular = Constantes.vacio;
        this.identificacion = Constantes.vacio;
        this.nombre = Constantes.vacio;
        this.lote = Constantes.vacio;
        this.valor = Constantes.cero;
        this.banco = new Banco();
    }
    public void normalizar(){
        if(this.banco == null) this.banco = new Banco();
        if(this.operadorTarjeta == null) this.operadorTarjeta = new OperadorTarjeta();
        if(this.franquiciaTarjeta == null) this.franquiciaTarjeta = new FranquiciaTarjeta();
    }
}
