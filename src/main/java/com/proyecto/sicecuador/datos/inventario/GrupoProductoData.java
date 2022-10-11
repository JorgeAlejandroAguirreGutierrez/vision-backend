package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.modelos.contabilidad.AfectacionContable;
import com.proyecto.sicecuador.modelos.contabilidad.MovimientoContable;
import com.proyecto.sicecuador.modelos.inventario.CategoriaProducto;
import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import com.proyecto.sicecuador.repositorios.inventario.IGrupoProductoRepository;
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
@Order(30)
@Profile({"dev","prod"})
public class GrupoProductoData implements ApplicationRunner {
    @Autowired
    private IGrupoProductoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<GrupoProducto> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<GrupoProducto> grupos_productos = new ArrayList<>();
            grupos_productos.add(new GrupoProducto("GPR011907000001", "HOGAR", "PRISMA", "ELECTRODOMESTICO", "TELEVISOR", "SONY","30'","ACTIVO",new CategoriaProducto(1), new MovimientoContable(1)));
            grupos_productos.add(new GrupoProducto("GPR011907000002", "FAMILIA", "PLATA", "ELECTRODOMESTICO", "NEVERA", "BROTHER", "ZERO FUSION","ACTIVO", new CategoriaProducto(1), new MovimientoContable(1)));
            grupos_productos.add(new GrupoProducto("GPR011907000003", "FAMILIA", "BLANCA", "TECNOLOGIA", "CELULAR", "LG","PLUS","ACTIVO", new CategoriaProducto(1), new MovimientoContable(1)));
            grupos_productos.add(new GrupoProducto("GPR011907000004", "FAMILIA", "GRIS", "MUEBLE", "SILLA", "INDURAMA","ECO DELUXE","ACTIVO", new CategoriaProducto(1), new MovimientoContable(1)));
            grupos_productos.add(new GrupoProducto("GPR011907000005", "FAMILIA", "PRISMA", "TECNOLOGIA", "COMPUTADOR", "LG", "INTEL","ACTIVO", new CategoriaProducto(1), new MovimientoContable(1)));
            grupos_productos.add(new GrupoProducto("GPR011907000006", "HOGAR", "PLATA", "MUEBLE", "MESA", "GRAM HELL", "PLUS","ACTIVO", new CategoriaProducto(1), new MovimientoContable(1)));
            grupos_productos.add(new GrupoProducto("GPR011907000007", "HOGAR", "PLATA", "DEPORTE","BICICLETA", "ICOM","ECO DELUXE","ACTIVO", new CategoriaProducto(1), new MovimientoContable(1)));
            grupos_productos.add(new GrupoProducto("GPR011907000008", "HOGAR", "PLATA", "ELECTRODOMESTICO","REFRIGERADORA", "ARMANI", "PLUS","ACTIVO", new CategoriaProducto(1), new MovimientoContable(1)));
            grupos_productos.add(new GrupoProducto("GPR011907000009", "HOGAR", "PLATA", "ELECTRODOMESTICO", "SECADORA", "INDURAMA", "RIZEN","ACTIVO", new CategoriaProducto(1), new MovimientoContable(1)));
            grupos_productos.add(new GrupoProducto("GPR011907000010", "HOGAR", "PLATA", "VEHICULO","CARRO", "ICOM","DE SERIE","ACTIVO", new CategoriaProducto(1), new MovimientoContable(1)));
            rep.saveAll(grupos_productos);
        }
    }
}
