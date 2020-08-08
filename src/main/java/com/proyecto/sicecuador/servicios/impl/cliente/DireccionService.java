package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.IClienteRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.IDireccionRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IDireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class DireccionService implements IDireccionService {
    @Autowired
    private IDireccionRepository rep;
    @Override
    public Direccion crear(Direccion direccion) {
        //direccion.setCodigo(Util.generarCodigo("direccion","CREAR",rep.count()));
        return rep.save(direccion);
    }

    @Override
    public Direccion actualizar(Direccion direccion) {
        return rep.save(direccion);
    }

    @Override
    public Direccion eliminar(Direccion direccion) {
        rep.deleteById(direccion.getId());
        return direccion;
    }

    @Override
    public Optional<Direccion> obtener(Direccion direccion) {
        return rep.findById(direccion.getId());
    }

    @Override
    public List<Direccion> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }
}
