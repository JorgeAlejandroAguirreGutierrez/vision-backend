package com.proyecto.sicecuador.repositorios.datos.recaudacion;

import com.proyecto.sicecuador.modelos.recaudacion.FranquiciaTarjeta;
import com.proyecto.sicecuador.modelos.recaudacion.ModeloTabla;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IFranquiciaTarjetaRepository;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IModeloTablaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(25)
public class ModeloTablaData implements ApplicationRunner {
    @Autowired
    private IModeloTablaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<ModeloTabla> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<ModeloTabla> modelos_tablas = new ArrayList<>();
            modelos_tablas.add(new ModeloTabla("MT1", "FRANCESA", 0));
            modelos_tablas.add(new ModeloTabla("MT2", "ALEMANA", 0));
            rep.saveAll(modelos_tablas);
        }
    }
}
