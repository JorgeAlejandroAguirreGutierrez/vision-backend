package com.proyecto.vision.datos.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.contabilidad.CuentaContable;
import com.proyecto.vision.modelos.inventario.CategoriaProducto;
import com.proyecto.vision.modelos.inventario.GrupoProducto;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.repositorios.inventario.IGrupoProductoRepository;
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
            gruposProductos.add(new GrupoProducto("GPR011907000001", "HOGAR", "ELECTRODOMESTICO", "LINEA BLANCA", "NEVERA", "LG","PUERTA SIMPLE", Constantes.estadoActivo,new CategoriaProducto(1), new CuentaContable(5), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000002", "HOGAR", "ELECTRODOMESTICO", "LINEA GRIS", "TELEVISOR", "SONY","SMART", Constantes.estadoActivo,new CategoriaProducto(1), new CuentaContable(6), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000003", "HOGAR", "MUEBLES", "LINEA CAFE", "DORMITORIOS", "CAMAS", "ZERO FUSION", Constantes.estadoActivo, new CategoriaProducto(1), new CuentaContable(5), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000004", "JUGUETERIA", "NIÑOS", "MOTORIZADOS", "CARROS", "HOT WEELLS","MINIATURA", Constantes.estadoActivo, new CategoriaProducto(1), new CuentaContable(6), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000005", "JUGUETERIA", "NIÑAS", "MUÑECAS", "PLASTICO", "BARBIE","DREAM HOUSE", Constantes.estadoActivo, new CategoriaProducto(1), new CuentaContable(5), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000006", "BEBIDAS", "ALCOHOLICAS", "CERVEZAS", "NACIONAL", "CLUB", "PLATINIUM", Constantes.estadoActivo, new CategoriaProducto(1), new CuentaContable(6), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000007", "BEBIDAS", "ALCOHOLICAS", "CERVEZAS", "IMPORTADA", "HEINEKEN", "LATA", Constantes.estadoActivo, new CategoriaProducto(1), new CuentaContable(5), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000008", "BEBIDAS", "NO ALCOHOLICAS", "AGUA", "MINERALIZADA", "GÜITIG", "BOTELLA", Constantes.estadoActivo, new CategoriaProducto(1), new CuentaContable(6), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000009", "ALIMENTOS", "PERECIBLES", "FRUTAS","NACIONAL", "COSTA","NO EMPAQUETADA", Constantes.estadoActivo, new CategoriaProducto(1), new CuentaContable(5), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000010", "ALIMENTOS", "NO PERECIBLES", "LEGUMBRES","ENLATADOS", "GRANOS", "EMPAQUETADO", Constantes.estadoActivo, new CategoriaProducto(1), new CuentaContable(6), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000011", "CONFITERIA", "SAL", "CARAMELOS", "IMPORTADO", "LA UNIVERSAL", "ENFUNDADO", Constantes.estadoActivo, new CategoriaProducto(1), new CuentaContable(5), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000012", "CONFITERIA", "DULCE", "CHOCOLATES","NACIONAL", "FERRERO","CAJA", Constantes.estadoActivo, new CategoriaProducto(1), new CuentaContable(6), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000013", "HOGAR", "TECNOLOGIA", "COMPUTADORAS","PORTATIL", "DELL","LATITUDE", Constantes.estadoActivo, new CategoriaProducto(1), new CuentaContable(5), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000014", "HOGAR", "DEPORTES", "ROPA","CAMISETAS", "FUTBOL","ECUADOR", Constantes.estadoActivo, new CategoriaProducto(1), new CuentaContable(6), new Empresa(1)));
            gruposProductos.add(new GrupoProducto("GPR011907000015", "HOGAR", "LIMPIEZA", "CASAS","", "","", Constantes.estadoActivo, new CategoriaProducto(2), new CuentaContable(5), new Empresa(1)));

            gruposProductos.add(new GrupoProducto("GPR022308000001","BEBIDAS","NO ALCOHOLICAS","SIN GAS","AGUA","TESALIA","500 ML","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000002","BEBIDAS","ALCOHOLICA","NACIONAL","CERVEZA","CORONA","MEDIANA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000003","BEBIDAS","ALCOHOLICA","NACIONAL","CERVEZA","PILSENER","1LT","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000004","BEBIDAS","ALCOHOLICA","NACIONAL","CERVEZA","CLUB","1LT","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000005","ALIMENTACION","TRADICIONAL","SIMPLE","ENTERO","CEVICHE","ATUN","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000006","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","CAMARON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000007","ALIMENTACION","TRADICIONAL","SIMPLE","ENTERO","CEVICHE","CHICHARRON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000008","ALIMENTACION","TRADICIONAL","ESPECIAL","ENTERO","CEVICHE","CUADRON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000009","ALIMENTACION","TRADICIONAL","SIMPLE","ENTERO","CEVICHE","CUERO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000010","ALIMENTACION","TRADICIONAL","SIMPLE","ENTERO","CEVICHE","OREJA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000011","ALIMENTACION","TRADICIONAL","SIMPLE","ENTERO","CEVICHE","POLLO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000012","ALIMENTACION","TRADICIONAL","ESPECIAL","ENTERO","CEVICHE","TODITO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000013","ALIMENTACION","TRADICIONAL","SIMPLE","ENTERO","CEVICHE","TROMPA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000014","ALIMENTACION","TRADICIONAL","SIMPLE","ENTERO","CEVICHE","LIGTH","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000015","ALIMENTACION","TRADICIONAL","ESPECIAL","ENTERO","CEVICHE","MAX","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000016","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","ATUN+CAMARON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000017","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","CHICHARRON+ATUN","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000018","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","CUERO+ATUN","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000019","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","CUERO+CAMARON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000020","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","CUERO+CHICHARRON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000021","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","CUERO+MOLLEJA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000022","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","CUERO+OREJA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000023","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","CUERO+POLLO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000024","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","CUERO+TROMPA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000025","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","MOLLEJA+POLLO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000026","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","OREJA+ATUN","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000027","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","OREJA+CAMARON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000028","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","OREJA+CHICHARRON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000029","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","OREJA+MOLLEJA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000030","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","OREJA+POLLO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000031","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","OREJA+TROMPA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000032","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","POLLO+ATUN","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000033","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","POLLO+CAMARON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000034","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","POLLO+CHICHARRON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000035","ALIMENTACION","TRADICIONAL","MIXTO","ENTERO","CEVICHE","POLLO+TROMPA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000036","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+CHICHARRON+CAMARON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000037","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+MOLLEJA+TROMPA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000038","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+OREJA+ATUN","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000039","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+OREJA+CAMARON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000040","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+OREJA+CHICHARRON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000041","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+OREJA+MOLLEJA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000042","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+OREJA+POLLO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000043","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+OREJA+TROMPA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000044","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+POLLO+ATUN","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000045","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+POLLO+CAMARON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000046","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+POLLO+CHICHARRON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000047","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+POLLO+MOLLEJA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000048","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+POLLO+TROMPA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000049","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","CUERO+TROMPA+CHICHARRON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000050","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","OREJA+CHICHARRON+CAMARON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000051","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","OREJA+MOLLEJA+TROMPA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000052","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","OREJA+POLLO+ATUN","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000053","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","OREJA+POLLO+CAMARON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000054","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","OREJA+POLLO+MOLLEJA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000055","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","OREJA+POLLO+TROMPA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000056","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","OREJA+TROMPA+CHICHARRON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000057","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","POLLO+CHICHARRON+CAMARON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000058","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","POLLO+MOLLEJA+TROMPA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000059","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","POLLO+OREJA+CHICHARRON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000060","ALIMENTACION","TRADICIONAL","TRIMIXTO","ENTERO","CEVICHE","POLLO+TROMPA+CHICHARRON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000061","BEBIDAS","NO ALCOHOLICAS","SIN GAS","REFRESCO","FUZE TEA","1LT","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000062","BEBIDAS","NO ALCOHOLICAS","SIN GAS","REFRESCO","FUZE TEA","500ML","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000063","BEBIDAS","NO ALCOHOLICAS","CON GAS","GASEOSAS","FANTA","1LT","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000064","BEBIDAS","NO ALCOHOLICAS","CON GAS","GASEOSAS","COCA COLA","MEDIANA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000065","BEBIDAS","NO ALCOHOLICAS","CON GAS","GASEOSAS","COCA COLA","DESECHABLE","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000066","BEBIDAS","NO ALCOHOLICAS","CON GAS","GASEOSAS","FANTA","MEDIDA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000067","BEBIDAS","NO ALCOHOLICAS","SIN GAS","NATURALES","JUGOS","DE COCO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000068","BEBIDAS","NO ALCOHOLICAS","SIN GAS","NATURALES","JUGOS","DE NARANJA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000069","BEBIDAS","NO ALCOHOLICAS","SIN GAS","NATURALES","JUGOS","DE TAMARINDO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000070","ALIMENTACION","TRADICIONAL","MIXTO","MEDIO","CEVICHE","CUERO+OREJA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000071","ALIMENTACION","TRADICIONAL","MIXTO","MEDIO","CEVICHE","CUERO+POLLO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000072","ALIMENTACION","TRADICIONAL","MIXTO","MEDIO","CEVICHE","OREJA+POLLO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000073","ALIMENTACION","TRADICIONAL","SIMPLE","MEDIO","CEVICHE","CUERO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000074","ALIMENTACION","TRADICIONAL","SIMPLE","MEDIO","CEVICHE","LIGHT","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000075","ALIMENTACION","TRADICIONAL","SIMPLE","MEDIO","CEVICHE","OREJA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000076","ALIMENTACION","TRADICIONAL","SIMPLE","MEDIO","CEVICHE","POLLO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000077","ALIMENTACION","TRADICIONAL","SIMPLE","ADICIONAL","PORCION","CHIFLE","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000078","ALIMENTACION","TRADICIONAL","SIMPLE","ADICIONAL","PORCION","DE AGUACATE","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000079","ALIMENTACION","TRADICIONAL","SIMPLE","ADICIONAL","PORCION","DE ATUN","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000080","ALIMENTACION","TRADICIONAL","SIMPLE","ADICIONAL","PORCION","DE CAMARON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000081","ALIMENTACION","TRADICIONAL","SIMPLE","ADICIONAL","PORCION","DE CHICHARRON","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000082","ALIMENTACION","TRADICIONAL","SIMPLE","ADICIONAL","PORCION","DE CHOCHOS","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000083","ALIMENTACION","TRADICIONAL","SIMPLE","ADICIONAL","PORCION","DE CUERO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000084","ALIMENTACION","TRADICIONAL","SIMPLE","ADICIONAL","PORCION","DE MOLLEJA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000085","ALIMENTACION","TRADICIONAL","SIMPLE","ADICIONAL","PORCION","DE OREJA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000086","ALIMENTACION","TRADICIONAL","SIMPLE","ADICIONAL","PORCION","DE POLLO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000087","ALIMENTACION","TRADICIONAL","SIMPLE","ADICIONAL","PORCION","DE TOSTADO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000088","ALIMENTACION","TRADICIONAL","SIMPLE","ADICIONAL","PORCION","DE TROMPA","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));
            gruposProductos.add(new GrupoProducto("GPR022308000089","ALIMENTACION","TRADICIONAL","ESPECIAL","ENTERO","DE CASA","VOLQUETERO","ACTIVO", new CategoriaProducto(1) ,new CuentaContable(323), new Empresa(2)));

            rep.saveAll(gruposProductos);
        }
    }
}
