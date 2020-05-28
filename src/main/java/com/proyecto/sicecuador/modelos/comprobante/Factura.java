package com.proyecto.sicecuador.modelos.comprobante;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.usuario.Sesion;
import com.proyecto.sicecuador.modelos.usuario.Usuario;
import com.proyecto.sicecuador.otros.comprobante.FacturaUtil;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "factura")
@EntityListeners({FacturaUtil.class})
public class Factura extends Entidad {
    @Column(name = "numero", nullable = true)
    private String numero;
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "estado", nullable = true)
    private boolean estado;
    @Column(name = "subtotal_sin_descuento", nullable = true)
    private double subtotal_sin_descuento;
    @Column(name = "subtotal_con_descuento", nullable = true)
    private double subtotal_con_descuento;
    @Column(name = "descuento_total", nullable = true)
    private double descuento_total;
    @Column(name = "subtotal_base12_sin_descuento", nullable = true)
    private double subtotal_base12_sin_descuento;
    @Column(name = "subtotal_base0_sin_descuento", nullable = true)
    private double subtotal_base0_sin_descuento;
    @Column(name = "subtotal_base12_con_descuento", nullable = true)
    private double subtotal_base12_con_descuento;
    @Column(name = "subtotal_base0_con_descuento", nullable = true)
    private double subtotal_base0_con_descuento;
    @Column(name = "importe_iva_sin_descuento", nullable = true)
    private double importe_iva_sin_descuento;
    @Column(name = "importe_iva_con_descuento", nullable = true)
    private double importe_iva_con_descuento;
    @Column(name = "total_sin_descuento", nullable = true)
    private double total_sin_descuento;
    @Column(name = "total_con_descuento", nullable = true)
    private double total_con_descuento;

    //GENERAL
    @Column(name = "valor_descuento_subtotal", nullable = true)
    private double valor_descuento_subtotal;
    @Column(name = "porcentaje_descuento_subtotal", nullable = true)
    private double porcentaje_descuento_subtotal;
    @Column(name = "valor_descuento_total", nullable = true)
    private double valor_descuento_total;
    @Column(name = "porcentaje_descuento_total", nullable = true)
    private double porcentaje_descuento_total;
    @Column(name = "valor_porcentaje_descuento_total", nullable = true)
    private double valor_porcentaje_descuento_total;

    @Column(name = "comentario", nullable = true)
    private String comentario;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cliente_factura_id", nullable = true)
    private Cliente cliente_factura;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "auxiliar_id", nullable = true)
    private Auxiliar auxiliar;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "vendedor_id", nullable = true)
    private Usuario vendedor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sesion_id", nullable = true)
    private Sesion sesion;

    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id")
    private List<FacturaDetalle> factura_detalles;

    public Factura(){

    }

    public Factura(long id){
        super(id);
    }
    public Factura(String numero){
        super(0);
        this.numero=numero;
    }

    public Factura(String codigo, String numero, Date fecha, boolean estado, double subtotal,
                   double valor_descuento_subtotal, double porcentaje_descuento_subtotal, double valor_porcentaje_descuento_subtotal,
                   double valor_descuento_total, double porcentaje_descuento_total, double valor_porcentaje_descuento_total,
                   double descuento, double base_12, double base_0, double importe_iva, double total, String comentario,
                   Cliente cliente, Cliente cliente_factura, Auxiliar auxiliar, Usuario vendedor, Sesion sesion) {
        super(codigo);
        this.numero=numero;
        this.fecha=fecha;
        this.estado=estado;
        this.comentario=comentario;
        this.cliente=cliente;
        this.cliente_factura=cliente_factura;
        this.auxiliar=auxiliar;
        this.vendedor=vendedor;
        this.sesion=sesion;
    }

    public String getNumero() {
        return numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public boolean isEstado() {
        return estado;
    }

    public double getSubtotal_sin_descuento() {
        return subtotal_sin_descuento;
    }

    public double getSubtotal_con_descuento() {
        return subtotal_con_descuento;
    }

    public double getDescuento_total() {
        return descuento_total;
    }

    public double getSubtotal_base12_sin_descuento() {
        return subtotal_base12_sin_descuento;
    }

    public double getSubtotal_base0_sin_descuento() {
        return subtotal_base0_sin_descuento;
    }

    public double getSubtotal_base12_con_descuento() {
        return subtotal_base12_con_descuento;
    }

    public double getSubtotal_base0_con_descuento() {
        return subtotal_base0_con_descuento;
    }

    public double getImporte_iva_sin_descuento() {
        return importe_iva_sin_descuento;
    }

    public double getImporte_iva_con_descuento() {
        return importe_iva_con_descuento;
    }

    public double getTotal_sin_descuento() {
        return total_sin_descuento;
    }

    public double getTotal_con_descuento() {
        return total_con_descuento;
    }

    public double getValor_descuento_subtotal() {
        return valor_descuento_subtotal;
    }

    public double getPorcentaje_descuento_subtotal() {
        return porcentaje_descuento_subtotal;
    }

    public double getValor_descuento_total() {
        return valor_descuento_total;
    }

    public double getPorcentaje_descuento_total() {
        return porcentaje_descuento_total;
    }

    public double getValor_porcentaje_descuento_total() {
        return valor_porcentaje_descuento_total;
    }

    public void setSubtotal_sin_descuento(double subtotal_sin_descuento) {
        this.subtotal_sin_descuento = subtotal_sin_descuento;
    }

    public void setSubtotal_con_descuento(double subtotal_con_descuento) {
        this.subtotal_con_descuento = subtotal_con_descuento;
    }

    public void setDescuento_total(double descuento_total) {
        this.descuento_total = descuento_total;
    }

    public void setSubtotal_base12_sin_descuento(double subtotal_base12_sin_descuento) {
        this.subtotal_base12_sin_descuento = subtotal_base12_sin_descuento;
    }

    public void setSubtotal_base0_sin_descuento(double subtotal_base0_sin_descuento) {
        this.subtotal_base0_sin_descuento = subtotal_base0_sin_descuento;
    }

    public void setSubtotal_base12_con_descuento(double subtotal_base12_con_descuento) {
        this.subtotal_base12_con_descuento = subtotal_base12_con_descuento;
    }

    public void setSubtotal_base0_con_descuento(double subtotal_base0_con_descuento) {
        this.subtotal_base0_con_descuento = subtotal_base0_con_descuento;
    }

    public void setImporte_iva_sin_descuento(double importe_iva_sin_descuento) {
        this.importe_iva_sin_descuento = importe_iva_sin_descuento;
    }

    public void setImporte_iva_con_descuento(double importe_iva_con_descuento) {
        this.importe_iva_con_descuento = importe_iva_con_descuento;
    }

    public void setTotal_sin_descuento(double total_sin_descuento) {
        this.total_sin_descuento = total_sin_descuento;
    }

    public void setTotal_con_descuento(double total_con_descuento) {
        this.total_con_descuento = total_con_descuento;
    }

    public void setValor_descuento_subtotal(double valor_descuento_subtotal) {
        this.valor_descuento_subtotal = valor_descuento_subtotal;
    }

    public void setPorcentaje_descuento_subtotal(double porcentaje_descuento_subtotal) {
        this.porcentaje_descuento_subtotal = porcentaje_descuento_subtotal;
    }

    public void setValor_descuento_total(double valor_descuento_total) {
        this.valor_descuento_total = valor_descuento_total;
    }

    public void setPorcentaje_descuento_total(double porcentaje_descuento_total) {
        this.porcentaje_descuento_total = porcentaje_descuento_total;
    }

    public void setValor_porcentaje_descuento_total(double valor_porcentaje_descuento_total) {
        this.valor_porcentaje_descuento_total = valor_porcentaje_descuento_total;
    }

    public String getComentario() {
        return comentario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Cliente getCliente_factura() {
        return cliente_factura;
    }

    public Auxiliar getAuxiliar() {
        return auxiliar;
    }

    public Sesion getSesion() {
        return sesion;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    @JsonManagedReference(value="factura-factura-detalle")
    public List<FacturaDetalle> getFactura_detalles() {
        return factura_detalles;
    }

    public void normalizar(){
    }
}
