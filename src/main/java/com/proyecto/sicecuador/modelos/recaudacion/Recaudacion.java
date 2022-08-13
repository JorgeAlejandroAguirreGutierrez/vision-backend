package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.cliente.FormaPago;
import com.proyecto.sicecuador.modelos.usuario.Sesion;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recaudacion")
public class Recaudacion extends Entidad {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha", nullable = true)
    private Date fecha;
    @Column(name = "total", nullable = true)
    private double total;
    @Column(name = "estado", nullable = true)
    private String estado;
    @Column(name = "comentario", nullable = true)
    private String comentario;
    @Column(name = "efectivo", nullable = true)
    private double efectivo;
    @Column(name = "total_cheques", nullable = true)
    private double totalCheques;
    @Column(name = "total_depositos", nullable = true)
    private double totalDepositos;
    @Column(name = "total_transferencias", nullable = true)
    private double totalTransferencias;
    @Column(name = "total_tarjetas_debitos", nullable = true)
    private double totalTarjetasDebitos;
    @Column(name = "total_tarjetas_creditos", nullable = true)
    private double totalTarjetasCreditos;
    @Column(name = "total_compensaciones", nullable = true)
    private double totalCompensaciones;
    @Column(name = "total_retenciones_ventas", nullable = true)
    private double totalRetencionesVentas;
    @Column(name = "total_credito", nullable = true)
    private double totalCredito;
    @Column(name = "plazo", nullable = true)
    private double plazo;
    @Column(name = "unidad_tiempo", nullable = true)
    private String unidadTiempo;
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "credito_id", nullable = true)
    private Credito credito;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "sesion_id", nullable = true)
    private Sesion sesion;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "cheque_id")
    private List<Cheque> cheques;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "deposito_id")
    private List<Deposito> depositos;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "transferencia_id")
    private List<Transferencia> transferencias;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjeta_credito_id")
    private List<TarjetaCredito> tarjetasCreditos;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "tarjeta_debito_id")
    private List<TarjetaDebito> tarjetasDebitos;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "compensacion_id")
    private List<Compensacion> compensaciones;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "retencion_venta_id")
    private List<RetencionVenta> retencionesVentas;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "forma_pago_id", nullable = true)
    private FormaPago formaPago;
    
    public Recaudacion(){
    }

    public Recaudacion(long id){
        super(id);
    }

    public Recaudacion(String codigo, Date fecha, double total, String comentario, double efectivo,
                       double totalCheques, double totalDepositos, double totalTransferencias,
                       double totalTarjetasDebitos, double totalTarjetasCreditos, double totalCredito,
                       double totalCompensaciones, double totalRetencionesVentas, double plazo, String unidadTiempo, List<Cheque> cheques,
                       List<Deposito>depositos, List<Transferencia> transferencias, List<Compensacion> compensaciones, List<RetencionVenta> retencionesVentas,
                       List<TarjetaCredito> tarjetasCreditos, List<TarjetaDebito> tarjetasDebitos, Credito credito,
                       Factura factura,FormaPago formaPago, Sesion sesion){
        super(codigo);
        this.fecha=fecha;
        this.total=total;
        this.comentario=comentario;
        this.efectivo=efectivo;
        this.totalCheques=totalCheques;
        this.totalDepositos=totalDepositos;
        this.totalTransferencias=totalTransferencias;
        this.totalTarjetasDebitos=totalTarjetasDebitos;
        this.totalTarjetasCreditos=totalTarjetasCreditos;
        this.totalCompensaciones=totalCompensaciones;
        this.totalRetencionesVentas=totalRetencionesVentas;
        this.totalCredito=totalCredito;
        this.plazo=plazo;
        this.unidadTiempo=unidadTiempo;

        this.cheques=cheques;
        this.depositos=depositos;
        this.transferencias=transferencias;
        this.tarjetasCreditos=tarjetasCreditos;
        this.tarjetasDebitos=tarjetasDebitos;
        this.compensaciones=compensaciones;
        this.retencionesVentas=retencionesVentas;
        this.credito=credito;
        this.factura=factura;
        this.formaPago=formaPago;
        this.sesion=sesion;
    }
    public Date getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }
    
    public String getEstado() {
		return estado;
	}
    
    public String getComentario() {
        return comentario;
    }

    public double getEfectivo() {
        return efectivo;
    }

    public double getTotalCheques() {
		return totalCheques;
	}

    public double getTotalDepositos() {
		return totalDepositos;
	}

    public double getTotalTransferencias() {
		return totalTransferencias;
	}

    public double getTotalTarjetasCreditos() {
    	return totalTarjetasCreditos;
    }

    public double getTotalTarjetasDebitos() {
		return totalTarjetasDebitos;
	}

    public double getTotalCompensaciones() {
		return totalCompensaciones;
	}

    public double getTotalRetencionesVentas() {
		return totalRetencionesVentas;
	}

    public double getTotalCredito() {
		return totalCredito;
	}
    public double getPlazo() {
		return plazo;
	}
    public String getUnidadTiempo() {
		return unidadTiempo;
	}
    
    @JsonManagedReference
    public List<Cheque> getCheques() {
        return cheques;
    }
    @JsonManagedReference
    public List<Deposito> getDepositos() {
        return depositos;
    }
    @JsonManagedReference
    public List<Transferencia> getTransferencias() {
        return transferencias;
    }
    @JsonManagedReference
    public List<TarjetaDebito> getTarjetasDebitos() {
		return tarjetasDebitos;
	}
    @JsonManagedReference
    public List<TarjetaCredito> getTarjetasCreditos() {
		return tarjetasCreditos;
	}
    @JsonManagedReference
    public List<Compensacion> getCompensaciones() {
        return compensaciones;
    }
    @JsonManagedReference
    public List<RetencionVenta> getRetencionesVentas() {
		return retencionesVentas;
	}

    public Credito getCredito() {
        return credito;
    }

    public Factura getFactura() {
        return factura;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }
    public Sesion getSesion() {
        return sesion;
    }

    public void normalizar(){
        if (this.credito.getSaldo()==0){
            this.credito=null;
        }
    }
    
    public void setEstado(String estado) {
		this.estado = estado;
	}

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public void setPlazo(double plazo) {
        this.plazo = plazo;
    }

    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }

    
    public void setTotalCheques(double totalCheques) {
		this.totalCheques = totalCheques;
	}
    
    public void setTotalDepositos(double totalDepositos) {
		this.totalDepositos = totalDepositos;
	}
    
    public void setTotalTransferencias(double totalTransferencias) {
		this.totalTransferencias = totalTransferencias;
	}
    
    public void setTotalCredito(double totalCredito) {
		this.totalCredito = totalCredito;
	}
    
    public void setTotalTarjetasDebitos(double totalTarjetasDebitos) {
		this.totalTarjetasDebitos = totalTarjetasDebitos;
	}
    
    public void setTotalTarjetasCreditos(double totalTarjetasCreditos) {
		this.totalTarjetasCreditos = totalTarjetasCreditos;
	}
    
    public void setTotalCompensaciones(double totalCompensaciones) {
		this.totalCompensaciones = totalCompensaciones;
	}
    
    public void setTotalRetencionesVentas(double totalRetencionesVentas) {
		this.totalRetencionesVentas = totalRetencionesVentas;
	}
    
    
}
