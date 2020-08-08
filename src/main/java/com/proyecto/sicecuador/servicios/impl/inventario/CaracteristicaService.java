package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.inventario.Caracteristica;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.repositorios.interf.inventario.ICaracteristicaRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.ICaracteristicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class CaracteristicaService implements ICaracteristicaService {
    @Autowired
    private ICaracteristicaRepository rep;
    @Override
    public Caracteristica crear(Caracteristica caracteristica) {
        return rep.save(caracteristica);
    }

    @Override
    public Caracteristica actualizar(Caracteristica caracteristica) {
        return rep.save(caracteristica);
    }

    @Override
    public Caracteristica eliminar(Caracteristica caracteristica) {
        rep.deleteById(caracteristica.getId());
        return caracteristica;
    }

    @Override
    public Optional<Caracteristica> obtener(Caracteristica caracteristica) {
        return rep.findById(caracteristica.getId());
    }

    @Override
    public List<Caracteristica> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }

    @Override
    public List<Caracteristica> consultarBienExistencias(Producto _producto) {
        List <Caracteristica> caracteristicas = rep.consultarBienExistencias(_producto.getId());
        return caracteristicas;
    }

    @Override
    public List<Caracteristica> consultarBienExistenciasBodega(Producto _producto, Bodega _bodega) {
        List <Caracteristica> caracteristicas = rep.consultarBienExistenciasBodega(_producto.getId(), _bodega.getId());
        return caracteristicas;
    }
}
