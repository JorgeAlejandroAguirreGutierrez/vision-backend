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
import java.util.Date;

import static com.proyecto.vision.Constantes.tabla_nd_cheque;

@Entity
@Table(name = tabla_nd_cheque)
@Getter
@Setter
@AllArgsConstructor
public class NDCheque extends Entidad {
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
    @JoinColumn(name = "nota_debito_id", nullable = true)
    private NotaDebito notaDebito;

    public NDCheque(long id){
        super(id);
    }
    public NDCheque(){
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
