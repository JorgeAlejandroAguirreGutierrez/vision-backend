package com.proyecto.vision.modelos.compra;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.Entidad;
import com.proyecto.vision.modelos.inventario.TipoGasto;
import com.proyecto.vision.modelos.acceso.Empresa;
import com.proyecto.vision.modelos.acceso.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.proyecto.vision.Constantes.tabla_gasto_personal;

@Entity
@Table(name = tabla_gasto_personal)
@Getter
@Setter
@AllArgsConstructor
public class GastoPersonal extends Entidad {
    @Column(name = "codigo", nullable = true)
    private String codigo;
    @Column(name = "establecimiento", nullable = true)
    private String establecimiento;
    @Column(name = "punto_venta", nullable = true)
    private String puntoVenta;
    @Column(name = "secuencial", nullable = true)
    private String secuencial;
    @Column(name = "numero_comprobante", nullable = true)
    private String numeroComprobante;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "descuento", nullable = true)
    private double descuento;
    @Column(name = "subtotal", nullable = true)
    private double subtotal;
    @Column(name = "subtotal_gravado", nullable = true)
    private double subtotalGravado;
    @Column(name = "subtotal_no_gravado", nullable = true)
    private double subtotalNoGravado;
    @Column(name = "importe_iva_total", nullable = true)
    private double importeIvaTotal;
    @Column(name = "total", nullable = true)
    private double total;
    @Column(name = "comentario", nullable = true)
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "tipo_gasto_id", nullable = true)
    private TipoGasto tipoGasto;
    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = true)
    private Proveedor proveedor;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = true)
    private Empresa empresa;
    @JsonManagedReference
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinColumn(name = "gasto_personal_id", nullable = true)
    private List<GastoPersonalLinea> gastoPersonalLineas;

    public GastoPersonal(long id){
        super(id);
    }
    public GastoPersonal(){
        super();
        this.codigo = Constantes.vacio;
        this.establecimiento = Constantes.vacio;
        this.puntoVenta = Constantes.vacio;
        this.secuencial = Constantes.vacio;
        this.numeroComprobante = Constantes.vacio;
        this.fecha = new Date();
        this.estado = Constantes.vacio;
        this.subtotal = Constantes.cero;
        this.descuento = Constantes.cero;
        this.subtotalGravado = Constantes.cero;
        this.subtotalNoGravado = Constantes.cero;
        this.importeIvaTotal = Constantes.cero;
        this.total = Constantes.cero;
        this.comentario = Constantes.vacio;
        this.gastoPersonalLineas = Collections.emptyList();
    }

    public void normalizar(){
        if(this.fecha == null) this.fecha = new Date();
        if(this.proveedor == null) this.proveedor = new Proveedor();
        if(this.usuario == null) this.usuario = new Usuario();
        if(this.gastoPersonalLineas.isEmpty()) this.gastoPersonalLineas = Collections.emptyList();
        for(GastoPersonalLinea gastoPersonalLinea: this.gastoPersonalLineas){
            gastoPersonalLinea.normalizar();
        }
    }
}