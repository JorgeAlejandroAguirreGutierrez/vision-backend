package com.proyecto.vision.servicios.impl.cliente;

import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.modelos.cliente.GrupoCliente;
import com.proyecto.vision.modelos.cliente.OrigenIngreso;
import com.proyecto.vision.modelos.cliente.PlazoCredito;
import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.repositorios.cliente.IPlazoCreditoRepository;
import com.proyecto.vision.servicios.interf.cliente.IPlazoCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class PlazoCreditoService implements IPlazoCreditoService {
    @Autowired
    private IPlazoCreditoRepository rep;

    @Override
    public void validar(PlazoCredito plazoCredito) {
        if(plazoCredito.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(plazoCredito.getPlazo() == Constantes.cero) throw new DatoInvalidoException(Constantes.plazo);
    }
    
    @Override
    public PlazoCredito crear(PlazoCredito plazoCredito) {
        validar(plazoCredito);
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_plazo_credito, plazoCredito.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	plazoCredito.setCodigo(codigo.get());
    	plazoCredito.setEstado(Constantes.activo);
    	return rep.save(plazoCredito);
    }

    @Override
    public PlazoCredito actualizar(PlazoCredito plazoCredito) {
        validar(plazoCredito);
        return rep.save(plazoCredito);
    }

    @Override
    public PlazoCredito activar(PlazoCredito plazoCredito) {
        validar(plazoCredito);
        plazoCredito.setEstado(Constantes.activo);
        return rep.save(plazoCredito);
    }

    @Override
    public PlazoCredito inactivar(PlazoCredito plazoCredito) {
        validar(plazoCredito);
        plazoCredito.setEstado(Constantes.inactivo);
        return rep.save(plazoCredito);
    }

    @Override
    public PlazoCredito obtener(long id) {
        Optional<PlazoCredito> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.plazo_credito);
    }

    @Override
    public List<PlazoCredito> consultar() {
        return rep.consultar();
    }

    @Override
    public List<PlazoCredito> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<PlazoCredito> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<PlazoCredito> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<PlazoCredito> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
