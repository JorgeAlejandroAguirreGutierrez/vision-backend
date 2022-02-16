package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.recaudacion.RangoCrediticio;
import com.proyecto.sicecuador.modelos.recaudacion.Recaudacion;
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
            double tasa_periodo=Math.rint((rangoCrediticio.getTasaInteresAnual()/recaudacion.getCredito().getPeriodicidadTotal())*100d)/100d;
            recaudacion.getCredito().setTasaPeriodo(tasa_periodo);
            recaudacion.setCredito(servicioCredito.construir(recaudacion.getCredito()).get());
        } else {
        	recaudacion.setCredito(null);
        }
    	return rep.save(recaudacion);
    }

    @Override
    public Recaudacion actualizar(Recaudacion recaudacion) {
        return rep.save(recaudacion);
    }

    @Override
    public Recaudacion eliminar(Recaudacion recaudacion) {
        rep.deleteById(recaudacion.getId());
        return recaudacion;
    }

    @Override
    public Optional<Recaudacion> obtener(Recaudacion recaudacion) {
        return rep.findById(recaudacion.getId());
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
    public boolean importar(MultipartFile file) {
        return false;
    }
}
