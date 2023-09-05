package com.proyecto.vision.datos.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.Secuencial;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.modelos.usuario.Estacion;
import com.proyecto.vision.repositorios.configuracion.ISecuencialRepository;
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
@Order(20)
@Profile({"dev","prod"})
public class SecuencialData implements ApplicationRunner {
    @Autowired
    private ISecuencialRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Secuencial> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Secuencial> secuenciales = new ArrayList<>();
            secuenciales.add(new Secuencial("SEC202304000001", 1, 10, Constantes.estadoActivo, new TipoComprobante(2), new Estacion(1)));
            secuenciales.add(new Secuencial("SEC202304000002", 1, 10, Constantes.estadoActivo, new TipoComprobante(4), new Estacion(1)));
            secuenciales.add(new Secuencial("SEC202304000003", 1, 10, Constantes.estadoActivo, new TipoComprobante(5), new Estacion(1)));
            secuenciales.add(new Secuencial("SEC202304000004", 1, 10, Constantes.estadoActivo, new TipoComprobante(6), new Estacion(1)));
            secuenciales.add(new Secuencial("SEC202304000005", 1, 10, Constantes.estadoActivo, new TipoComprobante(7), new Estacion(1)));

            secuenciales.add(new Secuencial("SEC202304000006", 1, 10, Constantes.estadoActivo, new TipoComprobante(2), new Estacion(2)));
            secuenciales.add(new Secuencial("SEC202304000007", 1, 10, Constantes.estadoActivo, new TipoComprobante(4), new Estacion(2)));
            secuenciales.add(new Secuencial("SEC202304000008", 1, 10, Constantes.estadoActivo, new TipoComprobante(5), new Estacion(2)));
            secuenciales.add(new Secuencial("SEC202304000009", 1, 10, Constantes.estadoActivo, new TipoComprobante(6), new Estacion(2)));
            secuenciales.add(new Secuencial("SEC202304000010", 1, 10, Constantes.estadoActivo, new TipoComprobante(7), new Estacion(2)));

            secuenciales.add(new Secuencial("SEC202304000011", 1, 10, Constantes.estadoActivo, new TipoComprobante(2), new Estacion(3)));
            secuenciales.add(new Secuencial("SEC202304000012", 1, 10, Constantes.estadoActivo, new TipoComprobante(4), new Estacion(3)));
            secuenciales.add(new Secuencial("SEC202304000013", 1, 10, Constantes.estadoActivo, new TipoComprobante(5), new Estacion(3)));
            secuenciales.add(new Secuencial("SEC202304000014", 1, 10, Constantes.estadoActivo, new TipoComprobante(6), new Estacion(3)));
            secuenciales.add(new Secuencial("SEC202304000015", 1, 10, Constantes.estadoActivo, new TipoComprobante(7), new Estacion(3)));

            secuenciales.add(new Secuencial("SEC202304000016", 1, 10, Constantes.estadoActivo, new TipoComprobante(2), new Estacion(4)));
            secuenciales.add(new Secuencial("SEC202304000017", 1, 10, Constantes.estadoActivo, new TipoComprobante(4), new Estacion(4)));
            secuenciales.add(new Secuencial("SEC202304000018", 1, 10, Constantes.estadoActivo, new TipoComprobante(5), new Estacion(4)));
            secuenciales.add(new Secuencial("SEC202304000019", 1, 10, Constantes.estadoActivo, new TipoComprobante(6), new Estacion(4)));
            secuenciales.add(new Secuencial("SEC202304000020", 1, 10, Constantes.estadoActivo, new TipoComprobante(7), new Estacion(4)));

            secuenciales.add(new Secuencial("SEC202304000021", 1, 10, Constantes.estadoActivo, new TipoComprobante(2), new Estacion(5)));
            secuenciales.add(new Secuencial("SEC202304000022", 1, 10, Constantes.estadoActivo, new TipoComprobante(4), new Estacion(5)));
            secuenciales.add(new Secuencial("SEC202304000023", 1, 10, Constantes.estadoActivo, new TipoComprobante(5), new Estacion(5)));
            secuenciales.add(new Secuencial("SEC202304000024", 1, 10, Constantes.estadoActivo, new TipoComprobante(6), new Estacion(5)));
            secuenciales.add(new Secuencial("SEC202304000025", 1, 10, Constantes.estadoActivo, new TipoComprobante(7), new Estacion(5)));

            secuenciales.add(new Secuencial("SEC202304000026", 1, 10, Constantes.estadoActivo, new TipoComprobante(2), new Estacion(6)));
            secuenciales.add(new Secuencial("SEC202304000027", 1, 10, Constantes.estadoActivo, new TipoComprobante(4), new Estacion(6)));
            secuenciales.add(new Secuencial("SEC202304000028", 1, 10, Constantes.estadoActivo, new TipoComprobante(5), new Estacion(6)));
            secuenciales.add(new Secuencial("SEC202304000029", 1, 10, Constantes.estadoActivo, new TipoComprobante(6), new Estacion(6)));
            secuenciales.add(new Secuencial("SEC202304000030", 1, 10, Constantes.estadoActivo, new TipoComprobante(7), new Estacion(6)));

            secuenciales.add(new Secuencial("SEC202304000031", 1, 10, Constantes.estadoActivo, new TipoComprobante(2), new Estacion(7)));
            secuenciales.add(new Secuencial("SEC202304000032", 1, 10, Constantes.estadoActivo, new TipoComprobante(4), new Estacion(7)));
            secuenciales.add(new Secuencial("SEC202304000033", 1, 10, Constantes.estadoActivo, new TipoComprobante(5), new Estacion(7)));
            secuenciales.add(new Secuencial("SEC202304000034", 1, 10, Constantes.estadoActivo, new TipoComprobante(6), new Estacion(7)));
            secuenciales.add(new Secuencial("SEC202304000035", 1, 10, Constantes.estadoActivo, new TipoComprobante(7), new Estacion(7)));
            // EMPRESA 2
            secuenciales.add(new Secuencial("SEC022308000036", 1, 10, Constantes.estadoActivo, new TipoComprobante(2), new Estacion(8)));
            secuenciales.add(new Secuencial("SEC022308000037", 1, 10, Constantes.estadoActivo, new TipoComprobante(4), new Estacion(8)));
            secuenciales.add(new Secuencial("SEC022308000038", 1, 10, Constantes.estadoActivo, new TipoComprobante(5), new Estacion(8)));
            secuenciales.add(new Secuencial("SEC022308000039", 1, 10, Constantes.estadoActivo, new TipoComprobante(6), new Estacion(8)));
            secuenciales.add(new Secuencial("SEC022308000040", 1, 10, Constantes.estadoActivo, new TipoComprobante(7), new Estacion(8)));

            secuenciales.add(new Secuencial("SEC022308000041", 1, 10, Constantes.estadoActivo, new TipoComprobante(2), new Estacion(9)));
            secuenciales.add(new Secuencial("SEC022308000042", 1, 10, Constantes.estadoActivo, new TipoComprobante(4), new Estacion(9)));
            secuenciales.add(new Secuencial("SEC022308000043", 1, 10, Constantes.estadoActivo, new TipoComprobante(5), new Estacion(9)));
            secuenciales.add(new Secuencial("SEC022308000044", 1, 10, Constantes.estadoActivo, new TipoComprobante(6), new Estacion(9)));
            secuenciales.add(new Secuencial("SEC022308000045", 1, 10, Constantes.estadoActivo, new TipoComprobante(7), new Estacion(9)));
            // EMPRESA 3
            secuenciales.add(new Secuencial("SEC032309000046", 1, 10, Constantes.estadoActivo, new TipoComprobante(2), new Estacion(11)));
            secuenciales.add(new Secuencial("SEC032309000047", 1, 10, Constantes.estadoActivo, new TipoComprobante(4), new Estacion(11)));
            secuenciales.add(new Secuencial("SEC032309000048", 1, 10, Constantes.estadoActivo, new TipoComprobante(5), new Estacion(11)));
            secuenciales.add(new Secuencial("SEC032309000049", 1, 10, Constantes.estadoActivo, new TipoComprobante(6), new Estacion(11)));
            secuenciales.add(new Secuencial("SEC032309000050", 1, 10, Constantes.estadoActivo, new TipoComprobante(7), new Estacion(11)));

            rep.saveAll(secuenciales);
        }
    }
}
