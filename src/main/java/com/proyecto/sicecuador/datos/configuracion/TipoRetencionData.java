package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.Constantes;
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
            List<TipoRetencion> tiposRetenciones = new ArrayList<>();
            tiposRetenciones.add(new TipoRetencion("TRE00000001", "IVA", "BIEN", "721", "RETENCION IVA 10% BIENES", 10, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000002", "IVA", "SERVICIO", "723", "RETENCION IVA 20% SERVICIOS", 20, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000003", "IVA", "BIEN", "725", "RETENCION IVA 30% BIENES", 30, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000004", "IVA", "BIEN", "727", "RETENCION IVA 50% EXPORTADORES", 50, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000005", "IVA", "SERVICIO", "729", "RETENCION IVA 70% SERVICIOS", 70, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000006", "IVA", "SERVICIO", "731", "RETENCION IVA 100% SERVICIOS PROFESIONALES", 100, Constantes.activo));
            
            tiposRetenciones.add(new TipoRetencion("TRE00000007", "RENTA", "SERVICIO", "303", "HONORARIOS PROFESIONALES Y DEMAS PAGOS POR SERVICIOS RELACIONADOS CON EL TITULO PROFESIONAL", 10, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000008", "RENTA", "SERVICIO", "304", "SERVICIOS PREDOMINA EL INTELECTO NO RELACIONADOS CON EL TITULO PROFESIONAL", 5, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000009", "RENTA", "BIEN", "304A", "COMISIONES Y DEMAS PAGOS POR Comisiones y demás pagos por servicios predomina intelecto no relacionados con el título profesional", 8, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000010", "RENTA", "BIEN", "304B", "PAGOS A NOTARIOS Y REGISTRADOS DE LA PROPIEDAD MERCANTIL POR SUS ACTIVIDADES EJERCIDAS COMO TALES", 8, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000011", "RENTA", "BIEN", "304C", "PAGOS A DEPORTISTAS, ENTRENADORES, ARBITROS, MIEMBROS DEL CUERPO TECNICO POR SUS ACTIVIDADES EJERCIDAS COMO TALES", 8, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000012", "RENTA", "BIEN", "304D", "PAGOS A ARTISTAS POR SUS ACTIVIDADES EJERCIDAS COMO TALES", 8, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000013", "RENTA", "SERVICIO", "307", "SERVICIOS PREDOMINA LA MANO DE OBRA", 2, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000014", "RENTA", "SERVICIO", "308", "UTILIZACION O APROVECHAMIENTO DE LA IMAGEN O RENOMBRE", 10, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000015", "RENTA", "SERVICIO", "309", "SERVICIOS PRESTADOS POR MEDIOS DE COMUNICACION Y AGENCIAS DE PUBLICIDAD", 1.75, Constantes.activo));
            tiposRetenciones.add(new TipoRetencion("TRE00000016", "RENTA", "SERVICIO", "310", "SERVICIO DE TRANSPORTE PRIVADO DE PASAJEROS O TRANSPORTE PUBLICO O PRIVADO DE CARGA", 1, Constantes.activo));
            rep.saveAll(tiposRetenciones);
        }
    }
}
