package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.repositorios.configuracion.IUbicacionRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.IUbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UbicacionService implements IUbicacionService {
    @Autowired
    private IUbicacionRepository rep;

    @Override
    public void validar(Ubicacion ubicacion) {
        if(ubicacion.getCodigoNorma().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.codigoNorma);
        if(ubicacion.getProvincia().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.provincia);
        if(ubicacion.getCanton().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.canton);
        if(ubicacion.getParroquia().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.parroquia);
    }
    
    @Override
    public Ubicacion crear(Ubicacion ubicacion) {
        validar(ubicacion);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_ubicacion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	ubicacion.setCodigo(codigo.get());
    	ubicacion.setEstado(Constantes.activo);
    	return rep.save(ubicacion);
    }

    @Override
    public Ubicacion actualizar(Ubicacion ubicacion) {
        validar(ubicacion);
        return rep.save(ubicacion);
    }

    @Override
    public Ubicacion activar(Ubicacion ubicacion) {
        validar(ubicacion);
        ubicacion.setEstado(Constantes.activo);
        return rep.save(ubicacion);
    }

    @Override
    public Ubicacion inactivar(Ubicacion ubicacion) {
        validar(ubicacion);
        ubicacion.setEstado(Constantes.inactivo);
        return rep.save(ubicacion);
    }

    @Override
    public Ubicacion obtener(long id) {
        Optional<Ubicacion> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.ubicacion);
        
    }

    @Override
    public List<Ubicacion> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Ubicacion> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Ubicacion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<Ubicacion> consultarProvincias() {
        List<String> provincias=rep.findProvincias(Constantes.activo);
        List<Ubicacion> ubicaciones=new ArrayList<>();
        for (String provincia: provincias) {
            Ubicacion ubicacion=new Ubicacion();
            ubicacion.setProvincia(provincia);
            ubicaciones.add(ubicacion);
        }
        return ubicaciones;
    }

    @Override
    public List<Ubicacion> consultarCantones(Ubicacion ubicacion) {
        List<String> cantones=rep.findCantones(ubicacion.getProvincia(), Constantes.activo);
        List<Ubicacion> ubicaciones=new ArrayList<>();
        for (String canton: cantones) {
            Ubicacion _ubicacion=new Ubicacion();
            _ubicacion.setCanton(canton);
            ubicaciones.add(_ubicacion);
        }
        return ubicaciones;
    }

    @Override
    public List<Ubicacion> consultarParroquias(String canton) {
        return rep.findParroquias(canton, Constantes.activo);
    }
    @Override
    public Ubicacion obtenerUbicacionId(String provincia, String canton, String parroquia) {
        Optional<Ubicacion> resp=rep.findByProvinciaAndCantonAndParroquia(provincia, provincia, parroquia, Constantes.activo);
        if(resp.isEmpty()) {
        	throw new EntidadNoExistenteException(Constantes.ubicacion);
        }
        return resp.get();
        
    } 

    @Override
    public List<Ubicacion> buscar(String codigoNorma, String provincia, String canton, String parroquia) {
        return  rep.buscar(codigoNorma, provincia, provincia, parroquia);
    }
}
