package com.proyecto.sicecuador.datos.inventario;

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
            List<GrupoProducto> gruposProductos = new ArrayList<>();
            gruposProductos.add(new GrupoProducto("GPR011907000001", "HOGAR", "ELECTRODOMESTICO", "LINEA BLANCA", "NEVERA", "LG","PUERTA SIMPLE","ACTIVO",new CategoriaProducto(1), new MovimientoContable(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000002", "HOGAR", "ELECTRODOMESTICO", "LINEA GRIS", "TELEVISOR", "SONY","SMART","ACTIVO",new CategoriaProducto(1), new MovimientoContable(2)));
            gruposProductos.add(new GrupoProducto("GPR011907000003", "HOGAR", "MUEBLES", "LINEA CAFE", "DORMITORIOS", "CAMAS", "ZERO FUSION","ACTIVO", new CategoriaProducto(1), new MovimientoContable(3)));
            gruposProductos.add(new GrupoProducto("GPR011907000004", "JUGUETERIA", "NIÑOS", "MOTORIZADOS", "CARROS", "HOT WEELLS","MINIATURA","ACTIVO", new CategoriaProducto(1), new MovimientoContable(4)));
            gruposProductos.add(new GrupoProducto("GPR011907000005", "JUGUETERIA", "NIÑAS", "MUÑECAS", "PLASTICO", "BARBIE","DREAM HOUSE","ACTIVO", new CategoriaProducto(1), new MovimientoContable(5)));
            gruposProductos.add(new GrupoProducto("GPR011907000006", "BEBIDAS", "ALCOHOLICAS", "CERVEZAS", "NACIONAL", "CLUB", "PLATINIUM","ACTIVO", new CategoriaProducto(1), new MovimientoContable(6)));
            gruposProductos.add(new GrupoProducto("GPR011907000007", "BEBIDAS", "ALCOHOLICAS", "CERVEZAS", "IMPORTADA", "HEINEKEN", "LATA","ACTIVO", new CategoriaProducto(1), new MovimientoContable(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000008", "BEBIDAS", "NO ALCOHOLICAS", "AGUA", "MINERALIZADA", "GÜITIG", "BOTELLA","ACTIVO", new CategoriaProducto(1), new MovimientoContable(2)));
            gruposProductos.add(new GrupoProducto("GPR011907000009", "ALIMENTOS", "PERECIBLES", "FRUTAS","NACIONAL", "COSTA","NO EMPAQUETADA","ACTIVO", new CategoriaProducto(1), new MovimientoContable(3)));
            gruposProductos.add(new GrupoProducto("GPR011907000010", "ALIMENTOS", "NO PERECIBLES", "LEGUMBRES","ENLATADOS", "GRANOS", "EMPAQUETADO","ACTIVO", new CategoriaProducto(1), new MovimientoContable(4)));
            gruposProductos.add(new GrupoProducto("GPR011907000011", "CONFITERIA", "SAL", "CARAMELOS", "IMPORTADO", "LA UNIVERSAL", "ENFUNDADO","ACTIVO", new CategoriaProducto(1), new MovimientoContable(5)));
            gruposProductos.add(new GrupoProducto("GPR011907000012", "CONFITERIA", "DULCE", "CHOCOLATES","NACIONAL", "FERRERO","CAJA","ACTIVO", new CategoriaProducto(1), new MovimientoContable(6)));
            gruposProductos.add(new GrupoProducto("GPR011907000013", "HOGAR", "TECNOLOGIA", "COMPUTADORAS","PORTATIL", "DELL","LATITUDE","ACTIVO", new CategoriaProducto(1), new MovimientoContable(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000014", "HOGAR", "DEPORTES", "ROPA","CAMISETAS", "FUTBOL","ECUADOR","ACTIVO", new CategoriaProducto(1), new MovimientoContable(2)));
            gruposProductos.add(new GrupoProducto("GPR011907000015", "HOGAR", "LIMPIEZA", "CASAS","", "","","ACTIVO", new CategoriaProducto(2), new MovimientoContable(1)));
            rep.saveAll(gruposProductos);
        }
    }
}
