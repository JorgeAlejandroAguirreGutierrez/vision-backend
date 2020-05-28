package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.IOrigenIngresoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IOrigenIngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrigenIngresoService implements IOrigenIngresoService {
    @Autowired
    private IOrigenIngresoRepository rep;
    @Override
    public OrigenIngreso crear(OrigenIngreso origen_ingreso) {
        //origen_ingreso.setCodigo(Util.generarCodigo("origen_ingreso","CREAR",rep.count()));
        return rep.save(origen_ingreso);
    }

    @Override
    public OrigenIngreso actualizar(OrigenIngreso origen_ingreso) {
        return rep.save(origen_ingreso);
    }

    @Override
    public OrigenIngreso eliminar(OrigenIngreso origen_ingreso) {
        rep.deleteById(origen_ingreso.getId());
        return origen_ingreso;
    }

    @Override
    public Optional<OrigenIngreso> obtener(OrigenIngreso origen_ingreso) {
        return rep.findById(origen_ingreso.getId());
    }

    @Override
    public List<OrigenIngreso> consultar() {
        return rep.findAll();
    }
}
