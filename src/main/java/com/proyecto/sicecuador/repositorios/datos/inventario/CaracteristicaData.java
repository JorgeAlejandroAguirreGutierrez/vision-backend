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
            caracteristicas.add(new Caracteristica("CAR011909000001", "ARROZ ROA", "BLANCO", "ROA", "2019", "000000001", new BodegaProducto(1), null));
            caracteristicas.add(new Caracteristica("CAR011909000002", "HUEVOS AAA", "BLANCO", "ROA", "2019", "000000002", new BodegaProducto(2), null));
            caracteristicas.add(new Caracteristica("CAR011909000003", "CALLETAS NOEL", "BLANCO", "AVIGALLINA", "2019", "000000003", new BodegaProducto(3), null));
            caracteristicas.add(new Caracteristica("CAR011909000004", "CAFE BUEN DIA", "CAFE", "CRIGALLE", "2019", "000000004", new BodegaProducto(4), null));
            caracteristicas.add(new Caracteristica("CAR011909000005", "FRIJOLES ROJOS", "ROJO", "BUEN DIA", "2019", "000000005", new BodegaProducto(5), null));
            caracteristicas.add(new Caracteristica("CAR011909000006", "DELL 14 PULGADAS", "ROJO", "FRIJOLISTOS", "2019", "000000006", new BodegaProducto(6), null));
            caracteristicas.add(new Caracteristica("CAR011909000007", "SAMSUNG HD 50", "ROJO", "FRIJOLISTOS", "2019", "000000007", new BodegaProducto(7), null));
            caracteristicas.add(new Caracteristica("CAR011909000008", "VENTILADOR TODO HOGAR", "ROJO", "FRIJOLISTOS", "2019", "000000008", new BodegaProducto(8), null));
            caracteristicas.add(new Caracteristica("CAR011909000009", "ESCRITORIO GERENCIAL", "", "ACER", "2019", "000000009B", new BodegaProducto(9), null));
            caracteristicas.add(new Caracteristica("CAR011909000010", "MESA RIMAX", "", "SONY", "2019", "000000010B", new BodegaProducto(10), null));
            caracteristicas.add(new Caracteristica("CAR011909000011", "SILLA ERGONOMICA", "", "LG", "2019", "000000011B", new BodegaProducto(11), null));
            caracteristicas.add(new Caracteristica("CAR011909000012", "PS4 SLIM", "", "SAMSUNG", "2019", "000000012B", new BodegaProducto(12), null));
            caracteristicas.add(new Caracteristica("CAR011909000013",  "CELULAR SONY", "", "SONY", "2019", "000000013B", new BodegaProducto(13), null));
            caracteristicas.add(new Caracteristica("CAR011909000014", "CELULAR LG", "", "LG", "2019", "000000014B", new BodegaProducto(14), null));
            caracteristicas.add(new Caracteristica("CAR011909000015", "CELULAR SAMSUNG", "", "SAMSUNG", "2019", "000000015B", new BodegaProducto(15), null));
            caracteristicas.add(new Caracteristica("CAR011909000016","CELULAR MOTOROLA", "", "MOTOROLA", "2019", "000000016B", new BodegaProducto(16), null));
            caracteristicas.add(new Caracteristica("CAR011909000017", "CELULAR HUAWEI", "", "HUAWEI", "2019", "000000017B", new BodegaProducto(17), null));
            //SERVICIOS
            caracteristicas.add(new Caracteristica("CAR011909000017",  "SERVICIO", "", "", "", "000000018", new BodegaProducto(18), null));
            caracteristicas.add(new Caracteristica("CAR011909000018", "SERVICIO", "", "", "", "000000019", new BodegaProducto(19), null));
            caracteristicas.add(new Caracteristica("CAR011909000019", "SERVICIO", "", "", "", "000000020", new BodegaProducto(20), null));
            caracteristicas.add(new Caracteristica("CAR011909000020", "SERVICIO", "", "", "",  "000000021", new BodegaProducto(21), null));
            caracteristicas.add(new Caracteristica("CAR011909000021", "SERVICIO", "", "", "", "000000022", new BodegaProducto(22), null));
            caracteristicas.add(new Caracteristica("CAR011909000022", "SERVICIO", "", "", "", "000000023", new BodegaProducto(23), null));
            caracteristicas.add(new Caracteristica("CAR011909000023", "SERVICIO", "", "", "", "000000024", new BodegaProducto(24), null));
            caracteristicas.add(new Caracteristica("CAR011909000024", "SERVICIO", "", "", "", "000000025", new BodegaProducto(25), null));
            rep.saveAll(caracteristicas);
        }
    }
}
