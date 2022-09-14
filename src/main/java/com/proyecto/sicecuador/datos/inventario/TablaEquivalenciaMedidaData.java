package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.modelos.inventario.EquivalenciaMedida;
import com.proyecto.sicecuador.repositorios.inventario.ITablaEquivalenciaMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(40)
@Profile({"dev","prod"})
public class TablaEquivalenciaMedidaData implements ApplicationRunner {
    @Autowired
    private ITablaEquivalenciaMedidaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<EquivalenciaMedida> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<EquivalenciaMedida> tablas = new ArrayList<>();
            //LIBRA
            tablas.add(new EquivalenciaMedida("TEM011909000001", new Medida(1), new Medida(1), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000002", new Medida(1), new Medida(2), 0.454));
            tablas.add(new EquivalenciaMedida("TEM011909000003", new Medida(1), new Medida(3), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000004", new Medida(1), new Medida(4), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000005", new Medida(1), new Medida(5), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000006", new Medida(1), new Medida(6), 16));
            tablas.add(new EquivalenciaMedida("TEM011909000007", new Medida(1), new Medida(7), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000008", new Medida(1), new Medida(8), 453.592));
            tablas.add(new EquivalenciaMedida("TEM011909000009", new Medida(1), new Medida(9), 0.031));
            tablas.add(new EquivalenciaMedida("TEM011909000010", new Medida(1), new Medida(10), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000011", new Medida(1), new Medida(11), 0.001));
            tablas.add(new EquivalenciaMedida("TEM011909000012", new Medida(1), new Medida(12), 0));
            //KILOGRAMO
            tablas.add(new EquivalenciaMedida("TEM011909000001", new Medida(2), new Medida(1), 2.204));
            tablas.add(new EquivalenciaMedida("TEM011909000002", new Medida(2), new Medida(2), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000003", new Medida(2), new Medida(3), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000004", new Medida(2), new Medida(4), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000005", new Medida(2), new Medida(5), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000006", new Medida(2), new Medida(6), 35.273));
            tablas.add(new EquivalenciaMedida("TEM011909000007", new Medida(2), new Medida(7), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000008", new Medida(2), new Medida(8), 1000));
            tablas.add(new EquivalenciaMedida("TEM011909000009", new Medida(2), new Medida(9), 0.068));
            tablas.add(new EquivalenciaMedida("TEM011909000010", new Medida(2), new Medida(10), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000011", new Medida(2), new Medida(11), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000012", new Medida(2), new Medida(12), 1000000));
            //QUINTAL US
            tablas.add(new EquivalenciaMedida("TEM011909000001", new Medida(3), new Medida(1), 220.462));
            tablas.add(new EquivalenciaMedida("TEM011909000002", new Medida(3), new Medida(2), 100));
            tablas.add(new EquivalenciaMedida("TEM011909000003", new Medida(3), new Medida(3), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000004", new Medida(3), new Medida(4), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000005", new Medida(3), new Medida(5), 15.747));
            tablas.add(new EquivalenciaMedida("TEM011909000006", new Medida(3), new Medida(6), 3527.4));
            tablas.add(new EquivalenciaMedida("TEM011909000007", new Medida(3), new Medida(7), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000008", new Medida(3), new Medida(8), 100000));
            tablas.add(new EquivalenciaMedida("TEM011909000009", new Medida(3), new Medida(9), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000010", new Medida(3), new Medida(10), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000011", new Medida(3), new Medida(11), 0.1));
            tablas.add(new EquivalenciaMedida("TEM011909000012", new Medida(3), new Medida(12), 0));
            //QUINTAL UK
            tablas.add(new EquivalenciaMedida("TEM011909000001", new Medida(4), new Medida(1), 112));
            tablas.add(new EquivalenciaMedida("TEM011909000002", new Medida(4), new Medida(2), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000003", new Medida(4), new Medida(3), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000004", new Medida(4), new Medida(4), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000005", new Medida(4), new Medida(5), 8));
            tablas.add(new EquivalenciaMedida("TEM011909000006", new Medida(4), new Medida(6), 1792));
            tablas.add(new EquivalenciaMedida("TEM011909000007", new Medida(4), new Medida(7), 784000));
            tablas.add(new EquivalenciaMedida("TEM011909000008", new Medida(4), new Medida(8), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000009", new Medida(4), new Medida(9), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000010", new Medida(4), new Medida(10), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000011", new Medida(4), new Medida(11), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000012", new Medida(4), new Medida(12), 0));
            //STONE
            tablas.add(new EquivalenciaMedida("TEM011909000001", new Medida(5), new Medida(1), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000002", new Medida(5), new Medida(2), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000003", new Medida(5), new Medida(3), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000004", new Medida(5), new Medida(4), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000005", new Medida(5), new Medida(5), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000006", new Medida(5), new Medida(6), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000007", new Medida(5), new Medida(7), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000008", new Medida(5), new Medida(8), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000009", new Medida(5), new Medida(9), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000010", new Medida(5), new Medida(10), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000011", new Medida(5), new Medida(11), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000012", new Medida(5), new Medida(12), 0));
            //ONZA
            tablas.add(new EquivalenciaMedida("TEM011909000001", new Medida(6), new Medida(1), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000002", new Medida(6), new Medida(2), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000003", new Medida(6), new Medida(3), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000004", new Medida(6), new Medida(4), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000005", new Medida(6), new Medida(5), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000006", new Medida(6), new Medida(6), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000007", new Medida(6), new Medida(7), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000008", new Medida(6), new Medida(8), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000009", new Medida(6), new Medida(9), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000010", new Medida(6), new Medida(10), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000011", new Medida(6), new Medida(11), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000012", new Medida(6), new Medida(12), 0));
            //GRANO
            tablas.add(new EquivalenciaMedida("TEM011909000001", new Medida(7), new Medida(1), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000002", new Medida(7), new Medida(2), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000003", new Medida(7), new Medida(3), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000004", new Medida(7), new Medida(4), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000005", new Medida(7), new Medida(5), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000006", new Medida(7), new Medida(6), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000007", new Medida(7), new Medida(7), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000008", new Medida(7), new Medida(8), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000009", new Medida(7), new Medida(9), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000010", new Medida(7), new Medida(10), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000011", new Medida(7), new Medida(11), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000012", new Medida(7), new Medida(12), 0));
            //GRAMO
            tablas.add(new EquivalenciaMedida("TEM011909000001", new Medida(8), new Medida(1), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000002", new Medida(8), new Medida(2), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000003", new Medida(8), new Medida(3), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000004", new Medida(8), new Medida(4), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000005", new Medida(8), new Medida(5), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000006", new Medida(8), new Medida(6), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000007", new Medida(8), new Medida(7), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000008", new Medida(8), new Medida(8), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000009", new Medida(8), new Medida(9), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000010", new Medida(8), new Medida(10), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000011", new Medida(8), new Medida(11), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000012", new Medida(8), new Medida(12), 0));
            //SLUG
            tablas.add(new EquivalenciaMedida("TEM011909000001", new Medida(9), new Medida(1), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000002", new Medida(9), new Medida(2), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000003", new Medida(9), new Medida(3), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000004", new Medida(9), new Medida(4), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000005", new Medida(9), new Medida(5), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000006", new Medida(9), new Medida(6), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000007", new Medida(9), new Medida(7), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000008", new Medida(9), new Medida(8), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000009", new Medida(9), new Medida(9), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000010", new Medida(9), new Medida(10), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000011", new Medida(9), new Medida(11), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000012", new Medida(9), new Medida(12), 0));
            //TONELADA
            tablas.add(new EquivalenciaMedida("TEM011909000001", new Medida(10), new Medida(1), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000002", new Medida(10), new Medida(2), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000003", new Medida(10), new Medida(3), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000004", new Medida(10), new Medida(4), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000005", new Medida(10), new Medida(5), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000006", new Medida(10), new Medida(6), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000007", new Medida(10), new Medida(7), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000008", new Medida(10), new Medida(8), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000009", new Medida(10), new Medida(9), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000010", new Medida(10), new Medida(10), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000011", new Medida(10), new Medida(11), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000012", new Medida(10), new Medida(12), 0));
            //TONELADA LARGA
            tablas.add(new EquivalenciaMedida("TEM011909000001", new Medida(11), new Medida(1), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000002", new Medida(11), new Medida(2), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000003", new Medida(11), new Medida(3), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000004", new Medida(11), new Medida(4), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000005", new Medida(11), new Medida(5), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000006", new Medida(11), new Medida(6), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000007", new Medida(11), new Medida(7), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000008", new Medida(11), new Medida(8), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000009", new Medida(11), new Medida(9), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000010", new Medida(11), new Medida(10), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000011", new Medida(11), new Medida(11), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000012", new Medida(11), new Medida(12), 0));
            //MILIGRAMO
            tablas.add(new EquivalenciaMedida("TEM011909000001", new Medida(12), new Medida(1), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000002", new Medida(12), new Medida(2), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000003", new Medida(12), new Medida(3), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000004", new Medida(12), new Medida(4), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000005", new Medida(12), new Medida(5), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000006", new Medida(12), new Medida(6), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000007", new Medida(12), new Medida(7), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000008", new Medida(12), new Medida(8), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000009", new Medida(12), new Medida(9), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000010", new Medida(12), new Medida(10), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000011", new Medida(12), new Medida(11), 0));
            tablas.add(new EquivalenciaMedida("TEM011909000012", new Medida(12), new Medida(12), 0));
            //UNIDAD
            tablas.add(new EquivalenciaMedida("TEM011909000001", new Medida(13), new Medida(13), 1));
            tablas.add(new EquivalenciaMedida("TEM011909000002", new Medida(13), new Medida(14), 0.0834));
            tablas.add(new EquivalenciaMedida("TEM011909000003", new Medida(13), new Medida(15), 0.002));
            tablas.add(new EquivalenciaMedida("TEM011909000004", new Medida(13), new Medida(16), 0.1667));
            tablas.add(new EquivalenciaMedida("TEM011909000005", new Medida(13), new Medida(17), 0.0834));
            
            rep.saveAll(tablas);
        }
    }
}
