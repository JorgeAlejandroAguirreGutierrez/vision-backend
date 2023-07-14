package com.proyecto.vision.servicios.impl.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.DatoInvalidoException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.inventario.Medida;
import com.proyecto.vision.repositorios.inventario.IMedidaRepository;
import com.proyecto.vision.servicios.interf.inventario.IMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class MedidaService implements IMedidaService {
    @Autowired
    private IMedidaRepository rep;

    @Override
    public void validar(Medida medida) {
        if(medida.getTipo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.tipo);
        if(medida.getDescripcion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.descripcion);
        if(medida.getAbreviatura().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.abreviatura);
    }
    
    @Override
    public Medida crear(Medida medida) {
        validar(medida);
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_medida, medida.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	medida.setCodigo(codigo.get());
    	medida.setEstado(Constantes.estadoActivo);
    	return rep.save(medida);
    }

    @Override
    public Medida actualizar(Medida medida) {
        validar(medida);
        return rep.save(medida);
    }

    @Override
    public Medida activar(Medida medida) {
        validar(medida);
        medida.setEstado(Constantes.estadoActivo);
        return rep.save(medida);
    }

    @Override
    public Medida inactivar(Medida medida) {
        validar(medida);
        medida.setEstado(Constantes.estadoInactivo);
        return rep.save(medida);
    }

    @Override
    public Medida obtener(long id) {
        Optional<Medida> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.medida);
    }

    @Override
    public List<Medida> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<Medida> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<Medida> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<Medida> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<Medida> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
