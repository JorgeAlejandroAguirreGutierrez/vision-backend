package com.proyecto.vision.datos.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.repositorios.inventario.ITipoOperacionRepository;
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
@Order(33)
@Profile({"dev","prod"})
public class TipoOperacionData implements ApplicationRunner {
    @Autowired
    private ITipoOperacionRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<TipoOperacion> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<TipoOperacion> tiposOperaciones = new ArrayList<>();
            tiposOperaciones.add(new TipoOperacion("TOP202301000001", "SALDO INICIAL","SALDO INICIAL", Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000002", "VENTA","VENTA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000003", "COMPRA","COMPRA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000004", "CONSIGNACIÓN RECIBIDA","CONSIG. RECIBIDA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000005", "CONSIGNACIÓN ENTREGADA","CONSIG. ENTREGADA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000006", "DEVOLUCIÓN EN COMPRAS","DEV. COMPRA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000007", "DEVOLUCIÓN EN VENTAS","DEV. VENTA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000008", "DESCUENTO EN COMPRAS","DES. COMPRA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000009", "DESCUENTO EN VENTAS","DES. VENTA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000010", "PROMOCIÓN","PROMO",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000011", "DONACIÓN","DONACIÓN",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000012", "SALIDA A PRODUCCIÓN","PRODUCCIÓN",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000013", "TRANSFERENCIA ENTRE ALMACENES","TRANS. ALMACEN",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000014", "TRANSFERENCIA ENTRE BODEGAS","TRANS. BODEGA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000015", "RETIRO","RETIRO",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000016", "MERMAS","MERMAS",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP202301000017", "DESTRUCCIÓN","DESTRUCCIÓN",Constantes.estadoActivo));

            rep.saveAll(tiposOperaciones);
        }
    }
}
