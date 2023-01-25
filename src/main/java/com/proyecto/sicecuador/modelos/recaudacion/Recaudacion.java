package com.proyecto.sicecuador.modelos.recaudacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.comprobante.Factura;
import com.proyecto.sicecuador.modelos.usuario.Sesion;

import javax.persistence.*;
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
    @Column(name = "comentario", nullable = true)
    private String comentario;
    @Column(name = "efectivo", nullable = true)
    private double efectivo;
    @Column(name = "cambio", nullable = true)
    private double cambio;
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
    @Column(name = "total_credito", nullable = true)
    private double totalCredito;
    @Column(name = "efectivo_codigo_sri", nullable = true)
    private String efectivoCodigoSri;
    @Column(name = "cheque_codigo_sri", nullable = true)
    private String chequeCodigoSri;
    @Column(name = "deposito_codigo_sri", nullable = true)
    private String depositoCodigoSri;
    @Column(name = "transferencia_codigo_sri", nullable = true)
    private String transferenciaCodigoSri;
    @Column(name = "tarjeta_credito_codigo_sri", nullable = true)
    private String tarjetaCreditoCodigoSri;
    @Column(name = "tarjeta_debito_codigo_sri", nullable = true)
    private String tarjetaDebitoCodigoSri;
    @Column(name = "credito_codigo_sri", nullable = true)
    private String creditoCodigoSri;
    @Column(name = "estado", nullable = true)
    private String estado;
    @ManyToOne
    @JoinColumn(name = "factura_id", nullable = true)
    private Factura factura;
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
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "credito_id", nullable = true)
    private Credito credito;
    
    public Recaudacion(){
    }

    public Recaudacion(long id){
        super(id);
    }

    public Recaudacion(String codigo, Date fecha, double total, String comentario, double efectivo, double cambio,
                       double totalCheques, double totalDepositos, double totalTransferencias,
                       double totalTarjetasDebitos, double totalTarjetasCreditos, double totalCredito, String estado,
                       String efectivoCodigoSri, String chequeCodigoSri, String depositoCodigoSri, String transferenciaCodigoSri,
                       String tarjetaCreditoCodigoSri, String tarjetaDebitoCodigoSri, String creditoCodigoSri, Factura factura, Sesion sesion,
                       List<Cheque> cheques, List<Deposito>depositos, List<Transferencia> transferencias,
                       List<TarjetaCredito> tarjetasCreditos, List<TarjetaDebito> tarjetasDebitos, Credito credito){
        super(codigo);
        this.fecha=fecha;
        this.total=total;
        this.comentario=comentario;
        this.efectivo=efectivo;
        this.cambio=cambio;
        this.totalCheques=totalCheques;
        this.totalDepositos=totalDepositos;
        this.totalTransferencias=totalTransferencias;
        this.totalTarjetasDebitos=totalTarjetasDebitos;
        this.totalTarjetasCreditos=totalTarjetasCreditos;
        this.totalCredito=totalCredito;
        this.estado=estado;
        
        this.efectivoCodigoSri = efectivoCodigoSri;
        this.chequeCodigoSri = chequeCodigoSri;
        this.depositoCodigoSri = depositoCodigoSri;
        this.transferenciaCodigoSri = transferenciaCodigoSri;
        this.tarjetaCreditoCodigoSri = tarjetaCreditoCodigoSri;
        this.tarjetaDebitoCodigoSri = tarjetaDebitoCodigoSri;
        this.creditoCodigoSri = creditoCodigoSri;

        this.factura=factura;
        this.sesion=sesion;

        this.cheques=cheques;
        this.depositos=depositos;
        this.transferencias=transferencias;
        this.tarjetasCreditos=tarjetasCreditos;
        this.tarjetasDebitos=tarjetasDebitos;
        this.credito = credito;
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
    
    public double getCambio() {
		return cambio;
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

    public double getTotalCredito() {
		return totalCredito;
	}
    
    public String getEstado() {
		return estado;
	}
    
    public String getEfectivoCodigoSri() {
		return efectivoCodigoSri;
	}
    
    public String getChequeCodigoSri() {
		return chequeCodigoSri;
	}
    
    public String getDepositoCodigoSri() {
		return depositoCodigoSri;
	}
    
    public String getTransferenciaCodigoSri() {
		return transferenciaCodigoSri;
	}
    
    public String getTarjetaCreditoCodigoSri() {
		return tarjetaCreditoCodigoSri;
	}
    
    public String getTarjetaDebitoCodigoSri() {
		return tarjetaDebitoCodigoSri;
	}
    
    public String getCreditoCodigoSri() {
		return creditoCodigoSri;
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

    public Credito getCredito() {
        return credito;
    }

    public Factura getFactura() {
        return factura;
    }
    
    public Sesion getSesion() {
        return sesion;
    }
    
    public void setEfectivo(double efectivo) {
		this.efectivo = efectivo;
	}
    
    public void setCambio(double cambio) {
		this.cambio = cambio;
	}

    public void setTotal(double total) {
        this.total = total;
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
    
    public void setEstado(String estado) {
		this.estado = estado;
	}
    
    public void setTotalTarjetasDebitos(double totalTarjetasDebitos) {
		this.totalTarjetasDebitos = totalTarjetasDebitos;
	}
    
    public void setTotalTarjetasCreditos(double totalTarjetasCreditos) {
		this.totalTarjetasCreditos = totalTarjetasCreditos;
	}
    
    public void setEfectivoCodigoSri(String efectivoCodigoSri) {
		this.efectivoCodigoSri = efectivoCodigoSri;
	}
    
    public void setChequeCodigoSri(String chequeCodigoSri) {
		this.chequeCodigoSri = chequeCodigoSri;
	}
    
    public void setDepositoCodigoSri(String depositoCodigoSri) {
		this.depositoCodigoSri = depositoCodigoSri;
	}
    
    public void setTransferenciaCodigoSri(String transferenciaCodigoSri) {
		this.transferenciaCodigoSri = transferenciaCodigoSri;
	}
    
    public void setTarjetaCreditoCodigoSri(String tarjetaCreditoCodigoSri) {
		this.tarjetaCreditoCodigoSri = tarjetaCreditoCodigoSri;
	}
    
    public void setTarjetaDebitoCodigoSri(String tarjetaDebitoCodigoSri) {
		this.tarjetaDebitoCodigoSri = tarjetaDebitoCodigoSri;
	}
    
    public void setCreditoCodigoSri(String creditoCodigoSri) {
		this.creditoCodigoSri = creditoCodigoSri;
	}

    public void setCredito(Credito credito) {
        this.credito = credito;
    }
}
