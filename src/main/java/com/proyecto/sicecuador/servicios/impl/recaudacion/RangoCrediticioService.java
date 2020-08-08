package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.RangoCrediticio;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IRangoCrediticioRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IRangoCrediticioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class RangoCrediticioService implements IRangoCrediticioService {
    @Autowired
    private IRangoCrediticioRepository rep;
    @Override
    public RangoCrediticio crear(RangoCrediticio operador_tarjeta) {
        return rep.save(operador_tarjeta);
    }

    @Override
    public RangoCrediticio actualizar(RangoCrediticio operador_tarjeta) {
        return rep.save(operador_tarjeta);
    }

    @Override
    public RangoCrediticio eliminar(RangoCrediticio operador_tarjeta) {
        rep.deleteById(operador_tarjeta.getId());
        return operador_tarjeta;
    }

    @Override
    public Optional<RangoCrediticio> obtener(RangoCrediticio operador_tarjeta) {
        return rep.findById(operador_tarjeta.getId());
    }

    @Override
    public List<RangoCrediticio> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }

    @Override
    public Optional<RangoCrediticio> obtenerSaldo(double saldo) {
        return rep.findSaldoRangoCrediticio(saldo);
    }

}
