package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.inventario.TipoOperacion;
import com.proyecto.sicecuador.repositorios.inventario.ITipoOperacionRepository;
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
            tiposOperaciones.add(new TipoOperacion("TOP011907000001", "SALDO INICIAL","SALDO INICIAL", Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000002", "VENTA","VENTA",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000003", "COMPRA","COMPRA",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000004", "CONSIGNACIÓN RECIBIDA","CONSIG. RECIBIDA",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000005", "CONSIGNACIÓN ENTREGADA","CONSIG. ENTREGADA",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000006", "DEVOLUCIÓN RECIBIDA","DEV. RECIBIDA",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000007", "DEVOLUCIÓN ENTREGADA","DEV. ENTREGADA",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000008", "PROMOCIÓN","PROMO",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000009", "PREMIO","PREMIO",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000010", "DONACIÓN","DONACIÓN",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000011", "SALIDA A PRODUCCIÓN","PRODUCCIÓN",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000012", "TRANSFERENCIA ENTRE ALMACENES","TRANS. ALMACEN",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000013", "TRANSFERENCIA ENTRE BODEGAS","TRANS. BODEGA",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000014", "RETIRO","RETIRO",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000014", "MERMAS","MERMAS",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000014", "DESMEDROS","DESMEDROS",Constantes.activo));
            tiposOperaciones.add(new TipoOperacion("TOP011907000014", "DESTRUCCIÓN","DESTRUCCIÓN",Constantes.activo));



            rep.saveAll(tiposOperaciones);
        }
    }
}
