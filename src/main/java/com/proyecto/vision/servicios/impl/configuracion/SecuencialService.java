package com.proyecto.vision.servicios.impl.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.repositorios.configuracion.ISecuencialRepository;
import com.proyecto.vision.servicios.interf.configuracion.ISecuencialService;
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
    	secuencial.setEstado(Constantes.estadoActivo);
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
        secuencial.setEstado(Constantes.estadoActivo);
        return rep.save(secuencial);
    }

    @Override
    public Secuencial inactivar(Secuencial secuencial) {
        validar(secuencial);
        secuencial.setEstado(Constantes.estadoInactivo);
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
    public Secuencial obtenerPorTipoComprobanteYEstacionYEmpresaYEstado(long tipoComprobanteId, long estacionId, long empresaId, String estado) {
        Optional<Secuencial> res = rep.obtenerPorTipoComprobanteYEstacionYEmpresaYEstado(tipoComprobanteId, estacionId, empresaId, estado);
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
    public List<Secuencial> consultarPorEmpresa(long empresaId) {
        return rep.consultarPorEmpresa(empresaId);
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
