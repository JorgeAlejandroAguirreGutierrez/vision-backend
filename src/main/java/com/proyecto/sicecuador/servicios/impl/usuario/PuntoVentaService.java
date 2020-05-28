package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.modelos.usuario.PuntoVenta;
import com.proyecto.sicecuador.repositorios.interf.usuario.IEstablecimientoRepository;
import com.proyecto.sicecuador.repositorios.interf.usuario.IPuntoVentaRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IPuntoVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PuntoVentaService implements IPuntoVentaService {
    @Autowired
    private IPuntoVentaRepository rep;
    @Override
    public PuntoVenta crear(PuntoVenta punto_venta) {
        return rep.save(punto_venta);
    }

    @Override
    public PuntoVenta actualizar(PuntoVenta punto_venta) {
        return rep.save(punto_venta);
    }

    @Override
    public PuntoVenta eliminar(PuntoVenta punto_venta) {
        rep.deleteById(punto_venta.getId());
        return punto_venta;
    }

    @Override
    public Optional<PuntoVenta> obtener(PuntoVenta punto_venta) {
        return rep.findById(punto_venta.getId());
    }

    @Override
    public List<PuntoVenta> consultar() {
        return rep.findAll();
    }
}
