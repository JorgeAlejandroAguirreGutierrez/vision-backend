package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.recaudacion.*;
import com.proyecto.sicecuador.repositorios.recaudacion.IRecaudacionRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICreditoService;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IRecaudacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
public class RecaudacionService implements IRecaudacionService {
    @Autowired
    private IRecaudacionRepository rep;
    
    @Autowired
    private ICreditoService servicioCredito;

    @Override
    public void validar(Recaudacion recaudacion) {
        if(recaudacion.getFecha() == null) throw new DatoInvalidoException(Constantes.fecha);
        if(recaudacion.getTotal() == Constantes.cero) throw new DatoInvalidoException(Constantes.total);
        if(recaudacion.getFactura().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.factura);
        if(recaudacion.getSesion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.sesion);
        if(recaudacion.getCredito().getSaldo() == Constantes.cero) recaudacion.setCredito(null);
    }
    
    @Override
    public Recaudacion crear(Recaudacion recaudacion) {
    	validar(recaudacion);
        Optional<String>codigo=Util.generarCodigo(Constantes.tabla_recaudacion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	recaudacion.setCodigo(codigo.get());
    	double diferencia= recaudacion.getFactura().getTotalConDescuento()-recaudacion.getTotal();
        if (diferencia>0){
        	recaudacion.setTotalCredito(diferencia);
            recaudacion.getCredito().setSaldo(diferencia);
            recaudacion.setTotal(recaudacion.getTotal()+diferencia);
        }
        recaudacion.setEfectivoCodigoSri(Constantes.sin_utilizacion_del_sistema_financiero);
        recaudacion.setChequeCodigoSri(Constantes.otros_con_utilizacion_sistema_financiero);
        recaudacion.setDepositoCodigoSri(Constantes.otros_con_utilizacion_sistema_financiero);
        recaudacion.setTransferenciaCodigoSri(Constantes.otros_con_utilizacion_sistema_financiero);
        recaudacion.setTarjetaCreditoCodigoSri(Constantes.tarjeta_de_credito);
        recaudacion.setTarjetaDebitoCodigoSri(Constantes.tarjeta_de_debito);
        recaudacion.setEstado(Constantes.recaudado);
    	Recaudacion res = rep.save(recaudacion);
        res.normalizar();
        return res;
    }

    @Override
    public Recaudacion actualizar(Recaudacion recaudacion) {
        validar(recaudacion);
        Recaudacion res = rep.save(recaudacion);
        res.normalizar();
        return res;
    }

    @Override
    public Recaudacion obtener(long id) {
        Optional<Recaudacion> recaudacion = rep.findById(id);
        if(recaudacion.isPresent()) {
        	Recaudacion res = recaudacion.get();
            res.normalizar();
            return res;
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
    public Recaudacion obtenerPorFactura(long facturaId){
        Optional<Recaudacion> recaudacion = rep.obtenerPorFactura(facturaId);
        if(recaudacion.isPresent()) {
            Recaudacion res = recaudacion.get();
            res.normalizar();
            return res;
        }
        return null;
    }
    
    @Override
    public Recaudacion calcular(Recaudacion recaudacion){
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

        total = total + recaudacion.getCredito().getSaldo();
        recaudacion.setTotalCredito(recaudacion.getCredito().getSaldo());
        recaudacion.setTotalCheques(totalCheques);
        recaudacion.setTotalDepositos(totalDepositos);
        recaudacion.setTotalTransferencias(totalTransferencias);
        recaudacion.setTotalTarjetasDebitos(totalTarjetasDebitos);
        recaudacion.setTotalTarjetasCreditos(totalTarjetasCreditos);
        if(total >= recaudacion.getFactura().getTotalConDescuento()){
            recaudacion.setCambio(total-recaudacion.getFactura().getTotalConDescuento());
        } else {
            recaudacion.setCambio(Constantes.cero);
        }
        if(total >= recaudacion.getFactura().getTotalConDescuento()){
            total = recaudacion.getFactura().getTotalConDescuento();
        }
        double porPagar=recaudacion.getFactura().getTotalConDescuento()-total;
        porPagar=Math.round(porPagar*100.0)/100.0;
        if(porPagar<0) {
            porPagar=0;
        }
        recaudacion.setPorPagar(porPagar);
        recaudacion.setTotal(total);
        if(porPagar > 0){
            recaudacion.setEstado(Constantes.norecaudado);
        } else{
            recaudacion.setEstado(Constantes.recaudado);
        }
		return recaudacion;
    }
}
