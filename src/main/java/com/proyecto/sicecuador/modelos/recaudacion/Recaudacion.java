package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.usuario.Sesion;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "recaudacion")
public class Recaudacion extends Entidad {
	@JsonProperty("fecha")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha", nullable = true)
    private Date fecha;
	@JsonProperty("total")
    @Column(name = "total", nullable = true)
    private double total;
	@JsonProperty("comentario")
    @Column(name = "comentario", nullable = true)
    private String comentario;
	@JsonProperty("efectivo")
    @Column(name = "efectivo", nullable = true)
    private double efectivo;
	@JsonProperty("total_cheques")
    @Column(name = "total_cheques", nullable = true)
    private double totalCheques;
	@JsonProperty("total_depositos")
    @Column(name = "total_depositos", nullable = true)
    private double totalDepositos;
	@JsonProperty("total_transferencias")
    @Column(name = "total_transferencias", nullable = true)
    private double totalTransferencias;
	@JsonProperty("total_tarjetas_debitos")
    @Column(name = "total_tarjetas_debitos", nullable = true)
    private double totalTarjetasDebitos;
	@JsonProperty("total_tarjetas_creditos")
    @Column(name = "total_tarjetas_creditos", nullable = true)
    private double totalTarjetasCreditos;
	@JsonProperty("total_compensaciones")
    @Column(name = "total_compensaciones", nullable = true)
    private double totalCompensaciones;
	@JsonProperty("total_retenciones_ventas")
    @Column(name = "total_retenciones_ventas", nullable = true)
    private double totalRetencionesVentas;
	@JsonProperty("total_credito")
    @Column(name = "total_credito", nullable = true)
    private double totalCredito;
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonProperty("credito")
    @JoinColumn(name = "credito_id", nullable = true)
    private Credito credito;
    @NotNull
    @ManyToOne
    @JsonProperty("factura")
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
    @NotNull
    @ManyToOne
    @JsonProperty("sesion")
    @JoinColumn(name = "sesion_id", nullable = true)
    private Sesion sesion;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonProperty("cheques")
    @JoinColumn(name = "cheque_id")
    private List<Cheque> cheques;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonProperty("depositos")
    @JoinColumn(name = "deposito_id")
    private List<Deposito> depositos;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonProperty("transferencias")
    @JoinColumn(name = "transferencia_id")
    private List<Transferencia> transferencias;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonProperty("tarjetas_creditos")
    @JoinColumn(name = "tarjeta_credito_id")
    private List<TarjetaCredito> tarjetasCreditos;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonProperty("tarjetas_debitos")
    @JoinColumn(name = "tarjeta_debito_id")
    private List<TarjetaDebito> tarjetasDebitos;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonProperty("compensaciones")
    @JoinColumn(name = "compensacion_id")
    private List<Compensacion> compensaciones;
    @OneToMany(cascade =CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonProperty("retenciones_ventas")
    @JoinColumn(name = "retencion_venta_id")
    private List<RetencionVenta> retencionesVentas;

    public Recaudacion(){
    }

    public Recaudacion(long id){
        super(id);
    }

    public Recaudacion(String codigo, Date fecha, double total, String comentario, double efectivo,
                       double totalCheques, double totalDepositos, double totalTransferencias,
                       double totalTarjetasDebitos, double totalTarjetasCreditos, double totalCredito,
                       double totalCompensaciones, double totalRetencionesVentas, List<Cheque> cheques,
                       List<Deposito>depositos, List<Transferencia> transferencias, List<Compensacion> compensaciones, List<RetencionVenta> retencionesVentas,
                       List<TarjetaCredito> tarjetasCreditos, List<TarjetaDebito> tarjetasDebitos, Credito credito,
                       Factura factura, Sesion sesion){
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

        this.cheques=cheques;
        this.depositos=depositos;
        this.transferencias=transferencias;
        this.tarjetasCreditos=tarjetasCreditos;
        this.tarjetasDebitos=tarjetasDebitos;
        this.compensaciones=compensaciones;
        this.retencionesVentas=retencionesVentas;
        this.credito=credito;
        this.factura=factura;
        this.sesion=sesion;
    }
    public Date getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
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

    public Sesion getSesion() {
        return sesion;
    }

    public void normalizar(){
        if (this.credito.getSaldo()==0){
            this.credito=null;
        }
    }

    public void setCredito(Credito credito) {
        this.credito = credito;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
