package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IEmpresaRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService implements IEmpresaService {
    @Autowired
    private IEmpresaRepository rep;

    @Override
    public Empresa crear(Empresa empresa) {
        return rep.save(empresa);
    }

    @Override
    public Empresa actualizar(Empresa empresa) {
        return rep.save(empresa);
    }

    @Override
    public Empresa eliminar(Empresa empresa) {
        rep.deleteById(empresa.getId());
        return empresa;
    }

    @Override
    public Optional<Empresa> obtener(Empresa empresa) {
        return rep.findById(empresa.getId());
    }

    @Override
    public List<Empresa> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }
}
