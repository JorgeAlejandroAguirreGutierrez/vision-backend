package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.contabilidad.AfectacionContable;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.repositorios.inventario.IBodegaRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IBodegaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BodegaService implements IBodegaService {
    @Autowired
    private IBodegaRepository rep;

    @Override
    public void validar(Bodega bodega) {
        if(bodega.getNombre().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombre);
    }
    
    @Override
    public Bodega crear(Bodega bodega) {
        validar(bodega);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_bodega);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	bodega.setCodigo(codigo.get());
    	bodega.setEstado(Constantes.activo);
    	return rep.save(bodega);
    }

    @Override
    public Bodega actualizar(Bodega bodega) {
        validar(bodega);
        return rep.save(bodega);
    }

    @Override
    public Bodega activar(Bodega bodega) {
        validar(bodega);
        bodega.setEstado(Constantes.activo);
        return rep.save(bodega);
    }

    @Override
    public Bodega inactivar(Bodega bodega) {
        validar(bodega);
        bodega.setEstado(Constantes.inactivo);
        return rep.save(bodega);
    }

    @Override
    public Bodega obtener(long id) {
        Optional<Bodega> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.bodega);
    }

    @Override
    public List<Bodega> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Bodega> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Bodega> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
