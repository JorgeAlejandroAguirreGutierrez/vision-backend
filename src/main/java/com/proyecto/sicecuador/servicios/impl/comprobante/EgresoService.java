package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.Egreso;
import com.proyecto.sicecuador.repositorios.comprobante.IEgresoRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IEgresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class EgresoService implements IEgresoService {
    @Autowired
    private IEgresoRepository rep;
    
    @Override
    public Egreso crear(Egreso egreso) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_egreso);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	egreso.setCodigo(codigo.get());
    	return rep.save(egreso);
    }

    @Override
    public Egreso actualizar(Egreso egreso) {
        return rep.save(egreso);
    }

    @Override
    public Egreso activar(Egreso egreso) {
        egreso.setEstado(Constantes.activo);
        return rep.save(egreso);
    }

    @Override
    public Egreso inactivar(Egreso egreso) {
        egreso.setEstado(Constantes.inactivo);
        return rep.save(egreso);
    }

    @Override
    public Egreso obtener(long id) {
        Optional<Egreso> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.egreso);
    }

    @Override
    public List<Egreso> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Egreso> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Egreso> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile file) {
    }
}
