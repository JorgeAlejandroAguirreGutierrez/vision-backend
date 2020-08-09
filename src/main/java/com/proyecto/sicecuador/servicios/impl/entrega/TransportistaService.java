package com.proyecto.sicecuador.servicios.impl.entrega;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.entrega.GuiaRemision;
import com.proyecto.sicecuador.modelos.entrega.Transportista;
import com.proyecto.sicecuador.repositorios.interf.entrega.IGuiaRemisionRepository;
import com.proyecto.sicecuador.repositorios.interf.entrega.ITransportistaRepository;
import com.proyecto.sicecuador.servicios.interf.entrega.ITransportistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TransportistaService implements ITransportistaService {
    @Autowired
    private ITransportistaRepository rep;
    @Override
    public Transportista crear(Transportista transportista) {
        return rep.save(transportista);
    }

    @Override
    public Transportista actualizar(Transportista transportista) {
        return rep.save(transportista);
    }

    @Override
    public Transportista eliminar(Transportista transportista) {
        rep.deleteById(transportista.getId());
        return transportista;
    }

    @Override
    public Optional<Transportista> obtener(Transportista transportista) {
        return rep.findById(transportista.getId());
    }

    @Override
    public List<Transportista> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Transportista> transportistas=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal);
            for (List<String> datos: info) {
                Transportista transportista = new Transportista(datos);
                transportistas.add(transportista);
            }
            rep.saveAll(transportistas);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
