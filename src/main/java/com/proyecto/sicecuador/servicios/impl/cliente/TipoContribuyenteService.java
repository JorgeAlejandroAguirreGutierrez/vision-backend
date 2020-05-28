package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.TipoContribuyente;
import com.proyecto.sicecuador.repositorios.interf.cliente.IOrigenIngresoRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.ITipoContribuyenteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ITipoContribuyenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TipoContribuyenteService implements ITipoContribuyenteService {
    @Autowired
    private ITipoContribuyenteRepository rep;
    @Override
    public TipoContribuyente crear(TipoContribuyente tipo_contribuyente) {
        return rep.save(tipo_contribuyente);
    }

    @Override
    public TipoContribuyente actualizar(TipoContribuyente tipo_contribuyente) {
        return rep.save(tipo_contribuyente);
    }

    @Override
    public TipoContribuyente eliminar(TipoContribuyente tipo_contribuyente) {
        rep.deleteById(tipo_contribuyente.getId());
        return tipo_contribuyente;
    }

    @Override
    public Optional<TipoContribuyente> obtener(TipoContribuyente tipo_contribuyente) {
        return rep.findById(tipo_contribuyente.getId());
    }

    @Override
    public List<TipoContribuyente> consultar() {
        return rep.findAll();
    }
}
