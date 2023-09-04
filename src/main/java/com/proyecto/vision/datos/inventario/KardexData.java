package com.proyecto.vision.datos.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.TipoComprobante;
import com.proyecto.vision.modelos.inventario.Bodega;
import com.proyecto.vision.modelos.inventario.Kardex;
import com.proyecto.vision.modelos.inventario.Producto;
import com.proyecto.vision.modelos.inventario.TipoOperacion;
import com.proyecto.vision.repositorios.inventario.IKardexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(44)
@Profile({"dev","prod"})
public class KardexData implements ApplicationRunner {
    @Autowired
    private IKardexRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Kardex> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Kardex> kardexs = new ArrayList<>();
            kardexs.add(new Kardex("KAR202304000001", Date.valueOf("2023-04-01"), Constantes.vacio, 0, 200, 0, 200, 100.00, 0, 100.00, 20000.00, new TipoComprobante(1), new TipoOperacion(1), new Bodega(1), new Producto(1)));
            kardexs.add(new Kardex("KAR202304000002", Date.valueOf("2023-04-01"), Constantes.vacio, 0, 240, 0, 240, 120.75, 0, 120.75, 28980.00, new TipoComprobante(1), new TipoOperacion(1), new Bodega(1), new Producto(2)));
            kardexs.add(new Kardex("KAR202304000003", Date.valueOf("2023-04-01"), Constantes.vacio, 0, 345, 0, 345, 200.15, 0, 200.15, 69051.75, new TipoComprobante(1), new TipoOperacion(1), new Bodega(1), new Producto(3)));
            kardexs.add(new Kardex("KAR202304000004", Date.valueOf("2023-04-01"), Constantes.vacio, 0, 400, 0, 400, 200.00, 0, 200.00, 80000.00, new TipoComprobante(1), new TipoOperacion(1), new Bodega(1), new Producto(4)));
            kardexs.add(new Kardex("KAR202304000005", Date.valueOf("2023-04-01"), Constantes.vacio, 0, 100, 0, 100, 300.25, 0, 300.25, 30025.00, new TipoComprobante(1), new TipoOperacion(1), new Bodega(1), new Producto(5)));
            kardexs.add(new Kardex("KAR202304000006", Date.valueOf("2023-04-01"), Constantes.vacio, 0, 100, 0, 100, 30.00, 0, 30.00, 3000.00, new TipoComprobante(1), new TipoOperacion(1), new Bodega(1), new Producto(6)));

            kardexs.add(new Kardex("KAR202309000001", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 0.6818, 0, 0.6818, 68.18, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(11)));
            kardexs.add(new Kardex("KAR202309000002", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.2727, 0, 2.2727, 227.27, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(12)));
            kardexs.add(new Kardex("KAR202309000003", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(13)));
            kardexs.add(new Kardex("KAR202309000004", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.7273, 0, 2.7273, 272.73, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(14)));
            kardexs.add(new Kardex("KAR202309000005", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(15)));
            kardexs.add(new Kardex("KAR202309000006", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.1818, 0, 3.1818, 318.18, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(16)));
            kardexs.add(new Kardex("KAR202309000007", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.2727, 0, 2.2727, 227.27, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(17)));
            kardexs.add(new Kardex("KAR202309000008", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 4.5455, 0, 4.5455, 454.55, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(18)));
            kardexs.add(new Kardex("KAR202309000009", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.8182, 0, 1.8182, 181.82, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(19)));
            kardexs.add(new Kardex("KAR202309000010", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.8182, 0, 1.8182, 181.82, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(20)));
            kardexs.add(new Kardex("KAR202309000011", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.8182, 0, 1.8182, 181.82, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(21)));
            kardexs.add(new Kardex("KAR202309000012", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 5, 0, 5, 500, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(22)));
            kardexs.add(new Kardex("KAR202309000013", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.8182, 0, 1.8182, 181.82, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(23)));
            kardexs.add(new Kardex("KAR202309000014", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.8182, 0, 1.8182, 181.82, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(24)));
            kardexs.add(new Kardex("KAR202309000015", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 4.0909, 0, 4.0909, 409.09, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(25)));
            kardexs.add(new Kardex("KAR202309000016", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.6364, 0, 3.6364, 363.64, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(26)));
            kardexs.add(new Kardex("KAR202309000017", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.4091, 0, 3.4091, 340.91, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(27)));
            kardexs.add(new Kardex("KAR202309000018", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.7273, 0, 2.7273, 272.73, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(28)));
            kardexs.add(new Kardex("KAR202309000019", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.4091, 0, 3.4091, 340.91, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(29)));
            kardexs.add(new Kardex("KAR202309000020", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(30)));
            kardexs.add(new Kardex("KAR202309000021", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.0455, 0, 2.0455, 204.55, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(31)));
            kardexs.add(new Kardex("KAR202309000022", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.0455, 0, 2.0455, 204.55, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(32)));
            kardexs.add(new Kardex("KAR202309000023", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.0455, 0, 2.0455, 204.55, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(33)));
            kardexs.add(new Kardex("KAR202309000024", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.0455, 0, 2.0455, 204.55, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(34)));
            kardexs.add(new Kardex("KAR202309000025", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.0455, 0, 2.0455, 204.55, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(35)));
            kardexs.add(new Kardex("KAR202309000026", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.7273, 0, 2.7273, 272.73, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(36)));
            kardexs.add(new Kardex("KAR202309000027", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.4091, 0, 3.4091, 340.91, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(37)));
            kardexs.add(new Kardex("KAR202309000028", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(38)));
            kardexs.add(new Kardex("KAR202309000029", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.0455, 0, 2.0455, 204.55, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(39)));
            kardexs.add(new Kardex("KAR202309000030", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.0455, 0, 2.0455, 204.55, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(40)));
            kardexs.add(new Kardex("KAR202309000031", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.0455, 0, 2.0455, 204.55, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(41)));
            kardexs.add(new Kardex("KAR202309000032", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.7273, 0, 2.7273, 272.73, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(42)));
            kardexs.add(new Kardex("KAR202309000033", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.4091, 0, 3.4091, 340.91, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(43)));
            kardexs.add(new Kardex("KAR202309000034", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(44)));
            kardexs.add(new Kardex("KAR202309000035", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.0455, 0, 2.0455, 204.55, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(45)));
            kardexs.add(new Kardex("KAR202309000036", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.6364, 0, 3.6364, 363.64, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(46)));
            kardexs.add(new Kardex("KAR202309000037", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 0, 0, 0, 0, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(47)));
            kardexs.add(new Kardex("KAR202309000038", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.1818, 0, 3.1818, 318.18, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(48)));
            kardexs.add(new Kardex("KAR202309000039", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.6364, 0, 3.6364, 363.64, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(49)));
            kardexs.add(new Kardex("KAR202309000040", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.1818, 0, 3.1818, 318.18, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(50)));
            kardexs.add(new Kardex("KAR202309000041", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(51)));
            kardexs.add(new Kardex("KAR202309000042", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(52)));
            kardexs.add(new Kardex("KAR202309000043", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(53)));
            kardexs.add(new Kardex("KAR202309000044", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.1818, 0, 3.1818, 318.18, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(54)));
            kardexs.add(new Kardex("KAR202309000045", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.6364, 0, 3.6364, 363.64, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(55)));
            kardexs.add(new Kardex("KAR202309000046", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.1818, 0, 3.1818, 318.18, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(56)));
            kardexs.add(new Kardex("KAR202309000047", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(57)));
            kardexs.add(new Kardex("KAR202309000048", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(58)));
            kardexs.add(new Kardex("KAR202309000049", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.1818, 0, 3.1818, 318.18, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(59)));
            kardexs.add(new Kardex("KAR202309000050", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.6364, 0, 3.6364, 363.64, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(60)));
            kardexs.add(new Kardex("KAR202309000051", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(61)));
            kardexs.add(new Kardex("KAR202309000052", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.1818, 0, 3.1818, 318.18, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(62)));
            kardexs.add(new Kardex("KAR202309000053", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.6364, 0, 3.6364, 363.64, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(63)));
            kardexs.add(new Kardex("KAR202309000054", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(64)));
            kardexs.add(new Kardex("KAR202309000055", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(65)));
            kardexs.add(new Kardex("KAR202309000056", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.1818, 0, 3.1818, 318.18, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(66)));
            kardexs.add(new Kardex("KAR202309000057", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.6364, 0, 3.6364, 363.64, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(67)));
            kardexs.add(new Kardex("KAR202309000058", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 2.5, 0, 2.5, 250, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(68)));
            kardexs.add(new Kardex("KAR202309000059", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.1818, 0, 3.1818, 318.18, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(69)));
            kardexs.add(new Kardex("KAR202309000060", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 3.1818, 0, 3.1818, 318.18, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(70)));
            kardexs.add(new Kardex("KAR202309000061", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.5909, 0, 1.5909, 159.09, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(71)));
            kardexs.add(new Kardex("KAR202309000062", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 0.9091, 0, 0.9091, 90.91, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(72)));
            kardexs.add(new Kardex("KAR202309000063", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.5909, 0, 1.5909, 159.09, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(73)));
            kardexs.add(new Kardex("KAR202309000064", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 0.6818, 0, 0.6818, 68.18, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(74)));
            kardexs.add(new Kardex("KAR202309000065", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 0.9091, 0, 0.9091, 90.91, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(75)));
            kardexs.add(new Kardex("KAR202309000066", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 0.9091, 0, 0.9091, 90.91, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(76)));
            kardexs.add(new Kardex("KAR202309000067", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 0.9091, 0, 0.9091, 90.91, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(77)));
            kardexs.add(new Kardex("KAR202309000068", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 0.9091, 0, 0.9091, 90.91, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(78)));
            kardexs.add(new Kardex("KAR202309000069", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 0.9091, 0, 0.9091, 90.91, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(79)));
            kardexs.add(new Kardex("KAR202309000070", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.5909, 0, 1.5909, 159.09, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(80)));
            kardexs.add(new Kardex("KAR202309000071", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.5909, 0, 1.5909, 159.09, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(81)));
            kardexs.add(new Kardex("KAR202309000072", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.5909, 0, 1.5909, 159.09, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(82)));
            kardexs.add(new Kardex("KAR202309000073", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.3636, 0, 1.3636, 136.36, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(83)));
            kardexs.add(new Kardex("KAR202309000074", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.3636, 0, 1.3636, 136.36, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(84)));
            kardexs.add(new Kardex("KAR202309000075", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.3636, 0, 1.3636, 136.36, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(85)));
            kardexs.add(new Kardex("KAR202309000076", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.3636, 0, 1.3636, 136.36, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(86)));
            kardexs.add(new Kardex("KAR202309000077", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 0.6818, 0, 0.6818, 68.18, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(87)));
            kardexs.add(new Kardex("KAR202309000078", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 0.9091, 0, 0.9091, 90.91, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(88)));
            kardexs.add(new Kardex("KAR202309000079", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.3636, 0, 1.3636, 136.36, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(89)));
            kardexs.add(new Kardex("KAR202309000080", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.8182, 0, 1.8182, 181.82, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(90)));
            kardexs.add(new Kardex("KAR202309000081", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.3636, 0, 1.3636, 136.36, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(91)));
            kardexs.add(new Kardex("KAR202309000082", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.3636, 0, 1.3636, 136.36, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(92)));
            kardexs.add(new Kardex("KAR202309000083", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.3636, 0, 1.3636, 136.36, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(93)));
            kardexs.add(new Kardex("KAR202309000084", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.3636, 0, 1.3636, 136.36, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(94)));
            kardexs.add(new Kardex("KAR202309000085", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.3636, 0, 1.3636, 136.36, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(95)));
            kardexs.add(new Kardex("KAR202309000086", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.3636, 0, 1.3636, 136.36, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(96)));
            kardexs.add(new Kardex("KAR202309000087", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 0.9091, 0, 0.9091, 90.91, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(97)));
            kardexs.add(new Kardex("KAR202309000088", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 1.3636, 0, 1.3636, 136.36, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(98)));
            kardexs.add(new Kardex("KAR202309000089", Date.valueOf("2023-08-01"), Constantes.vacio, 0, 100, 0, 100, 4.5455, 0, 4.5455, 454.55, new TipoComprobante(1), new TipoOperacion(1), new Bodega(4), new Producto(99)));

            rep.saveAll(kardexs);
        }
    }
}
