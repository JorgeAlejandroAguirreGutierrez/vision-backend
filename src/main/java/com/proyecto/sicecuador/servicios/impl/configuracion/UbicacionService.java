package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IUbicacionRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.IUbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UbicacionService implements IUbicacionService {
    @Autowired
    private IUbicacionRepository rep;
    @Override
    public Ubicacion crear(Ubicacion ubicacion) {
        return rep.save(ubicacion);
    }

    @Override
    public Ubicacion actualizar(Ubicacion ubicacion) {
        return rep.save(ubicacion);
    }

    @Override
    public Ubicacion eliminar(Ubicacion ubicacion) {
        rep.deleteById(ubicacion.getId());
        return ubicacion;
    }

    @Override
    public Optional<Ubicacion> obtener(Ubicacion ubicacion) {
        return rep.findById(ubicacion.getId());
    }

    @Override
    public List<Ubicacion> consultar() {
        return rep.findAll();
    }

    @Override
    public List<Ubicacion> consultarProvincias() {
        List<String> provincias=rep.findProvincias();
        List<Ubicacion> ubicaciones=new ArrayList<>();
        for (String provincia: provincias) {
            Ubicacion ubicacion=new Ubicacion();
            ubicacion.setProvincia(provincia);
            ubicaciones.add(ubicacion);
        }
        return ubicaciones;
    }

    @Override
    public List<Ubicacion> consultarCantones(Ubicacion ubicacion) {
        List<String> cantones=rep.findCantones(ubicacion.getProvincia());
        List<Ubicacion> ubicaciones=new ArrayList<>();
        for (String canton: cantones) {
            Ubicacion _ubicacion=new Ubicacion();
            _ubicacion.setCanton(canton);
            ubicaciones.add(_ubicacion);
        }
        return ubicaciones;
    }

    @Override
    public List<Ubicacion> consultarParroquias(Ubicacion ubicacion) {
        return rep.findParroquias(ubicacion.getCanton());
    }
    @Override
    public Optional<Ubicacion> obtenerUbicacionID(Ubicacion ubicacion) {
        return rep.findByProvinciaAndCantonAndParroquia(ubicacion.getProvincia(),
                ubicacion.getCanton(), ubicacion.getParroquia());
    }
}
