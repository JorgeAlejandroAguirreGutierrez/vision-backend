package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import com.proyecto.sicecuador.repositorios.comprobante.ITipoComprobanteRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.ITipoComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public Page<TipoComprobante> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

}
