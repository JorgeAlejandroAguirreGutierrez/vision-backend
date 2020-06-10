package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.Amortizacion;
import com.proyecto.sicecuador.modelos.recaudacion.Banco;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IAmortizacionRepository;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IBancoRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IAmortizacionService;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IBancoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class AmortizacionService implements IAmortizacionService {
    @Autowired
    private IAmortizacionRepository rep;
    @Override
    public Amortizacion crear(Amortizacion amortizacion) {
        return rep.save(amortizacion);
    }

    @Override
    public Amortizacion actualizar(Amortizacion amortizacion) {
        return rep.save(amortizacion);
    }

    @Override
    public Amortizacion eliminar(Amortizacion amortizacion) {
        rep.deleteById(amortizacion.getId());
        return amortizacion;
    }

    @Override
    public Optional<Amortizacion> obtener(Amortizacion banco) {
        return rep.findById(banco.getId());
    }

    @Override
    public List<Amortizacion> consultar() {
        return rep.findAll();
    }
}
