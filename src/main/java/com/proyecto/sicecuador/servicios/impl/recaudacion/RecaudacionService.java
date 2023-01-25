package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.recaudacion.Cheque;
import com.proyecto.sicecuador.modelos.recaudacion.Deposito;
import com.proyecto.sicecuador.modelos.recaudacion.Recaudacion;
import com.proyecto.sicecuador.modelos.recaudacion.TarjetaCredito;
import com.proyecto.sicecuador.modelos.recaudacion.TarjetaDebito;
import com.proyecto.sicecuador.modelos.recaudacion.Transferencia;
import com.proyecto.sicecuador.repositorios.recaudacion.IRecaudacionRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICreditoService;
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
        	recaudacion.setTotalCredito(diferencia);
        	recaudacion.setTotal(recaudacion.getTotal()+diferencia);
            recaudacion.getCredito().setSaldo(diferencia);
        }
        recaudacion.setEfectivoCodigoSri(Constantes.sin_utilizacion_del_sistema_financiero);
        recaudacion.setChequeCodigoSri(Constantes.otros_con_utilizacion_sistema_financiero);
        recaudacion.setDepositoCodigoSri(Constantes.otros_con_utilizacion_sistema_financiero);
        recaudacion.setTransferenciaCodigoSri(Constantes.otros_con_utilizacion_sistema_financiero);
        recaudacion.setTarjetaCreditoCodigoSri(Constantes.tarjeta_de_credito);
        recaudacion.setTarjetaDebitoCodigoSri(Constantes.tarjeta_de_debito);
        recaudacion.setEstado(Constantes.recaudado);
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
        double pagar=recaudacion.getFactura().getTotalConDescuento()-total;
        pagar=Math.round(pagar*100.0)/100.0;
        if(pagar<0) {
            pagar=0;
        }
        total = total + pagar;
        recaudacion.getCredito().setSaldo(pagar);
        recaudacion.setTotalCredito(pagar);
        recaudacion.setTotalCheques(totalCheques);
        recaudacion.setTotalDepositos(totalDepositos);
        recaudacion.setTotalTransferencias(totalTransferencias);
        recaudacion.setTotalTarjetasDebitos(totalTarjetasDebitos);
        recaudacion.setTotalTarjetasCreditos(totalTarjetasCreditos);
        if(total >= recaudacion.getFactura().getTotalConDescuento()){
            recaudacion.setCambio(total-recaudacion.getFactura().getTotalConDescuento());
        }
        if(total >= recaudacion.getFactura().getTotalConDescuento()){
            total = recaudacion.getFactura().getTotalConDescuento();
        }
        recaudacion.setTotal(total);
        recaudacion.setEstado(Constantes.recaudado);
		return Optional.of(recaudacion);
    }
    
    @Override
    public void importar(MultipartFile file) {
    }
}
