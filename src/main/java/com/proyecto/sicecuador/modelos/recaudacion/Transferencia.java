package com.proyecto.sicecuador.modelos.recaudacion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transferencia")
@Data
@AllArgsConstructor
public class Transferencia extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "tipo_transaccion", nullable = true)
    private String tipoTransaccion;
    @Column(name = "numero_transaccion", nullable = true)
    private String numeroTransaccion;
    @Column(name = "fecha_transaccion", nullable = true)
    private Date fechaTransaccion;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "recaudacion_id", nullable = true)
    private Recaudacion recaudacion;

    public Transferencia(long id){
        super(id);
    }
    public Transferencia(){
        super();
        this.codigo = Constantes.vacio;
        this.tipoTransaccion = Constantes.vacio;
        this.numeroTransaccion = Constantes.vacio;
        this.fechaTransaccion = new Date();
        this.valor = Constantes.cero;
        this.banco = new Banco();
    }
    public void normalizar(){
        if(this.fechaTransaccion == null) this.fechaTransaccion = new Date();
        if(this.banco == null) this.banco = new Banco();
    }
}
