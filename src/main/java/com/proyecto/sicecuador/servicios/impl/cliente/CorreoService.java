package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.Correo;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.ICorreoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ICorreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class CorreoService implements ICorreoService {
    @Autowired
    private ICorreoRepository rep;
    @Override
    public Correo crear(Correo correo) {
        //correo.setCodigo(Util.generarCodigo("correo","CREAR",rep.count()));
        return rep.save(correo);
    }

    @Override
    public Correo actualizar(Correo correo) {
        return rep.save(correo);
    }

    @Override
    public Correo eliminar(Correo correo) {
        rep.deleteById(correo.getId());
        return correo;
    }

    @Override
    public Optional<Correo> obtener(Correo correo) {
        return rep.findById(correo.getId());
    }

    @Override
    public List<Correo> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }
}
