package com.proyecto.sicecuador.modelos.comprobante;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.otros.comprobante.ProformaUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "proforma")
@EntityListeners({ProformaUtil.class})
public class Proforma extends Entidad {
    @Column(name = "numero_interno", nullable = true)
    private String numero_interno;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "fecha_caducidad", nullable = true)
    private Date fecha_caducidad;
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
    @Column(name = "comentario", nullable = true)
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = true)
    private Usuario vendedor;

    public Proforma(){

    }

    public Proforma(long id){
        super(id);
    }

    public Proforma(String codigo, String numero_interno, Date fecha, Date fecha_caducidad, String estado, double subtotal, double subdescuento,
                  double base_iva, double base_0, double importe_iva, double total, double descuento_porcentaje,
                  double descuento, String comentario, Cliente cliente, Usuario vendedor){
        super(codigo);
        this.numero_interno=numero_interno;
        this.fecha=fecha;
        this.fecha_caducidad=fecha_caducidad;
        this.estado=estado;
        this.subtotal=subtotal;
        this.subdescuento=subdescuento;
        this.base_iva=base_iva;
        this.base_0=base_0;
        this.importe_iva=importe_iva;
        this.total=total;
        this.descuento_porcentaje=descuento_porcentaje;
        this.descuento=descuento;
        this.comentario=comentario;
        this.cliente=cliente;
        this.vendedor=vendedor;
    }
    public String getNumero_interno() {
        return numero_interno;
    }

    public Date getFecha() {
        return fecha;
    }

    public Date getFecha_caducidad() {
        return fecha_caducidad;
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

    public String getComentario() {
        return comentario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Usuario getVendedor() {
        return vendedor;
    }
}
