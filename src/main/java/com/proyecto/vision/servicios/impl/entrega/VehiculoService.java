package com.proyecto.vision.servicios.impl.entrega;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.CodigoNoExistenteException;
import com.proyecto.vision.exception.EntidadNoExistenteException;
import com.proyecto.vision.modelos.entrega.Vehiculo;
import com.proyecto.vision.repositorios.entrega.IVehiculoRepository;
import com.proyecto.vision.servicios.interf.entrega.IVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class VehiculoService implements IVehiculoService {
    @Autowired
    private IVehiculoRepository rep;
    
    @Override
    public Vehiculo crear(Vehiculo vehiculo) {
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(null, Constantes.tabla_vehiculo, vehiculo.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	vehiculo.setCodigo(codigo.get());
    	vehiculo.setEstado(Constantes.estadoActivo);
    	return rep.save(vehiculo);
    }

    @Override
    public Vehiculo actualizar(Vehiculo vehiculo) {
        return rep.save(vehiculo);
    }

    @Override
    public Vehiculo activar(Vehiculo vehiculo) {
        vehiculo.setEstado(Constantes.estadoActivo);
        return rep.save(vehiculo);
    }

    @Override
    public Vehiculo inactivar(Vehiculo vehiculo) {
        vehiculo.setEstado(Constantes.estadoInactivo);
        return rep.save(vehiculo);
    }

    @Override
    public Vehiculo obtener(long id) {
        Optional<Vehiculo> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.vehiculo_transporte);   
    }

    @Override
    public List<Vehiculo> consultar() {
        return rep.consultar();
    }

    @Override
    public List<Vehiculo> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<Vehiculo> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<Vehiculo> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<Vehiculo> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
