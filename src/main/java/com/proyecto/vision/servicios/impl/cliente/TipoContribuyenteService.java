package com.proyecto.vision.servicios.impl.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.cliente.TipoContribuyente;
import com.proyecto.vision.repositorios.cliente.ITipoContribuyenteRepository;
import com.proyecto.vision.servicios.interf.cliente.ITipoContribuyenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TipoContribuyenteService implements ITipoContribuyenteService {
    @Autowired
    private ITipoContribuyenteRepository rep;
    @Override
    public TipoContribuyente crear(TipoContribuyente tipoContribuyente) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_tipo_contribuyente);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	tipoContribuyente.setCodigo(codigo.get());
    	return rep.save(tipoContribuyente);
    }

    @Override
    public TipoContribuyente actualizar(TipoContribuyente tipoContribuyente) {
        return rep.save(tipoContribuyente);
    }

    @Override
    public TipoContribuyente obtener(long id) {
        Optional<TipoContribuyente> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.tipo_contribuyente);
    }

    @Override
    public List<TipoContribuyente> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<TipoContribuyente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
