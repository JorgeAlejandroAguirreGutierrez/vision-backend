package com.proyecto.vision.modelos.recaudacion;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.cajaBanco.CuentaPropia;
import com.proyecto.vision.modelos.venta.Factura;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

import static com.proyecto.vision.Constantes.tabla_transferencia;

@Entity
@Table(name = tabla_transferencia)
@Getter
@Setter
@AllArgsConstructor
public class Transferencia extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "tipo", nullable = true)
    private String tipo;
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

    public Transferencia(long id){
        super(id);
    }
    public Transferencia(){
        super();
        this.codigo = Constantes.vacio;
        this.fecha = new Date();
        this.tipo = Constantes.vacio;
        this.comprobante = Constantes.vacio;
        this.valor = Constantes.cero;
    }
    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.cuentaPropia == null) this.cuentaPropia = new CuentaPropia();
    }
}
