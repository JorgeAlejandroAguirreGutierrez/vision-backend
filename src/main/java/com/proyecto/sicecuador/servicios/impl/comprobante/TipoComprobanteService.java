package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.TipoComprobante;
import com.proyecto.sicecuador.repositorios.interf.comprobante.ITipoComprobanteRepository;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.ITipoComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class TipoComprobanteService implements ITipoComprobanteService {
    @Autowired
    private ITipoComprobanteRepository rep;
    
    @Override
    public TipoComprobante crear(TipoComprobante tipo_comprobante) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_tipo_comprobante);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	tipo_comprobante.setCodigo(codigo.get());
    	return rep.save(tipo_comprobante);
    }

    @Override
    public TipoComprobante actualizar(TipoComprobante tipo_comprobante) {
        return rep.save(tipo_comprobante);
    }

    @Override
    public TipoComprobante eliminar(TipoComprobante tipo_comprobante) {
        rep.deleteById(tipo_comprobante.getId());
        return tipo_comprobante;
    }

    @Override
    public Optional<TipoComprobante> obtener(TipoComprobante tipo_comprobante) {
        return rep.findById(tipo_comprobante.getId());
    }

    @Override
    public List<TipoComprobante> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }

}
