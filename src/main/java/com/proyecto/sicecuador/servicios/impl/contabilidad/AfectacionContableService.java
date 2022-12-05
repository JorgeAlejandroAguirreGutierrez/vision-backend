package com.proyecto.sicecuador.servicios.impl.contabilidad;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.contabilidad.AfectacionContable;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.repositorios.contabilidad.IAfectacionContableRepository;
import com.proyecto.sicecuador.servicios.interf.contabilidad.IAfectacionContableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AfectacionContableService implements IAfectacionContableService {
    @Autowired
    private IAfectacionContableRepository rep;
    
    @Override
    public AfectacionContable crear(AfectacionContable afectacionContable) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_afectacion_contable);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	afectacionContable.setCodigo(codigo.get());
    	return rep.save(afectacionContable);
    }

    @Override
    public AfectacionContable actualizar(AfectacionContable afectacionContable) {
    	return rep.save(afectacionContable);
    }

    @Override
    public AfectacionContable activar(AfectacionContable afectacionContable) {
        afectacionContable.setEstado(Constantes.activo);
        return rep.save(afectacionContable);
    }

    @Override
    public AfectacionContable inactivar(AfectacionContable afectacionContable) {
        afectacionContable.setEstado(Constantes.inactivo);
        return rep.save(afectacionContable);
    }

    @Override
    public AfectacionContable obtener(long id) {
        Optional<AfectacionContable> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.afectacion_contable);
    }

    @Override
    public List<AfectacionContable> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<AfectacionContable> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<AfectacionContable> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }


    @Override
    public List<AfectacionContable> buscar(AfectacionContable afectacionContable) {
        return  rep.buscar(afectacionContable.getDescripcion(), Constantes.activo);
    }
    
    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<AfectacionContable> afectacionesContables=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,7);
            for (List<String> datos: info) {
                AfectacionContable afectacionContable = new AfectacionContable(datos);
                afectacionesContables.add(afectacionContable);
            }
            rep.saveAll(afectacionesContables);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

}
