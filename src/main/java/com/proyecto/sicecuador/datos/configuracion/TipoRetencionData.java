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
            tipos_retenciones.add(new TipoRetencion("RETENCION_IVA1", "IVA", "BIEN", "721", null, "Retencion Iva 10% Bienes", 10));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IVA2", "IVA", "SERVICIO", "723", null, "Retencion Iva 20% Servicios", 20));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IVA3", "IVA", "BIEN", "725", null, "Retencion Iva 30% Bienes", 30));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IVA4", "IVA", "BIEN", "727", null, "Retencion Iva 50% Exportadores", 50));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IVA5", "IVA", "SERVICIO", "729", null, "Retencion Iva 70% Servicios", 70));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IVA6", "IVA", "SERVICIO", "731", null, "Retencion Iva 100% Servicios Profesionales", 100));
            
            
            tipos_retenciones.add(new TipoRetencion("RETENCION_IR1", "RENTA", "BIEN", "303", null, "Honorarios profesionales y demás pagos por servicios relacionados con el título profesional", 10));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IR2", "RENTA", "BIEN", "304", null, "Servicios predomina el intelecto no relacionados con el título profesional", 5));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IR3", "RENTA", "BIEN", "304A", null, "Comisiones y demás pagos por servicios predomina intelecto no relacionados con el título profesional", 8));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IR4", "RENTA", "BIEN", "304B", null, "Pagos a notarios y registradores de la propiedad y mercantil por sus actividades ejercidas como tales", 8));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IR5", "RENTA", "BIEN", "304C", null, "Pagos a deportistas, entrenadores, árbitros, miembros del cuerpo técnico por sus actividades ejercidas como tales", 8));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IR6", "RENTA", "BIEN", "304D", null, "Pagos a artistas por sus actividades ejercidas como tales", 8));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IR7", "RENTA", "BIEN", "307", null, "Servicios predomina la mano de obra", 2));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IR8", "RENTA", "BIEN", "308", null, "Utilización o aprovechamiento de la imagen o renombre", 10));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IR9", "RENTA", "BIEN", "309", null, "Servicios prestados por medios de comunicación y agencias de publicidada", 1.75));
            tipos_retenciones.add(new TipoRetencion("RETENCION_IR10", "RENTA", "BIEN", "310", null, "Servicio de transporte privado de pasajeros o transporte público o privado de carga", 1));
            
            tipos_retenciones.add(new TipoRetencion("RETENCION_IR11", "RENTA", "SERVICIO", "0", null, "Renta servicio prueba", 10));
            rep.saveAll(tipos_retenciones);
        }
    }
}
