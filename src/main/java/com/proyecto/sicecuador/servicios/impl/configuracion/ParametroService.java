package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.repositorios.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.IParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ParametroService implements IParametroService {
    @Autowired
    private IParametroRepository rep;
    
    @Override
    public Parametro crear(Parametro parametro) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_parametro);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	parametro.setCodigo(codigo.get());
    	parametro.setEstado(Constantes.activo);
    	return rep.save(parametro);
    }

    @Override
    public Parametro actualizar(Parametro parametro) {
        return rep.save(parametro);
    }

    @Override
    public Parametro activar(Parametro parametro) {
        parametro.setEstado(Constantes.activo);
        return rep.save(parametro);
    }

    @Override
    public Parametro inactivar(Parametro parametro) {
        parametro.setEstado(Constantes.inactivo);
        return rep.save(parametro);
    }

    @Override
    public Parametro obtener(long id) {
        Optional<Parametro> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.parametro);
    }

    @Override
    public List<Parametro> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Parametro> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Parametro> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public Parametro obtenerPorTipo(Parametro parametro) {
        Optional<Parametro> res= rep.findByTipo(parametro.getTipo(), Constantes.activo);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.parametro);
    }

    @Override
    public Parametro obtenerPorTipoTabla(Parametro parametro) {
        Optional<Parametro> res= rep.findByTablaAndTipo(parametro.getTabla(), parametro.getTipo(), Constantes.activo);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.parametro);
    }
    
    @Override
    public List<Parametro> consultarPorTipo(Parametro parametro) {
        return rep.AllByTipo(parametro.getTipo(), Constantes.activo);
    }
    
    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<Parametro> parametros=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal,1);
            for (List<String> datos: info) {
                Parametro parametro = new Parametro(datos);
                parametros.add(parametro);
            }
            rep.saveAll(parametros);
        }catch (Exception e){
        	System.out.println(e.getMessage());
        }
    }
}
