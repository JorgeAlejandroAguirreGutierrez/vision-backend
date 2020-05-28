package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.FormaPago;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.IFormaPagoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IFormaPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FormaPagoService implements IFormaPagoService {
    @Autowired
    private IFormaPagoRepository rep;
    @Override
    public FormaPago crear(FormaPago forma_pago) {
        //forma_pago.setCodigo(Util.generarCodigo("forma_pago","CREAR",rep.count()));
        return rep.save(forma_pago);
    }

    @Override
    public FormaPago actualizar(FormaPago forma_pago) {
        return rep.save(forma_pago);
    }

    @Override
    public FormaPago eliminar(FormaPago forma_pago) {
        rep.deleteById(forma_pago.getId());
        return forma_pago;
    }

    @Override
    public Optional<FormaPago> obtener(FormaPago forma_pago) {
        return rep.findById(forma_pago.getId());
    }

    @Override
    public List<FormaPago> consultar() {
        return rep.findAll();
    }
}
