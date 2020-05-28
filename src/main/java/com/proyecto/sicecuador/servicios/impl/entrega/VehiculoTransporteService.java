package com.proyecto.sicecuador.servicios.impl.entrega;

import com.proyecto.sicecuador.modelos.entrega.VehiculoTransporte;
import com.proyecto.sicecuador.repositorios.interf.entrega.IVehiculoTransporteRepository;
import com.proyecto.sicecuador.servicios.interf.entrega.IVehiculoTransporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class VehiculoTransporteService implements IVehiculoTransporteService {
    @Autowired
    private IVehiculoTransporteRepository rep;
    @Override
    public VehiculoTransporte crear(VehiculoTransporte vehiculo_transporte) {
        return rep.save(vehiculo_transporte);
    }

    @Override
    public VehiculoTransporte actualizar(VehiculoTransporte vehiculo_transporte) {
        return rep.save(vehiculo_transporte);
    }

    @Override
    public VehiculoTransporte eliminar(VehiculoTransporte vehiculo_transporte) {
        rep.deleteById(vehiculo_transporte.getId());
        return vehiculo_transporte;
    }

    @Override
    public Optional<VehiculoTransporte> obtener(VehiculoTransporte vehiculo_transporte) {
        return rep.findById(vehiculo_transporte.getId());
    }

    @Override
    public List<VehiculoTransporte> consultar() {
        return rep.findAll();
    }
}
