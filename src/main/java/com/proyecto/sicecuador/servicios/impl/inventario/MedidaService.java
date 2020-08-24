package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.inventario.Impuesto;
import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.repositorios.interf.inventario.IMedidaRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class MedidaService implements IMedidaService {
    @Autowired
    private IMedidaRepository rep;
    @Override
    public Medida crear(Medida medida) {
        return rep.save(medida);
    }

    @Override
    public Medida actualizar(Medida medida) {
        return rep.save(medida);
    }

    @Override
    public Medida eliminar(Medida medida) {
        rep.deleteById(medida.getId());
        return medida;
    }

    @Override
    public Optional<Medida> obtener(Medida medida) {
        return rep.findById(medida.getId());
    }

    @Override
    public List<Medida> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Medida> medidas=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal,5);
            for (List<String> datos: info) {
                Medida medida = new Medida(datos);
                medidas.add(medida);
            }
            rep.saveAll(medidas);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
