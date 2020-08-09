package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.entrega.VehiculoTransporte;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.repositorios.interf.inventario.IBodegaRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IBodegaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BodegaService implements IBodegaService {
    @Autowired
    private IBodegaRepository rep;
    @Override
    public Bodega crear(Bodega bodega) {
        return rep.save(bodega);
    }

    @Override
    public Bodega actualizar(Bodega bodega) {
        return rep.save(bodega);
    }

    @Override
    public Bodega eliminar(Bodega bodega) {
        rep.deleteById(bodega.getId());
        return bodega;
    }

    @Override
    public Optional<Bodega> obtener(Bodega bodega) {
        return rep.findById(bodega.getId());
    }

    @Override
    public List<Bodega> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Bodega> bodegas=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal);
            for (List<String> datos: info) {
                Bodega bodega = new Bodega(datos);
                bodegas.add(bodega);
            }
            rep.saveAll(bodegas);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
