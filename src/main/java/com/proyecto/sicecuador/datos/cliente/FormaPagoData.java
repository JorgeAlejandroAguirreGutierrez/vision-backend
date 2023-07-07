package com.proyecto.vision.datos.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.cliente.FormaPago;
import com.proyecto.vision.repositorios.cliente.IFormaPagoRepository;
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
public class FormaPagoData implements ApplicationRunner {
    @Autowired
    private IFormaPagoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<FormaPago> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<FormaPago> formasPagos = new ArrayList<>();
            formasPagos.add(new FormaPago("FPA202301000001", "01", "SIN UTILIZACION DEL SISTEMA FINANCIERO", "EFECTIVO", Constantes.activo));
            formasPagos.add(new FormaPago("FPA202301000002", "15", "COMPENSACIÓN DE DEUDAS", "COMPENSACIÓN", Constantes.activo));
            formasPagos.add(new FormaPago("FPA202301000003", "16", "TARJETA DE DÉBITO", "TAR. DÉBITO", Constantes.activo));
            formasPagos.add(new FormaPago("FPA202301000004", "17", "DINERO ELECTRÓNICO", "E-COMMERCE", Constantes.activo));
            formasPagos.add(new FormaPago("FPA202301000005", "18", "TARJETA PREPAGO", "PREPAGO", Constantes.activo));
            formasPagos.add(new FormaPago("FPA202301000006", "19", "TARJETA DE CRÉDITO", "TAR. CRÉDITO", Constantes.activo));
            formasPagos.add(new FormaPago("FPA202301000007", "20", "OTROS CON UTILIZACIÓN DEL SISTEMA FINANCIERO", "CHEQUE", Constantes.activo));
            formasPagos.add(new FormaPago("FPA202301000008", "20", "OTROS CON UTILIZACIÓN DEL SISTEMA FINANCIERO", "DEPÓSITO", Constantes.activo));
            formasPagos.add(new FormaPago("FPA202301000009", "20", "OTROS CON UTILIZACIÓN DEL SISTEMA FINANCIERO", "TRANSFERENCIA", Constantes.activo));
            formasPagos.add(new FormaPago("FPA202301000010", "20", "OTROS CON UTILIZACIÓN DEL SISTEMA FINANCIERO", "RETENCIÓN", Constantes.activo));
            formasPagos.add(new FormaPago("FPA202301000011", "21", "ENDOSO DE TÍTULOS", "ENDOSO", Constantes.activo));
            rep.saveAll(formasPagos);
        }
    }
}
