package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.configuracion.TipoComprobante;
import com.proyecto.sicecuador.repositorios.configuracion.ITipoComprobanteRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.ITipoComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoComprobanteService implements ITipoComprobanteService {
    @Autowired
    private ITipoComprobanteRepository rep;
    
    @Override
    public TipoComprobante crear(TipoComprobante tipoComprobante) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_tipo_comprobante);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	tipoComprobante.setCodigo(codigo.get());
    	return rep.save(tipoComprobante);
    }

    @Override
    public TipoComprobante actualizar(TipoComprobante tipoComprobante) {
        return rep.save(tipoComprobante);
    }

    @Override
    public TipoComprobante obtener(long id) {
        Optional<TipoComprobante> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.tipo_comprobante);
    }

    @Override
    public TipoComprobante obtenerPorNombreTabla(String nombreTabla) {
        Optional<TipoComprobante> res = rep.obtenerPorNombreTabla(nombreTabla);
        if(res.isPresent()) {
            return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.tipo_comprobante);
    }

    @Override
    public List<TipoComprobante> consultar() {
        return rep.findAll();
    }

    @Override
    public List<TipoComprobante> consultarPorEstado(String estado){
        return rep.consultarPorEstado(estado);
    }
    @Override
    public List<TipoComprobante> consultarElectronica() {
        return rep.consultarPorElectronica(Constantes.si, Constantes.activo);
    }

    @Override
    public Page<TipoComprobante> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

}
