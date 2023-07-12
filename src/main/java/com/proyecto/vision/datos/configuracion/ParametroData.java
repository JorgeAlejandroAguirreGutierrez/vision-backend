package com.proyecto.vision.datos.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.Parametro;
import com.proyecto.vision.repositorios.configuracion.IParametroRepository;
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
@Order(16)
@Profile({"dev","prod"})
public class ParametroData implements ApplicationRunner {
    @Autowired
    private IParametroRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Parametro> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Parametro> parametros = new ArrayList<>();
            parametros.add(new Parametro("PAR202305000001", "LOGO", "vision.png", "", "LOG", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000002", "TIPOS USUARIOS", "VENDEDOR", "", "V", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000003", "TIPOS USUARIOS", "CAJERO", "", "C", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000004", "TIPOS MEDIDAS", "KILOGRAMO", "", "KG", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000005", "CHEQUE", "A LA VISTA", "", "CHV", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000006", "CHEQUE", "POSFECHADO", "", "CHP", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000007", "TIPO TRANSACCION", "DIRECTA", "", "DIR", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000008", "TIPO TRANSACCION", "VIA BCE", "", "BCE", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000009", "TARJETA", "CREDITO", "", "TCR", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000010", "TARJETA", "DEBITO", "", "TDB", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000011", "PERIODICIDAD", "MENSUAL", "", "M", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000012", "PERIODICIDAD", "QUINCENAL", "", "Q", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000013", "PERIODICIDAD", "TRIMESTRAL", "", "T", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000014", "PERIODICIDAD", "ANUAL", "", "A", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000015", "AMORTIZACION", "ALEMANA", "", "AL", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000016", "AMORTIZACION", "FRANCESA", "", "FR", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000017", "FORMA COBRO", "CONTADO", "", "C", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000018", "FORMA COBRO", "DIFERIDO", "", "D", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000019", "TIPO COMPROBANTE RETENCION", "FACTURA", "", "FAC", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000020", "TIPOS MEDIDAS", "UNIDAD", "", "U", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000021", "TIPOS MEDIDAS", "METRO", "", "M", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000022", "FORMA TIEMPO", "DIA", "", "D", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000023", "FORMA TIEMPO", "MES", "", "M", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000024", "FORMA TIEMPO", "ANIO", "", "A", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000025", "PERIODO_MENSUAL", "12", "", "PME", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000026", "PERIODO_QUINCENAL", "24", "", "PQU", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000027", "PERIODO_TRIMESTRAL", "4", "", "PTR", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000028", "PERIODO_ANUAL", "1", "", "PAN", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000029", "MENSUAL", "30", "", "MEN", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000030", "QUINCENAL", "15", "", "QUI", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000031", "TRIMESTRAL", "90", "", "TRI", Constantes.estadoActivo));
            parametros.add(new Parametro("PAR202305000032", "ANUAL", "365", "", "ANU", Constantes.estadoActivo));

            rep.saveAll(parametros);
        }
    }
}
