package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.repositorios.usuario.IEstablecimientoRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class EstablecimientoService implements IEstablecimientoService {
    @Autowired
    private IEstablecimientoRepository rep;
    
    @Override
    public Establecimiento crear(Establecimiento establecimiento) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_establecimiento);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	establecimiento.setCodigo(codigo.get());
    	establecimiento.setEstado(Constantes.activo);
    	return rep.save(establecimiento);
    }

    @Override
    public Establecimiento actualizar(Establecimiento establecimiento) {
        return rep.save(establecimiento);
    }

    @Override
    public Establecimiento activar(Establecimiento establecimiento) {
        establecimiento.setEstado(Constantes.activo);
        return rep.save(establecimiento);
    }

    @Override
    public Establecimiento inactivar(Establecimiento establecimiento) {
        establecimiento.setEstado(Constantes.inactivo);
        return rep.save(establecimiento);
    }

    @Override
    public Establecimiento obtener(long id) {
        Optional<Establecimiento> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.establecimiento);
    }

    @Override
    public List<Establecimiento> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Establecimiento> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Establecimiento> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<Establecimiento> establecimientos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,0);
            for (List<String> datos: info) {
                Establecimiento establecimiento = new Establecimiento(datos);
                establecimientos.add(establecimiento);
            }
            rep.saveAll(establecimientos);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
