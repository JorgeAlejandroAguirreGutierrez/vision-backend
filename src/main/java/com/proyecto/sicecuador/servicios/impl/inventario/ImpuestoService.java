package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.inventario.Caracteristica;
import com.proyecto.sicecuador.modelos.inventario.Impuesto;
import com.proyecto.sicecuador.repositorios.interf.inventario.IImpuestoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IImpuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ImpuestoService implements IImpuestoService {
    @Autowired
    private IImpuestoRepository rep;
    @Override
    public Impuesto crear(Impuesto impuesto) {
        return rep.save(impuesto);
    }

    @Override
    public Impuesto actualizar(Impuesto impuesto) {
        return rep.save(impuesto);
    }

    @Override
    public Impuesto eliminar(Impuesto impuesto) {
        rep.deleteById(impuesto.getId());
        return impuesto;
    }

    @Override
    public Optional<Impuesto> obtener(Impuesto impuesto) {
        return rep.findById(impuesto.getId());
    }

    @Override
    public List<Impuesto> consultar() {
        return rep.findAll();
    }

    @Override
    public Optional<Impuesto> obtenerImpuestoPorcentaje(Impuesto impuesto) {
        return rep.findByPorcentaje(impuesto.getPorcentaje());
    }
    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Impuesto> impuestos=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal,3);
            for (List<String> datos: info) {
                Impuesto impuesto = new Impuesto(datos);
                impuestos.add(impuesto);
            }
            if(impuestos.isEmpty()){
                return false;
            }
            rep.saveAll(impuestos);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
