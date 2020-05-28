package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.inventario.BodegaProducto;
import com.proyecto.sicecuador.modelos.inventario.Caracteristica;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.repositorios.interf.inventario.ICaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(46)
public class CaracteristicaData implements ApplicationRunner {
    @Autowired
    private ICaracteristicaRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Caracteristica> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Caracteristica> caracteristicas = new ArrayList<>();
            caracteristicas.add(new Caracteristica("CAR011909000001", 10, "ARROZ PARA COCINAR", "BLANCO", "ROA", "2019", null, new BodegaProducto(1)));
            caracteristicas.add(new Caracteristica("CAR011909000002", 30, "ARROZ PARA SANCOCHO", "BLANCO", "ROA", "2019", null, new BodegaProducto(2)));
            caracteristicas.add(new Caracteristica("CAR011909000003", 40, "HUEVOS PARA COCINAR", "BLANCO", "AVIGALLINA", "2019", null, new BodegaProducto(3)));
            caracteristicas.add(new Caracteristica("CAR011909000004", 50, "GALLETAS PARA COMER", "CAFE", "CRIGALLE", "2019", null, new BodegaProducto(4)));
            caracteristicas.add(new Caracteristica("CAR011909000005", 60, "CAFE PARA HACER TINTO", "CAFE", "BUEN DIA", "2019", null, new BodegaProducto(5)));
            caracteristicas.add(new Caracteristica("CAR011909000006", 70, "FRIJOLES PARA HACER ALMUERZO", "ROJO", "FRIJOLISTOS", "2019", null, new BodegaProducto(6)));
            caracteristicas.add(new Caracteristica("CAR011909000007", 20, "FRIJOLES PARA HACER ALMUERZO", "ROJO", "FRIJOLISTOS", "2019", null, new BodegaProducto(7)));
            caracteristicas.add(new Caracteristica("CAR011909000008", 30, "FRIJOLES PARA HACER ALMUERZO", "ROJO", "FRIJOLISTOS", "2019", null, new BodegaProducto(8)));
            caracteristicas.add(new Caracteristica("CAR011909000009", 1, "COMPUTADOR ACER", "", "ACER", "2019", "000000009", new BodegaProducto(9)));
            caracteristicas.add(new Caracteristica("CAR011909000010", 1, "COMPUTADOR SONY", "", "SONY", "2019", "000000010", new BodegaProducto(10)));
            caracteristicas.add(new Caracteristica("CAR011909000011", 1, "TELEVISOR LG", "", "LG", "2019", "000000011", new BodegaProducto(11)));
            caracteristicas.add(new Caracteristica("CAR011909000012", 1, "TELEVISOR SAMSUNG", "", "SAMSUNG", "2019", "000000012", new BodegaProducto(12)));
            caracteristicas.add(new Caracteristica("CAR011909000013", 1, "TELEVISOR SONY", "", "SONY", "2019", "000000013", new BodegaProducto(13)));
            caracteristicas.add(new Caracteristica("CAR011909000014", 1, "CELULAR LG", "", "LG", "2019", "000000014", new BodegaProducto(14)));
            caracteristicas.add(new Caracteristica("CAR011909000015", 1, "CELULAR SAMSUNG", "", "SAMSUNG", "2019", "000000015", new BodegaProducto(15)));
            caracteristicas.add(new Caracteristica("CAR011909000016", 1, "CELULAR MOTOROLA", "", "MOTOROLA", "2019", "000000016", new BodegaProducto(16)));
            caracteristicas.add(new Caracteristica("CAR011909000017", 1, "CELULAR HUAWEI", "", "HUAWEI", "2019", "000000017", new BodegaProducto(17)));
            //SERVICIOS
            caracteristicas.add(new Caracteristica("CAR011909000017", 100, "SERVICIO", "", "", "", null, new BodegaProducto(18)));
            caracteristicas.add(new Caracteristica("CAR011909000018",100, "SERVICIO", "", "", "", null, new BodegaProducto(19)));
            caracteristicas.add(new Caracteristica("CAR011909000019",100, "SERVICIO", "", "", "", null, new BodegaProducto(20)));
            caracteristicas.add(new Caracteristica("CAR011909000020",100, "SERVICIO", "", "", "",  null, new BodegaProducto(21)));
            caracteristicas.add(new Caracteristica("CAR011909000021",100, "SERVICIO", "", "", "", null, new BodegaProducto(22)));
            caracteristicas.add(new Caracteristica("CAR011909000022",100, "SERVICIO", "", "", "", null, new BodegaProducto(23)));
            caracteristicas.add(new Caracteristica("CAR011909000023",100, "SERVICIO", "", "", "", null, new BodegaProducto(24)));
            caracteristicas.add(new Caracteristica("CAR011909000024",100, "SERVICIO", "", "", "", null, new BodegaProducto(25)));
            rep.saveAll(caracteristicas);
        }
    }
}
