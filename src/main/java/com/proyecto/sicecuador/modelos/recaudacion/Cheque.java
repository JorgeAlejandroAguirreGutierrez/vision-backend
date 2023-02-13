package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cheque")
@Data
@AllArgsConstructor
public class Cheque extends Entidad {
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
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public Cheque(long id){
        super(id);
    }
    public Cheque(){
        super();
        this.codigo = Constantes.vacio;
        this.numero = Constantes.vacio;
        this.tipo = Constantes.vacio;
        this.fecha = new Date();
        this.fechaEfectivizacion = new Date();
        this.valor = Constantes.cero;
        this.banco = new Banco();
    }

    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.fechaEfectivizacion == null) this.fechaEfectivizacion = new Date();
        if(this.banco == null) this.banco = new Banco();
    }

}
