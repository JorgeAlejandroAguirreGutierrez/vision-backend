package com.proyecto.sicecuador.repositorios.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.Bodega;
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
            caracteristicas.add(new Caracteristica("CAR011909000001", "ARROZ ROA", "BLANCO", "ROA", "2019", "000000001", new Producto(1), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000001-1", "ARROZ ROA-1", "BLANCO", "ROA", "2019", "000000001-1", new Producto(1), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000001-2", "ARROZ ROA-2", "BLANCO", "ROA", "2019", "000000001-2", new Producto(1), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000001-3", "ARROZ ROA-3", "BLANCO", "ROA", "2019", "000000001-3", new Producto(1), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000002", "HUEVOS AAA", "BLANCO", "ROA", "2019", "000000002", new Producto(2), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000002-1", "HUEVOS AAA-1", "BLANCO", "ROA", "2019", "000000002-1", new Producto(2), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000002-2", "HUEVOS AAA-2", "BLANCO", "ROA", "2019", "000000002-2", new Producto(2), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000002-3", "HUEVOS AAA-3", "BLANCO", "ROA", "2019", "000000002-3", new Producto(2), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000002-4", "HUEVOS AAA-4", "BLANCO", "ROA", "2019", "000000002-4", new Producto(2), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000003", "CALLETAS NOEL", "BLANCO", "AVIGALLINA", "2019", "000000003", new Producto(3), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000004", "CAFE BUEN DIA", "CAFE", "CRIGALLE", "2019", "000000004", new Producto(4), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000005", "FRIJOLES ROJOS", "ROJO", "BUEN DIA", "2019", "000000005", new Producto(5), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000006", "DELL 14 PULGADAS", "ROJO", "DELL", "2019", "000000006", new Producto(6), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000006-1", "DELL 14 PULGADAS-1", "ROJO", "DELL", "2019", "000000006-1", new Producto(6), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000006-2", "DELL 14 PULGADAS-2", "ROJO", "DELL", "2019", "000000006-2", new Producto(6), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000006-3", "DELL 14 PULGADAS-3", "ROJO", "DELL", "2019", "000000006-3", new Producto(6), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000006-4", "DELL 14 PULGADAS-4", "ROJO", "DELL", "2019", "000000006-4", new Producto(6), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000006-5", "DELL 14 PULGADAS-5", "ROJO", "DELL", "2019", "000000006-5", new Producto(6), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000006-6", "DELL 14 PULGADAS-6", "ROJO", "DELL", "2019", "000000006-6", new Producto(6), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000006-7", "DELL 14 PULGADAS-7", "ROJO", "DELL", "2019", "000000006-7", new Producto(6), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000006-8", "DELL 14 PULGADAS-8", "ROJO", "DELL", "2019", "000000006-8", new Producto(6), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000006-9", "DELL 14 PULGADAS-9", "ROJO", "DELL", "2019", "000000006-9", new Producto(6), new Bodega(2)));
                caracteristicas.add(new Caracteristica("CAR011909000007", "SAMSUNG HD 50", "ROJO", "SAMSUNG", "2019", "000000007", new Producto(7), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000007-1", "SAMSUNG HD 50-1", "ROJO", "SAMSUNG", "2019", "000000007-1", new Producto(7), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000007-2", "SAMSUNG HD 50-2", "ROJO", "SAMSUNG", "2019", "000000007-2", new Producto(7), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000007-3", "SAMSUNG HD 50-3", "ROJO", "SAMSUNG", "2019", "000000007-3", new Producto(7), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000007-4", "SAMSUNG HD 50-4", "ROJO", "SAMSUNG", "2019", "000000007-4", new Producto(7), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000007-5", "SAMSUNG HD 50-5", "ROJO", "SAMSUNG", "2019", "000000007-5", new Producto(7), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000008", "VENTILADOR TODO HOGAR", "ROJO", "FRIJOLISTOS", "2019", "000000008", new Producto(8), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000009", "ESCRITORIO GERENCIAL", "", "ACER", "2019", "000000009B", new Producto(9), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000009-1", "ESCRITORIO GERENCIAL-1", "", "ACER", "2019", "000000009B-1", new Producto(1), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000009-1", "ESCRITORIO GERENCIAL-2", "", "ACER", "2019", "000000009B-2", new Producto(1), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000009-1", "ESCRITORIO GERENCIAL-3", "", "ACER", "2019", "000000009B-3", new Producto(1), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000009-1", "ESCRITORIO GERENCIAL-4", "", "ACER", "2019", "000000009B-4", new Producto(1), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000009-1", "ESCRITORIO GERENCIAL-5", "", "ACER", "2019", "000000009B-5", new Producto(1), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000009-1", "ESCRITORIO GERENCIAL-6", "", "ACER", "2019", "000000009B-6", new Producto(1), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000009-1", "ESCRITORIO GERENCIAL-7", "", "ACER", "2019", "000000009B-7", new Producto(1), new Bodega(2)));
            caracteristicas.add(new Caracteristica("CAR011909000010", "MESA RIMAX", "", "SONY", "2019", "000000010B", new Producto(10),new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000011", "SILLA ERGONOMICA", "", "LG", "2019", "000000011B", new Producto(11), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000012", "PS4 SLIM", "", "SAMSUNG", "2019", "000000012B", new Producto(12),new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013",  "CELULAR SONY", "", "SONY", "2019", "000000013B", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-1",  "CELULAR SONY-1", "", "SONY", "2019", "000000013B-1", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-2",  "CELULAR SONY-2", "", "SONY", "2019", "000000013B-2", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-3",  "CELULAR SONY-3", "", "SONY", "2019", "000000013B-3", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-4",  "CELULAR SONY-4", "", "SONY", "2019", "000000013B-4", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-5",  "CELULAR SONY-5", "", "SONY", "2019", "000000013B-5", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-6",  "CELULAR SONY-6", "", "SONY", "2019", "000000013B-6", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-7",  "CELULAR SONY-7", "", "SONY", "2019", "000000013B-7", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-8",  "CELULAR SONY-8", "", "SONY", "2019", "000000013B-8", new Producto(13), new Bodega(3)));

            caracteristicas.add(new Caracteristica("CAR011909000013", "CELULAR LG", "", "LG", "2019", "000000014B", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013", "CELULAR SAMSUNG", "", "SAMSUNG", "2019", "000000015B", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013","CELULAR MOTOROLA", "", "MOTOROLA", "2019", "000000016B", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013", "CELULAR HUAWEI", "", "HUAWEI", "2019", "000000017B", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-1", "CELULAR HUAWEI-1", "", "HUAWEI", "2019", "000000017B-1", new Producto(13),new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-2", "CELULAR HUAWEI-2", "", "HUAWEI", "2019", "000000017B-2", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-3", "CELULAR HUAWEI-3", "", "HUAWEI", "2019", "000000017B-3", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-4", "CELULAR HUAWEI-4", "", "HUAWEI", "2019", "000000017B-4", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-5", "CELULAR HUAWEI-5", "", "HUAWEI", "2019", "000000017B-5", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-6", "CELULAR HUAWEI-6", "", "HUAWEI", "2019", "000000017B-6", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000013-7", "CELULAR HUAWEI-7", "", "HUAWEI", "2019", "000000017B-7", new Producto(13), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000014-1", "AURICULARES SONY-1", "", "SONY", "2019", "000000018B-1", new Producto(14), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000014-2", "AURICULARES SONY-2", "", "SONY", "2019", "000000018B-2", new Producto(14), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000014-3", "AURICULARES SONY-3", "", "SONY", "2019", "000000018B-3", new Producto(14), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000015-1", "ZAPATOS BRAHMA-1", "", "BRAHMA", "2019", "000000019B-1", new Producto(15), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000015-2", "ZAPATOS BRAHMA-2", "", "BRAHMA", "2019", "000000019B-2", new Producto(15), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000016-1", "CARGADOR BLASTER", "", "BLASTER", "2019", "000000019B-1",  new Producto(16), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000016-2", "CARGADOR ASUS", "", "ASUS", "2019", "000000019B-2", new Producto(16), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000017-3", "ZAPATOS BRAHMA-3", "", "BRAHMA", "2019", "000000019B-3", new Producto(17), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000017-1", "ZAPATOS BRAHMA-3", "", "BRAHMA", "2019", "000000019B-3", new Producto(17), new Bodega(3)));
            caracteristicas.add(new Caracteristica("CAR011909000017-2", "ZAPATOS BRAHMA-3", "", "BRAHMA", "2019", "000000019B-3", new Producto(17), new Bodega(3)));
            //SERVICIOS
            caracteristicas.add(new Caracteristica("CAR011909000018",  "SERVICIO", "", "", "", "000000018",new Producto(15), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000019", "SERVICIO", "", "", "", "000000019", new Producto(16), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000020", "SERVICIO", "", "", "", "000000020", new Producto(17), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000021", "SERVICIO", "", "", "",  "000000021", new Producto(18),new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000022", "SERVICIO", "", "", "", "000000022", new Producto(19), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000023", "SERVICIO", "", "", "", "000000023", new Producto(20), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000024", "SERVICIO", "", "", "", "000000024", new Producto(21), new Bodega(1)));
            caracteristicas.add(new Caracteristica("CAR011909000025", "SERVICIO", "", "", "", "000000025", new Producto(22), new Bodega(1)));
            rep.saveAll(caracteristicas);
        }
    }
}
