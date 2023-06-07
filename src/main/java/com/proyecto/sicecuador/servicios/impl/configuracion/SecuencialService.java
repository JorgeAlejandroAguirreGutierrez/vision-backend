package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.configuracion.Secuencial;
import com.proyecto.sicecuador.repositorios.configuracion.ISecuencialRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.ISecuencialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SecuencialService implements ISecuencialService {
    @Autowired
    private ISecuencialRepository rep;

    @Override
    public void validar(Secuencial secuencial) {
        if(secuencial.getNumeroSiguiente() <= Constantes.ceroId) throw new DatoInvalidoException(Constantes.descripcion);
    }
    
    @Override
    public Secuencial crear(Secuencial secuencial) {
        validar(secuencial);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_secuencial);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	secuencial.setCodigo(codigo.get());
    	secuencial.setEstado(Constantes.activo);
    	return rep.save(secuencial);
    }

    @Override
    public Secuencial actualizar(Secuencial secuencial) {
        validar(secuencial);
        return rep.save(secuencial);
    }

    @Override
    public Secuencial activar(Secuencial secuencial) {
        validar(secuencial);
        secuencial.setEstado(Constantes.activo);
        return rep.save(secuencial);
    }

    @Override
    public Secuencial inactivar(Secuencial secuencial) {
        validar(secuencial);
        secuencial.setEstado(Constantes.inactivo);
        return rep.save(secuencial);
    }

    @Override
    public Secuencial obtener(long id) {
    	Optional<Secuencial> res= rep.findById(id);
    	if(res.isPresent()) {
    		return res.get();
    	}
    	throw new EntidadNoExistenteException(Constantes.tipo_pago);
    }

    @Override
    public Secuencial obtenerPorTipoComprobanteYEstacion(long tipoComprobanteId, long estacionId) {
        Optional<Secuencial> res= rep.obtenerPorTipoComprobanteYEstacion(tipoComprobanteId, estacionId);
        if(res.isPresent()) {
            return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.secuencial);
    }

    @Override
    public List<Secuencial> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<Secuencial> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public Page<Secuencial> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
