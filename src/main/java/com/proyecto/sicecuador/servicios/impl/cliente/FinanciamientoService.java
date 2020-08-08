package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.Financiamiento;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.IFinanciamientoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IFinanciamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class FinanciamientoService implements IFinanciamientoService {
    @Autowired
    private IFinanciamientoRepository rep;
    @Override
    public Financiamiento crear(Financiamiento financiamiento) {
        //financiamiento.setCodigo(Util.generarCodigo("financiamiento","CREAR",rep.count()));
        return rep.save(financiamiento);
    }

    @Override
    public Financiamiento actualizar(Financiamiento financiamiento) {
        return rep.save(financiamiento);
    }

    @Override
    public Financiamiento eliminar(Financiamiento financiamiento) {
        rep.deleteById(financiamiento.getId());
        return financiamiento;
    }

    @Override
    public Optional<Financiamiento> obtener(Financiamiento financiamiento) {
        return rep.findById(financiamiento.getId());
    }

    @Override
    public List<Financiamiento> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }
}
