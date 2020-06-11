package com.proyecto.sicecuador.modelos.usuario;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.otros.cliente.TipoContribuyenteUtil;
import com.proyecto.sicecuador.otros.usuario.PuntoVentaUtil;

import javax.persistence.*;

@Entity
@Table(name = "punto_venta")
//@EntityListeners({PuntoVentaUtil.class})
public class PuntoVenta extends Entidad {
    @Column(name = "descripcion", nullable = true)
    private String descripcion;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "establecimiento_id", nullable = true)
    private Establecimiento establecimiento;

    public PuntoVenta(){
        super();
    }

    public PuntoVenta(long id) {
        super(id);
    }

    public PuntoVenta(String codigo, String descripcion, Establecimiento establecimiento){
        super(codigo);
        this.descripcion=descripcion;
        this.establecimiento=establecimiento;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }
}
