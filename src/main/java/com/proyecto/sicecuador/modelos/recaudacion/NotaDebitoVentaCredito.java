package com.proyecto.sicecuador.modelos.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.proyecto.sicecuador.Constantes.tabla_nota_debito_venta_credito;

@Entity
@Table(name = tabla_nota_debito_venta_credito)
@Data
@AllArgsConstructor
public class NotaDebitoVentaCredito extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "saldo", nullable = true)
    private double saldo;
    @Column(name = "unidad_tiempo", nullable = true)
    private String unidadTiempo;
    @Column(name = "plazo", nullable = true)
    private long plazo;

    public NotaDebitoVentaCredito(long id){
        super(id);
    }
    public NotaDebitoVentaCredito(){
        super();
        this.codigo = Constantes.vacio;
        this.saldo = Constantes.cero;
        this.unidadTiempo = Constantes.vacio;
        this.plazo = Constantes.ceroId;
    }
}
