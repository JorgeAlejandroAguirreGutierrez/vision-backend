package com.proyecto.sicecuador.modelos.comprobante;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.otros.cliente.TipoPagoUtil;
import com.proyecto.sicecuador.otros.comprobante.EgresoUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "egreso")
@EntityListeners({EgresoUtil.class})
public class Egreso extends Entidad {
    @Column(name = "numero_interno", nullable = true)
    private String numero_interno;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "fecha_entrega", nullable = true)
    private Date fecha_entrega;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "subtotal", nullable = true)
    private double subtotal;
    @Column(name = "subdescuento", nullable = true)
    private double subdescuento;
    @Column(name = "base_iva", nullable = true)
    private double base_iva;
    @Column(name = "base_0", nullable = true)
    private double base_0;
    @Column(name = "importe_iva", nullable = true)
    private double importe_iva;
    @Column(name = "total", nullable = true)
    private double total;
    @Column(name = "descuento_porcentaje", nullable = true)
    private double descuento_porcentaje;
    @Column(name = "descuento", nullable = true)
    private double descuento;
    @Column(name = "abono", nullable = true)
    private double abono;
    @Column(name = "comentario", nullable = true)
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "sesion_id", nullable = true)
    private Sesion sesion;
    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = true)
    private Usuario vendedor;

    public Egreso(){

    }

    public Egreso(long id){
        super(id);
    }

    public Egreso(String codigo, String numero_interno, Date fecha, Date fecha_entrega, String estado,
                  double subtotal, double subdescuento, double base_iva, double base_0, double importe_iva, double total,
                  double descuento_porcentaje, double descuento, double abono, String comentario, Cliente cliente,
                  Sesion sesion, Usuario vendedor){
        super(codigo);
        this.numero_interno=numero_interno;
        this.fecha=fecha;
        this.fecha_entrega=fecha_entrega;
        this.estado=estado;
        this.subtotal=subtotal;
        this.subdescuento=subdescuento;
        this.base_iva=base_iva;
        this.base_0=base_0;
        this.importe_iva=importe_iva;
        this.total=total;
        this.descuento_porcentaje=descuento_porcentaje;
        this.descuento=descuento;
        this.abono=abono;
        this.comentario= comentario;
        this.cliente=cliente;
        this.sesion=sesion;
        this.vendedor=vendedor;
    }
    public String getNumero_interno() {
        return numero_interno;
    }

    public Date getFecha() {
        return fecha;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public String getEstado() {
        return estado;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getSubdescuento() {
        return subdescuento;
    }

    public double getBase_iva() {
        return base_iva;
    }
    public double getBase_0() {
        return base_0;
    }

    public double getImporte_iva() {
        return importe_iva;
    }

    public double getTotal() {
        return total;
    }

    public double getDescuento_porcentaje() {
        return descuento_porcentaje;
    }

    public double getDescuento() {
        return descuento;
    }

    public double getAbono() {
        return abono;
    }

    public String getComentario() {
        return comentario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Sesion getSesion() {
        return sesion;
    }

    public Usuario getVendedor() {
        return vendedor;
    }
}
