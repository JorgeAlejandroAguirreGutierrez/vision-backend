package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.recaudacion.Cheque;
import com.proyecto.sicecuador.modelos.recaudacion.Compensacion;
import com.proyecto.sicecuador.modelos.recaudacion.Deposito;
import com.proyecto.sicecuador.modelos.recaudacion.RangoCrediticio;
import com.proyecto.sicecuador.modelos.recaudacion.Recaudacion;
import com.proyecto.sicecuador.modelos.recaudacion.RetencionVenta;
import com.proyecto.sicecuador.modelos.recaudacion.TarjetaCredito;
import com.proyecto.sicecuador.modelos.recaudacion.TarjetaDebito;
import com.proyecto.sicecuador.modelos.recaudacion.Transferencia;
import com.proyecto.sicecuador.repositorios.recaudacion.IRecaudacionRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICreditoService;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IRangoCrediticioService;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IRecaudacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class RecaudacionService implements IRecaudacionService {
    @Autowired
    private IRecaudacionRepository rep;
    
    @Autowired
    private IRangoCrediticioService servicioRangoCrediticio;
    
    @Autowired
    private ICreditoService servicioCredito;
    
    @Override
    public Recaudacion crear(Recaudacion recaudacion) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_recaudacion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	recaudacion.setCodigo(codigo.get());
    	double diferencia= recaudacion.getFactura().getTotalConDescuento()-recaudacion.getTotal();
        if (diferencia>0){
        	recaudacion.getCredito().setSaldo(diferencia);
        	recaudacion.setTotal(recaudacion.getTotal()+diferencia);
            
            RangoCrediticio rangoCrediticio=servicioRangoCrediticio.obtenerSaldo(recaudacion.getCredito().getSaldo()).get();
            recaudacion.getCredito().setTasaInteresAnual(rangoCrediticio.getTasaInteresAnual());
            double tasaPeriodo=Math.rint((rangoCrediticio.getTasaInteresAnual()/recaudacion.getCredito().getPeriodicidadTotal())*100d)/100d;
            recaudacion.getCredito().setTasaPeriodo(tasaPeriodo);
            recaudacion.setCredito(servicioCredito.construir(recaudacion.getCredito()).get());
        } else {
        	recaudacion.setCredito(null);
        }
        recaudacion.setEfectivoCodigoSri(Constantes.sin_utilizacion_del_sistema_financiero);
        recaudacion.setChequeCodigoSri(Constantes.otros_con_utilizacion_sistema_financiero);
        recaudacion.setDepositoCodigoSri(Constantes.otros_con_utilizacion_sistema_financiero);
        recaudacion.setTransferenciaCodigoSri(Constantes.otros_con_utilizacion_sistema_financiero);
        recaudacion.setTarjetaCreditoCodigoSri(Constantes.tarjeta_de_credito);
        recaudacion.setTarjetaDebitoCodigoSri(Constantes.tarjeta_de_debito);
        recaudacion.setCompensacionCodigoSri(Constantes.compensacion_de_deudas);
        recaudacion.setRetencionVentaCodigoSri(Constantes.sin_utilizacion_del_sistema_financiero);
    	return rep.save(recaudacion);
    }

    @Override
    public Recaudacion actualizar(Recaudacion recaudacion) {
        return rep.save(recaudacion);
    }

    @Override
    public Recaudacion obtener(long id) {
        Optional<Recaudacion> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.recaudacion);
    }

    @Override
    public List<Recaudacion> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Recaudacion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
    
    @Override
    public Optional<Recaudacion> obtenerPorFactura(long facturaId){
    	return rep.obtenerPorFactura(facturaId);
    }
    
    @Override
    public Optional<Recaudacion> calcular(Recaudacion recaudacion){
    	double total=0;
    	
    	total=total+recaudacion.getEfectivo();
    	
    	double totalCheques=0;
        for(Cheque cheque: recaudacion.getCheques()) {
        	totalCheques=totalCheques+cheque.getValor();
        	total=total+totalCheques;
        }
        
        double totalDepositos=0;
        for(Deposito deposito: recaudacion.getDepositos()) {
        	totalDepositos=totalDepositos+deposito.getValor();
        	total=total+totalDepositos;
        }
        
        double totalTransferencias=0;
        for(Transferencia transferencia: recaudacion.getTransferencias()) {
        	totalTransferencias=totalTransferencias+transferencia.getValor();
        	total=total+totalTransferencias;
        }
        
        double totalTarjetasDebitos=0;
        for(TarjetaDebito tarjetaDebito: recaudacion.getTarjetasDebitos()) {
        	totalTarjetasDebitos=totalTarjetasDebitos+tarjetaDebito.getValor();
        	total=total+totalTarjetasDebitos;
        }
        
        double totalTarjetasCreditos=0;
        for(TarjetaCredito tarjetaCredito: recaudacion.getTarjetasCreditos()) {
        	totalTarjetasCreditos=totalTarjetasCreditos+tarjetaCredito.getValor();
        	total=total+totalTarjetasCreditos;
        }
        
        double totalCompensaciones=0;
        for(Compensacion compensacion: recaudacion.getCompensaciones()) {
        	totalCompensaciones=totalCompensaciones+compensacion.getSaldo();
        	total=total+totalCompensaciones;
        }
        
        double totalRetencionesVentas=0;
        for(RetencionVenta retencionVenta: recaudacion.getRetencionesVentas()) {
        	totalRetencionesVentas=totalRetencionesVentas+retencionVenta.getValor();
        	total=total+totalRetencionesVentas;
        }
        
        recaudacion.setTotalCheques(totalCheques);
        recaudacion.setTotalDepositos(totalDepositos);
        recaudacion.setTotalTransferencias(totalTransferencias);
        recaudacion.setTotalTarjetasDebitos(totalTarjetasDebitos);
        recaudacion.setTotalTarjetasCreditos(totalTarjetasCreditos);   
        recaudacion.setTotalCompensaciones(totalCompensaciones);   
        recaudacion.setTotalRetencionesVentas(totalRetencionesVentas);   
        recaudacion.setTotal(total);
        double pagar=recaudacion.getFactura().getTotalConDescuento()-total;
        pagar=Math.round(pagar*100.0)/100.0;
        if(pagar<0) {
        	pagar=0;
        }
        recaudacion.setTotalCredito(pagar);
        recaudacion.getCredito().setSaldo(pagar);
        if(recaudacion.getTotal()>=recaudacion.getFactura().getTotalConDescuento()) {
        	recaudacion.setCambio(recaudacion.getTotal()-recaudacion.getFactura().getTotalConDescuento());
        	recaudacion.setEtapa(Constantes.recaudado);
        } else {
        	recaudacion.setEtapa(Constantes.norecaudado);
        }
		return Optional.of(recaudacion);
    }
    
    @Override
    public void importar(MultipartFile file) {
    }
}
