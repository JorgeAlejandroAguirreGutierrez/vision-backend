package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.repositorios.interf.usuario.IEstablecimientoRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class EstablecimientoService implements IEstablecimientoService {
    @Autowired
    private IEstablecimientoRepository rep;
    @Override
    public Establecimiento crear(Establecimiento establecimiento) {
        return rep.save(establecimiento);
    }

    @Override
    public Establecimiento actualizar(Establecimiento establecimiento) {
        return rep.save(establecimiento);
    }

    @Override
    public Establecimiento eliminar(Establecimiento establecimiento) {
        rep.deleteById(establecimiento.getId());
        return establecimiento;
    }

    @Override
    public Optional<Establecimiento> obtener(Establecimiento establecimiento) {
        return rep.findById(establecimiento.getId());
    }

    @Override
    public List<Establecimiento> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }
}
