package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.cliente.OrigenIngreso;
import com.proyecto.sicecuador.modelos.cliente.PlazoCredito;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.IOrigenIngresoRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.IPlazoCreditoRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IPlazoCreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class PlazoCreditoService implements IPlazoCreditoService {
    @Autowired
    private IPlazoCreditoRepository rep;
    @Override
    public PlazoCredito crear(PlazoCredito plazo_credito) {
        return rep.save(plazo_credito);
    }

    @Override
    public PlazoCredito actualizar(PlazoCredito plazo_credito) {
        return rep.save(plazo_credito);
    }

    @Override
    public PlazoCredito eliminar(PlazoCredito plazo_credito) {
        rep.deleteById(plazo_credito.getId());
        return plazo_credito;
    }

    @Override
    public Optional<PlazoCredito> obtener(PlazoCredito plazo_credito) {
        return rep.findById(plazo_credito.getId());
    }

    @Override
    public List<PlazoCredito> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<PlazoCredito> plazos_creditos=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal, 14);
            for (List<String> datos: info) {
                PlazoCredito plazo_credito = new PlazoCredito(datos);
                plazos_creditos.add(plazo_credito);
            }
            if(plazos_creditos.isEmpty()){
                return false;
            }
            rep.saveAll(plazos_creditos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
