package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.repositorios.inventario.IMedidaRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IMedidaService;
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
    	medida.setEstado(Constantes.activo);
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
        medida.setEstado(Constantes.activo);
        return rep.save(medida);
    }

    @Override
    public Medida inactivar(Medida medida) {
        validar(medida);
        medida.setEstado(Constantes.inactivo);
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
