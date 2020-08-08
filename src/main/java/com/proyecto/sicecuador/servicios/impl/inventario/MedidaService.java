package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.repositorios.interf.inventario.IMedidaRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class MedidaService implements IMedidaService {
    @Autowired
    private IMedidaRepository rep;
    @Override
    public Medida crear(Medida medida) {
        return rep.save(medida);
    }

    @Override
    public Medida actualizar(Medida medida) {
        return rep.save(medida);
    }

    @Override
    public Medida eliminar(Medida medida) {
        rep.deleteById(medida.getId());
        return medida;
    }

    @Override
    public Optional<Medida> obtener(Medida medida) {
        return rep.findById(medida.getId());
    }

    @Override
    public List<Medida> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }
}
