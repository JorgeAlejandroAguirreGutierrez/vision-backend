package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.cliente.EstadoCivil;
import com.proyecto.sicecuador.modelos.cliente.Financiamiento;
import com.proyecto.sicecuador.repositorios.interf.cliente.IEstadoCivilRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IEstadoCivilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class EstadoCivilService implements IEstadoCivilService {
    @Autowired
    private IEstadoCivilRepository rep;
    @Override
    public EstadoCivil crear(EstadoCivil estado_civil) {
        return rep.save(estado_civil);
    }

    @Override
    public EstadoCivil actualizar(EstadoCivil estado_civil) {
        return rep.save(estado_civil);
    }

    @Override
    public EstadoCivil eliminar(EstadoCivil estado_civil) {
        rep.deleteById(estado_civil.getId());
        return estado_civil;
    }

    @Override
    public Optional<EstadoCivil> obtener(EstadoCivil estado_civil) {
        return rep.findById(estado_civil.getId());
    }

    @Override
    public List<EstadoCivil> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<EstadoCivil> estados_civiles=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal,8);
            for (List<String> datos: info) {
                EstadoCivil estado_civil = new EstadoCivil(datos);
                estados_civiles.add(estado_civil);
            }
            rep.saveAll(estados_civiles);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
