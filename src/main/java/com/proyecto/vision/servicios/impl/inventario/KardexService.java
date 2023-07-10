package com.proyecto.vision.servicios.impl.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.repositorios.inventario.IKardexRepository;
import com.proyecto.vision.servicios.interf.inventario.IKardexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KardexService implements IKardexService {
    @Autowired
    private IKardexRepository rep;
    
    @Override
    public Kardex crear(Kardex kardex) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_kardex);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	kardex.setCodigo(codigo.get());
    	return rep.save(kardex);
    }

    @Override
    public Kardex actualizar(Kardex kardex) {
        return rep.save(kardex);
    }

    @Override
    public Kardex obtener(long id) {
        Optional<Kardex> res=rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.kardex);
    }

    @Override
    public List<Kardex> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<Kardex> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
    @Override
    public List<Kardex> consultarPorProducto(long productoId) {
        return rep.consultarPorProducto(productoId);
    }
    @Override
    public Kardex obtenerUltimoPorBodega(long bodegaId, long productoId) {
        Optional<Kardex> res = rep.obtenerUltimoPorBodegaYProducto(bodegaId, productoId);
        if(res.isEmpty()){
            return null;
        }
        return res.get();
    }
    @Override
    public void eliminar(long tipoComprobanteId, long tipoOperacionId,String referencia) {
        rep.eliminar(tipoComprobanteId, tipoOperacionId, referencia);
    }
}