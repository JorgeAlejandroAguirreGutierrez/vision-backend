package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.CuentaPropia;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.ICuentaPropiaRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.ICuentaPropiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CuentaPropiaService implements ICuentaPropiaService {
    @Autowired
    private ICuentaPropiaRepository rep;
    @Override
    public CuentaPropia crear(CuentaPropia cuenta_propia) {
        return rep.save(cuenta_propia);
    }

    @Override
    public CuentaPropia actualizar(CuentaPropia cuenta_propia) {
        return rep.save(cuenta_propia);
    }

    @Override
    public CuentaPropia eliminar(CuentaPropia cuenta_propia) {
        rep.deleteById(cuenta_propia.getId());
        return cuenta_propia;
    }

    @Override
    public Optional<CuentaPropia> obtener(CuentaPropia cuenta_propia) {
        return rep.findById(cuenta_propia.getId());
    }

    @Override
    public List<CuentaPropia> consultar() {
        return rep.findAll();
    }
}
