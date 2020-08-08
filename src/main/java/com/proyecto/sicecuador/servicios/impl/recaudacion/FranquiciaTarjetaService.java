package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.FranquiciaTarjeta;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IFranquiciaTarjetaRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IFranquiciaTarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class FranquiciaTarjetaService implements IFranquiciaTarjetaService {
    @Autowired
    private IFranquiciaTarjetaRepository rep;
    @Override
    public FranquiciaTarjeta crear(FranquiciaTarjeta franquicia_tarjeta) {
        return rep.save(franquicia_tarjeta);
    }

    @Override
    public FranquiciaTarjeta actualizar(FranquiciaTarjeta franquicia_tarjeta) {
        return rep.save(franquicia_tarjeta);
    }

    @Override
    public FranquiciaTarjeta eliminar(FranquiciaTarjeta franquicia_tarjeta) {
        rep.deleteById(franquicia_tarjeta.getId());
        return franquicia_tarjeta;
    }

    @Override
    public Optional<FranquiciaTarjeta> obtener(FranquiciaTarjeta franquicia_tarjeta) {
        return rep.findById(franquicia_tarjeta.getId());
    }

    @Override
    public List<FranquiciaTarjeta> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile file) {
        return false;
    }

}
