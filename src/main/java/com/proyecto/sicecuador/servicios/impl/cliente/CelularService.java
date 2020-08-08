package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.Celular;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.ICelularRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ICelularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class CelularService implements ICelularService {
    @Autowired
    private ICelularRepository rep;
    @Override
    public Celular crear(Celular celular) {
        return rep.save(celular);
    }

    @Override
    public Celular actualizar(Celular celular) {
        return rep.save(celular);
    }

    @Override
    public Celular eliminar(Celular celular) {
        rep.deleteById(celular.getId());
        return celular;
    }

    @Override
    public Optional<Celular> obtener(Celular celular) {
        return rep.findById(celular.getId());
    }

    @Override
    public List<Celular> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }
}
