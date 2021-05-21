package com.proyecto.sicecuador.servicios.impl.administracion;

import com.proyecto.sicecuador.modelos.administracion.Modelo;
import com.proyecto.sicecuador.repositorios.administracion.IModeloRepository;
import com.proyecto.sicecuador.servicios.interf.administracion.IModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class ModeloService implements IModeloService {
    @Autowired
    private IModeloRepository rep;
    @Override
    public Modelo crear(Modelo modelo) {
        return rep.save(modelo);
    }

    @Override
    public Modelo actualizar(Modelo modelo) {
        return rep.save(modelo);
    }

    @Override
    public Modelo eliminar(Modelo modelo) {
        rep.deleteById(modelo.getId());
        return modelo;
    }

    @Override
    public Optional<Modelo> obtener(Modelo modelo) {
        return rep.findById(modelo.getId());
    }

    @Override
    public List<Modelo> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }

}
