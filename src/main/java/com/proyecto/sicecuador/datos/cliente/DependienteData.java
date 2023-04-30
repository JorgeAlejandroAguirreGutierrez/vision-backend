package com.proyecto.sicecuador.datos.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.cliente.*;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.repositorios.cliente.IDependienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Order(21)
@Profile({"dev","prod"})
public class DependienteData implements ApplicationRunner {
    @Autowired
    private IDependienteRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Dependiente> ant=rep.findById((long) 1);
        if (!ant.isPresent()){
            List<Dependiente> dependientes=new ArrayList<>();

            List<TelefonoDependiente> telefonos1 = new ArrayList<>();
            telefonos1.add(new TelefonoDependiente("TED011908000001", "03-2964-121", new Dependiente(1)));
            telefonos1.add(new TelefonoDependiente("TED011908000002", "03-2964-122", new Dependiente(1)));
            List<CelularDependiente> celulares1 = new ArrayList<>();
            celulares1.add(new CelularDependiente("CED011908000001", "09-8765-4322", new Dependiente(1)));
            celulares1.add(new CelularDependiente("CED011908000002", "09-8123-4563", new Dependiente(1)));
            List<CorreoDependiente> correos1 = new ArrayList<>();
            correos1.add(new CorreoDependiente("COD011908000001", "dependiente1_1@gmail.com", new Dependiente(1)));
            correos1.add(new CorreoDependiente("COD011908000002", "dependiente1_2@gmail.com", new Dependiente(1)));
            dependientes.add(new Dependiente("DEP011907000001", "DEPENDIENTE 1 DE CLIENTE A", "CARRERA 4 # 4-4", -1.6719601146175827, -78.65041698970857, Constantes.activo, new Ubicacion(1), new Cliente(1), telefonos1, celulares1, correos1));

            List<TelefonoDependiente> telefonos2 = new ArrayList<>();
            telefonos2.add(new TelefonoDependiente("TEA011908000001", "03-2964-122", new Dependiente(2)));
            telefonos2.add(new TelefonoDependiente("TEA011908000002", "03-2964-132", new Dependiente(2)));
            List<CelularDependiente> celulares2 = new ArrayList<>();
            celulares2.add(new CelularDependiente("CEA011908000001", "09-8765-4303", new Dependiente(2)));
            celulares2.add(new CelularDependiente("CEA011908000002", "09-8123-4564", new Dependiente(2)));
            List<CorreoDependiente> correos2 = new ArrayList<>();
            correos2.add(new CorreoDependiente("COA011908000001", "dependiente2_1@gmail.com", new Dependiente(2)));
            correos2.add(new CorreoDependiente("COA011908000002", "dependiente2_2@gmail.com", new Dependiente(2)));
            dependientes.add(new Dependiente("DEP011908000002", "DEPENDIENTE 2 DE CLIENTE A", "CARRERA 5 # 5-5", -1.6719601146175827, -78.65041698970857, Constantes.activo, new Ubicacion(1), new Cliente(1), telefonos2, celulares2, correos2));

            List<TelefonoDependiente> telefonos3 = new ArrayList<>();
            telefonos3.add(new TelefonoDependiente("TEA011908000001", "03-2964-123", new Dependiente(3)));
            telefonos3.add(new TelefonoDependiente("TEA011908000002", "03-2964-133", new Dependiente(3)));
            List<CelularDependiente> celulares3 = new ArrayList<>();
            celulares3.add(new CelularDependiente("CEA011908000001", "09-8765-4333", new Dependiente(3)));
            celulares3.add(new CelularDependiente("CEA011908000002", "09-8123-3563", new Dependiente(3)));
            List<CorreoDependiente> correos3 = new ArrayList<>();
            correos3.add(new CorreoDependiente("COA011908000001", "dependiente3_1@gmail.com", new Dependiente(3)));
            correos3.add(new CorreoDependiente("COA011908000002", "dependiente3_2@gmail.com", new Dependiente(3)));
            dependientes.add(new Dependiente("DEP011908000003", "DEPENDIENTE 1 DE CLIENTE B", "CARRERA 6 # 6-6", -1.6719601146175827, -78.65041698970857, Constantes.activo, new Ubicacion(1), new Cliente(2), telefonos3, celulares3, correos3));

            List<TelefonoDependiente> telefonos4 = new ArrayList<>();
            telefonos4.add(new TelefonoDependiente("TEA011908000001", "03-2964-124", new Dependiente(4)));
            telefonos4.add(new TelefonoDependiente("TEA011908000002", "03-2964-144", new Dependiente(4)));
            List<CelularDependiente> celulares4 = new ArrayList<>();
            celulares4.add(new CelularDependiente("CEA011908000001", "09-8765-4344", new Dependiente(4)));
            celulares4.add(new CelularDependiente("CEA011908000002", "09-8123-3544", new Dependiente(4)));
            List<CorreoDependiente> correos4 = new ArrayList<>();
            correos4.add(new CorreoDependiente("COA011908000001", "dependiente4_1@gmail.com", new Dependiente(4)));
            correos4.add(new CorreoDependiente("COA011908000002", "dependiente4_2@gmail.com", new Dependiente(4)));
            dependientes.add(new Dependiente("DEP011908000004", "DEPENDIENTE 2 DE CLIENTE B", "CARRERA 7 # 7-7", -1.6719601146175827, -78.65041698970857, Constantes.activo, new Ubicacion(1), new Cliente(2), telefonos4, celulares4, correos4));
            rep.saveAll(dependientes);
        }
    }
}
