package com.proyecto.vision.servicios.impl.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.contabilidad.AfectacionContable;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.repositorios.inventario.IBodegaRepository;
import com.proyecto.vision.servicios.interf.inventario.IBodegaService;
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
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_bodega, bodega.getEmpresa().getId());
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
        return rep.consultar();
    }
    
    @Override
    public List<Bodega> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<Bodega> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<Bodega> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<Bodega> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
