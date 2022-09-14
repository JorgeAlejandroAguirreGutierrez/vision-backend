package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import com.proyecto.sicecuador.repositorios.configuracion.IEmpresaRepository;
import com.proyecto.sicecuador.repositorios.configuracion.ITipoIdentificacionRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.IEmpresaService;
import com.proyecto.sicecuador.servicios.interf.configuracion.ITipoIdentificacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TipoIdentificacionService implements ITipoIdentificacionService {
    @Autowired
    private ITipoIdentificacionRepository rep;

    @Override
    public TipoIdentificacion crear(TipoIdentificacion tipoIdentificacion) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_tipo_identificacion);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	tipoIdentificacion.setCodigo(codigo.get());
    	return rep.save(tipoIdentificacion);
    }

    @Override
    public TipoIdentificacion actualizar(TipoIdentificacion tipoIdentificacion) {
        return rep.save(tipoIdentificacion);
    }

    @Override
    public TipoIdentificacion eliminar(TipoIdentificacion tipoIdentificacion) {
        rep.deleteById(tipoIdentificacion.getId());
        return tipoIdentificacion;
    }

    @Override
    public Optional<TipoIdentificacion> obtener(TipoIdentificacion tipoIdentificacion) {
        return rep.findById(tipoIdentificacion.getId());
    }

    @Override
    public List<TipoIdentificacion> consultar() {
        return rep.findAll();
    }

    @Override
    public Page<TipoIdentificacion> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<TipoIdentificacion> tiposIdentificaciones=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,0);
            for (List<String> datos: info) {
            	TipoIdentificacion tipoIdentificacion = new TipoIdentificacion(datos);
                tiposIdentificaciones.add(tipoIdentificacion);
            }
            if(tiposIdentificaciones.isEmpty()){
                return false;
            }
            rep.saveAll(tiposIdentificaciones);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
