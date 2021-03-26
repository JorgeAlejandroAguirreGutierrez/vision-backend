package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.IParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    	return rep.save(parametro);
    }

    @Override
    public Parametro actualizar(Parametro parametro) {
        return rep.save(parametro);
    }

    @Override
    public Parametro eliminar(Parametro parametro) {
        rep.deleteById(parametro.getId());
        return parametro;
    }

    @Override
    public Optional<Parametro> obtener(Parametro parametro) {
        return rep.findById(parametro.getId());
    }

    @Override
    public List<Parametro> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Parametro> parametros=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,1);
            for (List<String> datos: info) {
                Parametro parametro = new Parametro(datos);
                parametros.add(parametro);
            }
            rep.saveAll(parametros);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Optional<Parametro> obtenerTipo(Parametro parametro) {
        return rep.findByTipo(parametro.getTipo());
    }

    @Override
    public Optional<Parametro> obtenerTipoTabla(Parametro parametro) {
        return rep.findByTablaAndTipo(parametro.getTabla(), parametro.getTipo());
    }
    @Override
    public List<Parametro> consultarTipo(Parametro parametro) {
        return rep.AllByTipo(parametro.getTipo());
    }
}
