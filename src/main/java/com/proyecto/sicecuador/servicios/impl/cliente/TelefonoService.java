package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.Telefono;
import com.proyecto.sicecuador.repositorios.interf.cliente.IOrigenIngresoRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.ITelefonoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ITelefonoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class TelefonoService implements ITelefonoService {
    @Autowired
    private ITelefonoRepository rep;
    @Override
    public Telefono crear(Telefono telefono) {
        return rep.save(telefono);
    }

    @Override
    public Telefono actualizar(Telefono telefono) {
        return rep.save(telefono);
    }

    @Override
    public Telefono eliminar(Telefono telefono) {
        rep.deleteById(telefono.getId());
        return telefono;
    }

    @Override
    public Optional<Telefono> obtener(Telefono telefono) {
        return rep.findById(telefono.getId());
    }

    @Override
    public List<Telefono> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }
}
