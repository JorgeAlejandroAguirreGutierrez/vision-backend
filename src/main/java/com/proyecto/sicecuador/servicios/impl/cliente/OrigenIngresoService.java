package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.cliente.Genero;
import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.IOrigenIngresoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IOrigenIngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class OrigenIngresoService implements IOrigenIngresoService {
    @Autowired
    private IOrigenIngresoRepository rep;
    @Override
    public OrigenIngreso crear(OrigenIngreso origen_ingreso) {
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

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<OrigenIngreso> origenes_ingresos=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal, 13);
            for (List<String> datos: info) {
                OrigenIngreso origen_ingreso = new OrigenIngreso(datos);
                origenes_ingresos.add(origen_ingreso);
            }
            rep.saveAll(origenes_ingresos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
