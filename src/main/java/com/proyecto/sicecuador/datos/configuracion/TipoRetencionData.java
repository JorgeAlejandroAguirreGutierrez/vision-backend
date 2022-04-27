package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.repositorios.configuracion.ITipoRetencionRepository;
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
@Order(17)
@Profile({"dev","prod"})
public class TipoRetencionData implements ApplicationRunner {
    @Autowired
    private ITipoRetencionRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TipoRetencion> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TipoRetencion> tipos_retenciones = new ArrayList<>();
            tipos_retenciones.add(new TipoRetencion("RETENCION_IVA1", "IVA", "BIEN", "103", "3", "IVA_100%", 100));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IVA2", "IVA", "SERVICIO", "104", "4", "IVA_15%", 15));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IR1", "RENTA", "BIEN", "303", null, "IR_10%", 10));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IR2", "RENTA", "SERVICIO", "304", null, "IR_5%", 5));
            rep.saveAll(tipos_retenciones);
        }
    }
}
