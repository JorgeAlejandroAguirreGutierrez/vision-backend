package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cajaBanco.CuentaPropia;
import com.proyecto.sicecuador.modelos.venta.Factura;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static com.proyecto.sicecuador.Constantes.tabla_deposito;

@Entity
@Table(name = tabla_deposito)
@Getter
@Setter
@AllArgsConstructor
public class Deposito extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "comprobante", nullable = true)
    private String comprobante;
    @Column(name = "valor", nullable = true)
    private double valor;
    @ManyToOne
    @JoinColumn(name = "cuenta_propia_id", nullable = true)
    private CuentaPropia cuentaPropia;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;

    public Deposito(long id){
        super(id);
    }
    public Deposito(){
        super();
        this.codigo = Constantes.vacio;
        this.fecha = new Date();
        this.comprobante = Constantes.vacio;
        this.valor = Constantes.cero;
    }

    public void normalizar(){
        if(this.cuentaPropia == null) this.cuentaPropia = new CuentaPropia();
    }
}
