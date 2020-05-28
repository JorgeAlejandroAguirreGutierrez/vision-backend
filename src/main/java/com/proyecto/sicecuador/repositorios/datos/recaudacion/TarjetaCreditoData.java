package com.proyecto.sicecuador.repositorios.datos.recaudacion;

import com.proyecto.sicecuador.modelos.configuracion.TipoRetencion;
import com.proyecto.sicecuador.modelos.recaudacion.*;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IRetencionVentaDetalleRepository;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.ITarjetaCreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(32)
public class TarjetaCreditoData implements ApplicationRunner {
    @Autowired
    private ITarjetaCreditoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Optional<TarjetaCredito> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TarjetaCredito> tarjetas_creditos = new ArrayList<>();
            tarjetas_creditos.add(new TarjetaCredito("RTC000001", false, true, "0502685966", "JORGE HIDALGO", "12314566", 50.00, new Tarjeta(1), new OperadorTarjeta(1)));
            tarjetas_creditos.add(new TarjetaCredito("RTC000002", true, true, "0502685966", "JORGE HIDALGO", "12314566", 30.00, new Tarjeta(2), new OperadorTarjeta(1)));
            tarjetas_creditos.add(new TarjetaCredito("RTC000003", false, false, "0601234567", "MARIO DELGADO", "12314566", 90.00, new Tarjeta(1), new OperadorTarjeta(2)));
            tarjetas_creditos.add(new TarjetaCredito("RTC000004", true, false, "0601234567", "MARIO DELGADO", "12314566", 70.00, new Tarjeta(2), new OperadorTarjeta(2)));
            rep.saveAll(tarjetas_creditos);
        }*/
    }
}
