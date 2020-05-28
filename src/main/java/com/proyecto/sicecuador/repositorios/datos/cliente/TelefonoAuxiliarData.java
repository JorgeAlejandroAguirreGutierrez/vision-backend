package com.proyecto.sicecuador.repositorios.datos.cliente;

import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.cliente.TelefonoAuxiliar;
import com.proyecto.sicecuador.repositorios.interf.cliente.ITelefonoAuxiliarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
@Order(22)
public class TelefonoAuxiliarData implements ApplicationRunner {
    @Autowired
    private ITelefonoAuxiliarRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TelefonoAuxiliar> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TelefonoAuxiliar> telefonos = new ArrayList<>();
            telefonos.add(new TelefonoAuxiliar("TEA011908000001", "032964121", new Auxiliar(1)));
            telefonos.add(new TelefonoAuxiliar("TEA011908000002", "032964122", new Auxiliar(1)));
            telefonos.add(new TelefonoAuxiliar("TEA011909000003", "032964123", new Auxiliar(2)));
            telefonos.add(new TelefonoAuxiliar("TEA011909000004", "032964124", new Auxiliar(3)));
            telefonos.add(new TelefonoAuxiliar("TEA011909000005", "032964125", new Auxiliar(4)));
            rep.saveAll(telefonos);
        }
    }
}
