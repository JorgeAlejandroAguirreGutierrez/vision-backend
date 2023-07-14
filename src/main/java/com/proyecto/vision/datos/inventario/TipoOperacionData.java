package com.proyecto.vision.datos.inventario;

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
            tiposOperaciones.add(new TipoOperacion("TOP011907000001", "SALDO INICIAL","SALDO INICIAL", Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000002", "VENTA","VENTA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000003", "COMPRA","COMPRA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000004", "CONSIGNACIÓN RECIBIDA","CONSIG. RECIBIDA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000005", "CONSIGNACIÓN ENTREGADA","CONSIG. ENTREGADA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000006", "DEVOLUCIÓN EN COMPRAS","DEV. COMPRA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000007", "DEVOLUCIÓN EN VENTAS","DEV. VENTA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000008", "DESCUENTO EN COMPRAS","DES. COMPRA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000009", "PROMOCIÓN","PROMO",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000010", "DONACIÓN","DONACIÓN",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000011", "SALIDA A PRODUCCIÓN","PRODUCCIÓN",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000012", "TRANSFERENCIA ENTRE ALMACENES","TRANS. ALMACEN",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000013", "TRANSFERENCIA ENTRE BODEGAS","TRANS. BODEGA",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000014", "RETIRO","RETIRO",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000015", "MERMAS","MERMAS",Constantes.estadoActivo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000016", "DESTRUCCIÓN","DESTRUCCIÓN",Constantes.estadoActivo));

            rep.saveAll(tiposOperaciones);
        }
    }
}
