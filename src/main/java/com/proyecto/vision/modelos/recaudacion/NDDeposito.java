package com.proyecto.vision.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.cajaBanco.Banco;
import com.proyecto.vision.modelos.cajaBanco.CuentaPropia;
import com.proyecto.vision.modelos.venta.NotaDebito;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static com.proyecto.vision.Constantes.tabla_nd_deposito;

@Entity
@Table(name = tabla_nd_deposito)
@Getter
@Setter
@AllArgsConstructor
public class NDDeposito extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "comprobante", nullable = true)
    private String comprobante;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "banco_id", nullable = true)
    private Banco banco;
    @ManyToOne
    @JoinColumn(name = "cuenta_propia_id", nullable = true)
    private CuentaPropia cuentaPropia;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nota_debito_id", nullable = true)
    private NotaDebito notaDebito;

    public NDDeposito(long id){
        super(id);
    }
    public NDDeposito(){
        super();
        this.codigo = Constantes.vacio;
        this.fecha = new Date();
        this.comprobante = Constantes.vacio;
        this.valor = Constantes.cero;
    }

    public void normalizar(){
        if(this.banco == null) this.banco = new Banco();
        if(this.cuentaPropia == null) this.cuentaPropia = new CuentaPropia();
    }
}
