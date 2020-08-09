package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.recaudacion.CuentaPropia;
import com.proyecto.sicecuador.modelos.recaudacion.FranquiciaTarjeta;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IFranquiciaTarjetaRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IFranquiciaTarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<FranquiciaTarjeta> franquicias_tarjetas=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal);
            for (List<String> datos: info) {
                FranquiciaTarjeta franquicia_tarjeta = new FranquiciaTarjeta(datos);
                franquicias_tarjetas.add(franquicia_tarjeta);
            }
            rep.saveAll(franquicias_tarjetas);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
