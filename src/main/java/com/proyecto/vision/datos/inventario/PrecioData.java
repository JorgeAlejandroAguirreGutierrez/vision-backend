package com.proyecto.vision.datos.inventario;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.cliente.Segmento;
import com.proyecto.vision.modelos.inventario.*;
import com.proyecto.vision.repositorios.inventario.IPrecioRepository;
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
@Order(44)
@Profile({"dev","prod"})
public class PrecioData implements ApplicationRunner {
    @Autowired
    private IPrecioRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Precio> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Precio> precios = new ArrayList<>();
            precios.add(new Precio("PRE012301000001", 15.0, 6.0, 15.0, 15.0, 30.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(1), new Producto(1)));
            precios.add(new Precio("PRE012301000002", 2.0, 2.0, 2.0, 2.0, 20.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(2), new Producto(1)));
            precios.add(new Precio("PRE012301000003", 3.0, 3.0, 3.0, 3.0, 30.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(3), new Producto(1)));
            precios.add(new Precio("PRE012301000004", 20.0, 10.0, 20.0, 20.0, 30.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(4), new Producto(1)));

            precios.add(new Precio("PRE012301000005", 20.0, 6.0, 30.0, 20.0, 40.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(1), new Producto(2)));
            precios.add(new Precio("PRE012301000006", 30.0, 2.0, 40.0, 30.0, 50.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(2), new Producto(2)));
            precios.add(new Precio("PRE012301000007", 40.0, 3.0, 50.0, 40.0, 60.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(3), new Producto(2)));
            precios.add(new Precio("PRE012301000008", 50.0, 10.0, 60.0, 50.0, 70.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(4), new Producto(2)));

            precios.add(new Precio("PRE012301000009", 20.0, 6.0, 30.0, 20.0, 40.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(1), new Producto(3)));
            precios.add(new Precio("PRE012301000010", 30.0, 2.0, 40.0, 30.0, 50.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(2), new Producto(3)));
            precios.add(new Precio("PRE012301000011", 40.0, 3.0, 50.0, 40.0, 60.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(3), new Producto(3)));
            precios.add(new Precio("PRE012301000012", 50.0, 10.0, 60.0, 50.0, 70.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(4), new Producto(3)));

            precios.add(new Precio("PRE012301000013", 20.0, 6.0, 30.0, 20.0, 40.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(1), new Producto(4)));
            precios.add(new Precio("PRE012301000014", 30.0, 2.0, 40.0, 30.0, 50.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(2), new Producto(4)));
            precios.add(new Precio("PRE012301000015", 40.0, 3.0, 50.0, 40.0, 60.0, 110.0, 10.0, Constantes.estadoActivo, new Segmento(3), new Producto(4)));
            precios.add(new Precio("PRE012301000016", 50.0, 10.0, 60.0, 50.0, 70.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(4), new Producto(4)));

            precios.add(new Precio("PRE012301000017", 20.0, 6.0, 30.0, 20.0, 40.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(1), new Producto(5)));
            precios.add(new Precio("PRE012301000018", 30.0, 2.0, 40.0, 30.0, 50.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(2), new Producto(5)));
            precios.add(new Precio("PRE012301000019", 40.0, 3.0, 50.0, 40.0, 60.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(3), new Producto(5)));
            precios.add(new Precio("PRE012301000020", 50.0, 10.0, 60.0, 50.0, 70.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(4), new Producto(5)));

            precios.add(new Precio("PRE012301000021", 20.0, 6.0, 30.0, 20.0, 40.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(1), new Producto(6)));
            precios.add(new Precio("PRE012301000022", 30.0, 2.0, 40.0, 30.0, 50.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(2), new Producto(6)));
            precios.add(new Precio("PRE012301000023", 40.0, 3.0, 50.0, 40.0, 60.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(3), new Producto(6)));
            precios.add(new Precio("PRE012301000024", 50.0, 10.0, 60.0, 50.0, 70.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(4), new Producto(6)));

            precios.add(new Precio("PRE012301000025", 20.0, 6.0, 30.0, 20.0, 40.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(1), new Producto(7)));
            precios.add(new Precio("PRE012301000026", 30.0, 2.0, 40.0, 30.0, 50.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(2), new Producto(7)));
            precios.add(new Precio("PRE012301000027", 40.0, 3.0, 50.0, 40.0, 60.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(3), new Producto(7)));
            precios.add(new Precio("PRE012301000028", 50.0, 10.0, 60.0, 50.0, 70.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(4), new Producto(7)));

            precios.add(new Precio("PRE012301000029", 20.0, 6.0, 30.0, 20.0, 40.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(1), new Producto(8)));
            precios.add(new Precio("PRE012301000030", 30.0, 2.0, 40.0, 30.0, 50.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(2), new Producto(8)));
            precios.add(new Precio("PRE012301000031", 40.0, 3.0, 50.0, 40.0, 60.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(3), new Producto(8)));
            precios.add(new Precio("PRE012301000032", 50.0, 10.0, 60.0, 50.0, 70.0, 10.0, 10.0, Constantes.estadoActivo, new Segmento(4), new Producto(8)));

            // EMPRESA 2
            precios.add(new Precio("PRE022308000001", 0.6818, 10, 0.75, 0.75, 0.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(9)));
            precios.add(new Precio("PRE022308000002", 2.2727, 10, 2.50, 2.50, 2.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(10)));
            precios.add(new Precio("PRE022308000003", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(11)));
            precios.add(new Precio("PRE022308000004", 2.7273, 10, 3.00, 3.00, 3.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(12)));
            precios.add(new Precio("PRE022308000009", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(13)));
            precios.add(new Precio("PRE022308000010", 3.1818, 10, 3.50, 3.50, 3.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(14)));
            precios.add(new Precio("PRE022308000011", 2.2727, 10, 2.50, 2.50, 2.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(15)));
            precios.add(new Precio("PRE022308000012", 4.5455, 10, 5.00, 5.00, 5.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(16)));
            precios.add(new Precio("PRE022308000013", 1.8182, 10, 2.00, 2.00, 2.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(17)));
            precios.add(new Precio("PRE022308000014", 1.8182, 10, 2.00, 2.00, 2.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(18)));
            precios.add(new Precio("PRE022308000015", 1.8182, 10, 2.00, 2.00, 2.00, 110, 10, Constantes.estadoActivo, new Segmento(5), new Producto(19)));
            precios.add(new Precio("PRE022308000016", 5, 10, 5.50, 5.50, 5.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(20)));
            precios.add(new Precio("PRE022308000017", 1.8182, 10, 2.00, 2.00, 2.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(21)));
            precios.add(new Precio("PRE022308000018", 1.8182, 10, 2.00, 2.00, 2.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(22)));
            precios.add(new Precio("PRE022308000019", 4.0909, 10, 4.50, 4.50, 4.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(23)));
            precios.add(new Precio("PRE022308000020", 3.6364, 10, 4.00, 4.00, 4.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(24)));
            precios.add(new Precio("PRE022308000021", 3.4091, 10, 3.75, 3.75, 3.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(25)));
            precios.add(new Precio("PRE022308000022", 2.7273, 10, 3.00, 3.00, 3.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(26)));
            precios.add(new Precio("PRE022308000023", 3.4091, 10, 3.75, 3.75, 3.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(27)));
            precios.add(new Precio("PRE022308000024", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(28)));
            precios.add(new Precio("PRE022308000025", 2.0455, 10, 2.25, 2.25, 2.25, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(29)));
            precios.add(new Precio("PRE022308000026", 2.0455, 10, 2.25, 2.25, 2.25, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(30)));
            precios.add(new Precio("PRE022308000027", 2.0455, 10, 2.25, 2.25, 2.25, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(31)));
            precios.add(new Precio("PRE022308000028", 2.0455, 10, 2.25, 2.25, 2.25, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(32)));
            precios.add(new Precio("PRE022308000029", 2.0455, 10, 2.25, 2.25, 2.25, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(33)));
            precios.add(new Precio("PRE022308000030", 2.7273, 10, 3.00, 3.00, 3.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(34)));
            precios.add(new Precio("PRE022308000031", 3.4091, 10, 3.75, 3.75, 3.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(35)));
            precios.add(new Precio("PRE022308000032", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(36)));
            precios.add(new Precio("PRE022308000033", 2.0455, 10, 2.25, 2.25, 2.25, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(37)));
            precios.add(new Precio("PRE022308000034", 2.0455, 10, 2.25, 2.25, 2.25, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(38)));
            precios.add(new Precio("PRE022308000035", 2.0455, 10, 2.25, 2.25, 2.25, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(39)));
            precios.add(new Precio("PRE022308000036", 2.7273, 10, 3.00, 3.00, 3.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(40)));
            precios.add(new Precio("PRE022308000037", 3.4091, 10, 3.75, 3.75, 3.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(41)));
            precios.add(new Precio("PRE022308000038", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(42)));
            precios.add(new Precio("PRE022308000038", 2.0455, 10, 2.25, 2.25, 2.25, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(43)));
            precios.add(new Precio("PRE022308000039", 3.6364, 10, 4.00, 4.00, 4.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(44)));
            precios.add(new Precio("PRE022308000040", 0, 10, 0.00, 0.00, 0.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(45)));
            precios.add(new Precio("PRE022308000041", 3.1818, 10, 3.50, 3.50, 3.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(46)));
            precios.add(new Precio("PRE022308000042", 3.6364, 10, 4.00, 4.00, 4.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(47)));
            precios.add(new Precio("PRE022308000043", 3.1818, 10, 3.50, 3.50, 3.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(48)));
            precios.add(new Precio("PRE022308000044", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(49)));
            precios.add(new Precio("PRE022308000045", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(50)));
            precios.add(new Precio("PRE022308000046", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(51)));
            precios.add(new Precio("PRE022308000047", 3.1818, 10, 3.50, 3.50, 3.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(52)));
            precios.add(new Precio("PRE022308000048", 3.6364, 10, 4.00, 4.00, 4.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(53)));
            precios.add(new Precio("PRE022308000049", 3.1818, 10, 3.50, 3.50, 3.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(54)));
            precios.add(new Precio("PRE022308000050", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(55)));
            precios.add(new Precio("PRE022308000051", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(56)));
            precios.add(new Precio("PRE022308000052", 3.1818, 10, 3.50, 3.50, 3.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(57)));
            precios.add(new Precio("PRE022308000053", 3.6364, 10, 4.00, 4.00, 4.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(58)));
            precios.add(new Precio("PRE022308000054", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(59)));
            precios.add(new Precio("PRE022308000055", 3.1818, 10, 3.50, 3.50, 3.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(60)));
            precios.add(new Precio("PRE022308000056", 3.6364, 10, 4.00, 4.00, 4.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(61)));
            precios.add(new Precio("PRE022308000057", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(62)));
            precios.add(new Precio("PRE022308000058", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(63)));
            precios.add(new Precio("PRE022308000059", 3.1818, 10, 3.50, 3.50, 3.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(64)));
            precios.add(new Precio("PRE022308000060", 3.6364, 10, 4.00, 4.00, 4.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(65)));
            precios.add(new Precio("PRE022308000061", 2.5, 10, 2.75, 2.75, 2.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(66)));
            precios.add(new Precio("PRE022308000062", 3.1818, 10, 3.50, 3.50, 3.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(67)));
            precios.add(new Precio("PRE022308000063", 3.1818, 10, 3.50, 3.50, 3.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(68)));
            precios.add(new Precio("PRE022308000064", 1.5909, 10, 1.75, 1.75, 1.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(69)));
            precios.add(new Precio("PRE022308000065", 0.9091, 10, 1.00, 1.00, 1.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(70)));
            precios.add(new Precio("PRE022308000066", 1.5909, 10, 1.75, 1.75, 1.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(71)));
            precios.add(new Precio("PRE022308000067", 0.6818, 10, 0.75, 0.75, 0.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(72)));
            precios.add(new Precio("PRE022308000068", 0.9091, 10, 1.00, 1.00, 1.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(73)));
            precios.add(new Precio("PRE022308000069", 0.9091, 10, 1.00, 1.00, 1.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(74)));
            precios.add(new Precio("PRE022308000070", 0.9091, 10, 1.00, 1.00, 1.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(75)));
            precios.add(new Precio("PRE022308000071", 0.9091, 10, 1.00, 1.00, 1.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(76)));
            precios.add(new Precio("PRE022308000072", 0.9091, 10, 1.00, 1.00, 1.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(77)));
            precios.add(new Precio("PRE022308000073", 1.5909, 10, 1.75, 1.75, 1.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(78)));
            precios.add(new Precio("PRE022308000074", 1.5909, 10, 1.75, 1.75, 1.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(79)));
            precios.add(new Precio("PRE022308000075", 1.5909, 10, 1.75, 1.75, 1.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(80)));
            precios.add(new Precio("PRE022308000076", 1.3636, 10, 1.50, 1.50, 1.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(81)));
            precios.add(new Precio("PRE022308000077", 1.3636, 10, 1.50, 1.50, 1.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(82)));
            precios.add(new Precio("PRE022308000078", 1.3636, 10, 1.50, 1.50, 1.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(83)));
            precios.add(new Precio("PRE022308000079", 1.3636, 10, 1.50, 1.50, 1.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(84)));
            precios.add(new Precio("PRE022308000080", 0.6818, 10, 0.75, 0.75, 0.75, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(85)));
            precios.add(new Precio("PRE022308000081", 0.9091, 10, 1.00, 1.00, 1.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(86)));
            precios.add(new Precio("PRE022308000082", 1.3636, 10, 1.50, 1.50, 1.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(87)));
            precios.add(new Precio("PRE022308000083", 1.8182, 10, 2.00, 2.00, 2.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(88)));
            precios.add(new Precio("PRE022308000084", 1.3636, 10, 1.50, 1.50, 1.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(89)));
            precios.add(new Precio("PRE022308000085", 1.3636, 10, 1.50, 1.50, 1.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(90)));
            precios.add(new Precio("PRE022308000086", 1.3636, 10, 1.50, 1.50, 1.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(91)));
            precios.add(new Precio("PRE022308000087", 1.3636, 10, 1.50, 1.50, 1.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(92)));
            precios.add(new Precio("PRE022308000088", 1.3636, 10, 1.50, 1.50, 1.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(93)));
            precios.add(new Precio("PRE022308000089", 1.3636, 10, 1.50, 1.50, 1.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(94)));
            precios.add(new Precio("PRE022308000090", 0.9091, 10, 1.00, 1.00, 1.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(95)));
            precios.add(new Precio("PRE022308000091", 1.3636, 10, 1.50, 1.50, 1.50, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(96)));
            precios.add(new Precio("PRE022308000092", 4.5455, 10, 5.00, 5.00, 5.00, 10, 10, Constantes.estadoActivo, new Segmento(5), new Producto(97)));

            rep.saveAll(precios);
        }
    }
}
