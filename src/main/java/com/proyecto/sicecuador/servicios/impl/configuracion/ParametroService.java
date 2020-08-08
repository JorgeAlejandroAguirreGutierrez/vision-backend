package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
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
    public boolean importar(MultipartFile file) {
        return false;
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
