package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.TipoPago;
import com.proyecto.sicecuador.repositorios.interf.cliente.ITipoContribuyenteRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.ITipoPagoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ITipoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TipoPagoService implements ITipoPagoService {
    @Autowired
    private ITipoPagoRepository rep;
    @Override
    public TipoPago crear(TipoPago tipo_pago) {
        return rep.save(tipo_pago);
    }

    @Override
    public TipoPago actualizar(TipoPago tipo_pago) {
        return rep.save(tipo_pago);
    }

    @Override
    public TipoPago eliminar(TipoPago tipo_pago) {
        rep.deleteById(tipo_pago.getId());
        return tipo_pago;
    }

    @Override
    public Optional<TipoPago> obtener(TipoPago tipo_pago) {return rep.findById(tipo_pago.getId());
    }

    @Override
    public List<TipoPago> consultar() {
        return rep.findAll();
    }
}
