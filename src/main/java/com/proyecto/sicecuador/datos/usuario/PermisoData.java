package com.proyecto.sicecuador.datos.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.configuracion.MenuOpcion;
import com.proyecto.sicecuador.modelos.usuario.Perfil;
import com.proyecto.sicecuador.modelos.usuario.Permiso;
import com.proyecto.sicecuador.repositorios.usuario.IPermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(22)
public class PermisoData implements ApplicationRunner {
    @Autowired
    private IPermisoRepository rep;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Permiso> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Permiso> permisos = new ArrayList<>();
        //PERFIL ADMINISTRADOR
            //Clientes
            permisos.add(new Permiso("PER012306000001", Constantes.activo, new MenuOpcion(1), new Perfil(1)));
            permisos.add(new Permiso("PER012306000002", Constantes.activo, new MenuOpcion(2), new Perfil(1)));
            permisos.add(new Permiso("PER012306000003", Constantes.activo, new MenuOpcion(13), new Perfil(1)));
            permisos.add(new Permiso("PER012306000004", Constantes.activo, new MenuOpcion(14), new Perfil(1)));
            permisos.add(new Permiso("PER012306000005", Constantes.activo, new MenuOpcion(15), new Perfil(1)));
            permisos.add(new Permiso("PER012306000006", Constantes.activo, new MenuOpcion(16), new Perfil(1)));
            permisos.add(new Permiso("PER012306000007", Constantes.activo, new MenuOpcion(17), new Perfil(1)));
            //Compras
            permisos.add(new Permiso("PER012306000008", Constantes.activo, new MenuOpcion(18), new Perfil(1)));
            permisos.add(new Permiso("PER012306000009", Constantes.activo, new MenuOpcion(19), new Perfil(1)));
            permisos.add(new Permiso("PER012306000010", Constantes.activo, new MenuOpcion(23), new Perfil(1)));
            permisos.add(new Permiso("PER012306000011", Constantes.activo, new MenuOpcion(25), new Perfil(1)));
            permisos.add(new Permiso("PER012306000012", Constantes.activo, new MenuOpcion(27), new Perfil(1)));
            //Ventas
            permisos.add(new Permiso("PER012306000013", Constantes.activo, new MenuOpcion(29), new Perfil(1)));
            permisos.add(new Permiso("PER012306000014", Constantes.activo, new MenuOpcion(30), new Perfil(1)));
            permisos.add(new Permiso("PER012306000015", Constantes.activo, new MenuOpcion(32), new Perfil(1)));
            permisos.add(new Permiso("PER012306000016", Constantes.activo, new MenuOpcion(34), new Perfil(1)));
            permisos.add(new Permiso("PER012306000017", Constantes.activo, new MenuOpcion(35), new Perfil(1)));
            permisos.add(new Permiso("PER012306000018", Constantes.activo, new MenuOpcion(36), new Perfil(1)));
            permisos.add(new Permiso("PER012306000019", Constantes.activo, new MenuOpcion(50), new Perfil(1)));
            permisos.add(new Permiso("PER012306000020", Constantes.activo, new MenuOpcion(51), new Perfil(1)));
            permisos.add(new Permiso("PER012306000021", Constantes.activo, new MenuOpcion(52), new Perfil(1)));
            //Inventarios
            permisos.add(new Permiso("PER012306000022", Constantes.activo, new MenuOpcion(54), new Perfil(1)));
            permisos.add(new Permiso("PER012306000023", Constantes.activo, new MenuOpcion(55), new Perfil(1)));
            permisos.add(new Permiso("PER012306000024", Constantes.activo, new MenuOpcion(58), new Perfil(1)));
            permisos.add(new Permiso("PER012306000025", Constantes.activo, new MenuOpcion(59), new Perfil(1)));
            permisos.add(new Permiso("PER012306000026", Constantes.activo, new MenuOpcion(60), new Perfil(1)));
            permisos.add(new Permiso("PER012306000027", Constantes.activo, new MenuOpcion(63), new Perfil(1)));
            //Caja Bancos
            permisos.add(new Permiso("PER012306000028", Constantes.activo, new MenuOpcion(66), new Perfil(1)));
            permisos.add(new Permiso("PER012306000029", Constantes.activo, new MenuOpcion(67), new Perfil(1)));
            //Cuentas Por Cobrar
            permisos.add(new Permiso("PER012306000030", Constantes.inactivo, new MenuOpcion(68), new Perfil(1)));
            //Cuentas Por Pagar
            permisos.add(new Permiso("PER012306000031", Constantes.inactivo, new MenuOpcion(69), new Perfil(1)));
            //Activos Fijos
            permisos.add(new Permiso("PER012306000032", Constantes.inactivo, new MenuOpcion(70), new Perfil(1)));
            //Producción
            permisos.add(new Permiso("PER012306000033", Constantes.inactivo, new MenuOpcion(71), new Perfil(1)));
            //Contabilidad
            permisos.add(new Permiso("PER012306000034", Constantes.activo, new MenuOpcion(72), new Perfil(1)));
            permisos.add(new Permiso("PER012306000035", Constantes.activo, new MenuOpcion(73), new Perfil(1)));
            //Talento Humano
            permisos.add(new Permiso("PER012306000036", Constantes.inactivo, new MenuOpcion(75), new Perfil(1)));
            //Financiero
            permisos.add(new Permiso("PER012306000037", Constantes.inactivo, new MenuOpcion(76), new Perfil(1)));
            //Importación
            permisos.add(new Permiso("PER012306000038", Constantes.inactivo, new MenuOpcion(77), new Perfil(1)));
            //Reporte
            permisos.add(new Permiso("PER012306000039", Constantes.activo, new MenuOpcion(78), new Perfil(1)));
            permisos.add(new Permiso("PER012306000040", Constantes.activo, new MenuOpcion(79), new Perfil(1)));
            permisos.add(new Permiso("PER012306000041", Constantes.activo, new MenuOpcion(80), new Perfil(1)));
            permisos.add(new Permiso("PER012306000042", Constantes.activo, new MenuOpcion(81), new Perfil(1)));
            permisos.add(new Permiso("PER012306000043", Constantes.activo, new MenuOpcion(82), new Perfil(1)));
            permisos.add(new Permiso("PER012306000044", Constantes.activo, new MenuOpcion(83), new Perfil(1)));
            permisos.add(new Permiso("PER012306000045", Constantes.activo, new MenuOpcion(84), new Perfil(1)));
            permisos.add(new Permiso("PER012306000046", Constantes.activo, new MenuOpcion(85), new Perfil(1)));
            permisos.add(new Permiso("PER012306000047", Constantes.activo, new MenuOpcion(86), new Perfil(1)));
            permisos.add(new Permiso("PER012306000048", Constantes.activo, new MenuOpcion(87), new Perfil(1)));
            permisos.add(new Permiso("PER012306000049", Constantes.activo, new MenuOpcion(88), new Perfil(1)));
            permisos.add(new Permiso("PER012306000050", Constantes.activo, new MenuOpcion(89), new Perfil(1)));
            permisos.add(new Permiso("PER012306000051", Constantes.activo, new MenuOpcion(90), new Perfil(1)));
            //Accesos
            permisos.add(new Permiso("PER012306000052", Constantes.activo, new MenuOpcion(91), new Perfil(1)));
            permisos.add(new Permiso("PER012306000053", Constantes.activo, new MenuOpcion(92), new Perfil(1)));
            permisos.add(new Permiso("PER012306000054", Constantes.activo, new MenuOpcion(93), new Perfil(1)));
            permisos.add(new Permiso("PER012306000055", Constantes.activo, new MenuOpcion(97), new Perfil(1)));
            permisos.add(new Permiso("PER012306000056", Constantes.activo, new MenuOpcion(99), new Perfil(1)));
            permisos.add(new Permiso("PER012306000057", Constantes.activo, new MenuOpcion(100), new Perfil(1)));
            //Configuración
            permisos.add(new Permiso("PER012306000058", Constantes.activo, new MenuOpcion(102), new Perfil(1)));
            permisos.add(new Permiso("PER012306000059", Constantes.activo, new MenuOpcion(104), new Perfil(1)));
            permisos.add(new Permiso("PER012306000060", Constantes.activo, new MenuOpcion(105), new Perfil(1)));
            permisos.add(new Permiso("PER012306000061", Constantes.activo, new MenuOpcion(106), new Perfil(1)));
            permisos.add(new Permiso("PER012306000062", Constantes.activo, new MenuOpcion(107), new Perfil(1)));
            permisos.add(new Permiso("PER012306000063", Constantes.activo, new MenuOpcion(108), new Perfil(1)));
            permisos.add(new Permiso("PER012306000064", Constantes.activo, new MenuOpcion(109), new Perfil(1)));
            permisos.add(new Permiso("PER012306000065", Constantes.activo, new MenuOpcion(110), new Perfil(1)));
            //Indicadores
            permisos.add(new Permiso("PER012306000066", Constantes.activo, new MenuOpcion(114), new Perfil(1)));
            //Control
            permisos.add(new Permiso("PER012306000067", Constantes.inactivo, new MenuOpcion(115), new Perfil(1)));
            //Auditoría
            permisos.add(new Permiso("PER012306000068", Constantes.inactivo, new MenuOpcion(116), new Perfil(1)));
            //Tutoriales
            permisos.add(new Permiso("PER012306000069", Constantes.inactivo, new MenuOpcion(117), new Perfil(1)));
        //PERFIL GERENCIAL
            //Clientes
            permisos.add(new Permiso("PER022306000001", Constantes.activo, new MenuOpcion(1), new Perfil(2)));
            permisos.add(new Permiso("PER022306000002", Constantes.activo, new MenuOpcion(2), new Perfil(2)));
            permisos.add(new Permiso("PER022306000003", Constantes.activo, new MenuOpcion(13), new Perfil(2)));
            permisos.add(new Permiso("PER022306000004", Constantes.inactivo, new MenuOpcion(14), new Perfil(2)));
            permisos.add(new Permiso("PER022306000005", Constantes.inactivo, new MenuOpcion(15), new Perfil(2)));
            permisos.add(new Permiso("PER022306000006", Constantes.activo, new MenuOpcion(16), new Perfil(2)));
            permisos.add(new Permiso("PER022306000007", Constantes.activo, new MenuOpcion(17), new Perfil(2)));
            //Compras
            permisos.add(new Permiso("PER022306000008", Constantes.activo, new MenuOpcion(18), new Perfil(2)));
            permisos.add(new Permiso("PER022306000009", Constantes.activo, new MenuOpcion(19), new Perfil(2)));
            permisos.add(new Permiso("PER022306000010", Constantes.activo, new MenuOpcion(23), new Perfil(2)));
            permisos.add(new Permiso("PER022306000011", Constantes.activo, new MenuOpcion(25), new Perfil(2)));
            permisos.add(new Permiso("PER022306000012", Constantes.activo, new MenuOpcion(27), new Perfil(2)));
            //Ventas
            permisos.add(new Permiso("PER022306000013", Constantes.activo, new MenuOpcion(29), new Perfil(2)));
            permisos.add(new Permiso("PER022306000014", Constantes.activo, new MenuOpcion(30), new Perfil(2)));
            permisos.add(new Permiso("PER022306000015", Constantes.activo, new MenuOpcion(32), new Perfil(2)));
            permisos.add(new Permiso("PER022306000016", Constantes.inactivo, new MenuOpcion(34), new Perfil(2)));
            permisos.add(new Permiso("PER022306000017", Constantes.inactivo, new MenuOpcion(35), new Perfil(2)));
            permisos.add(new Permiso("PER022306000018", Constantes.inactivo, new MenuOpcion(36), new Perfil(2)));
            permisos.add(new Permiso("PER022306000019", Constantes.activo, new MenuOpcion(50), new Perfil(2)));
            permisos.add(new Permiso("PER022306000020", Constantes.activo, new MenuOpcion(51), new Perfil(2)));
            permisos.add(new Permiso("PER022306000021", Constantes.activo, new MenuOpcion(52), new Perfil(2)));
            //Inventarios
            permisos.add(new Permiso("PER022306000022", Constantes.activo, new MenuOpcion(54), new Perfil(2)));
            permisos.add(new Permiso("PER022306000023", Constantes.activo, new MenuOpcion(55), new Perfil(2)));
            permisos.add(new Permiso("PER022306000024", Constantes.activo, new MenuOpcion(58), new Perfil(2)));
            permisos.add(new Permiso("PER022306000025", Constantes.activo, new MenuOpcion(59), new Perfil(2)));
            permisos.add(new Permiso("PER022306000026", Constantes.activo, new MenuOpcion(60), new Perfil(2)));
            permisos.add(new Permiso("PER022306000027", Constantes.activo, new MenuOpcion(63), new Perfil(2)));
            //Caja Bancos
            permisos.add(new Permiso("PER022306000028", Constantes.activo, new MenuOpcion(66), new Perfil(2)));
            permisos.add(new Permiso("PER022306000029", Constantes.inactivo, new MenuOpcion(67), new Perfil(2)));
            //Cuenta Por Cobrar
            permisos.add(new Permiso("PER022306000030", Constantes.inactivo, new MenuOpcion(68), new Perfil(2)));
            //Cuenta Por Pagar
            permisos.add(new Permiso("PER022306000031", Constantes.inactivo, new MenuOpcion(69), new Perfil(2)));
            //Activos Fijos
            permisos.add(new Permiso("PER022306000032", Constantes.inactivo, new MenuOpcion(70), new Perfil(2)));
            //Producción
            permisos.add(new Permiso("PER022306000033", Constantes.inactivo, new MenuOpcion(71), new Perfil(2)));
            //Contabilidad
            permisos.add(new Permiso("PER022306000034", Constantes.activo, new MenuOpcion(72), new Perfil(2)));
            permisos.add(new Permiso("PER022306000035", Constantes.activo, new MenuOpcion(73), new Perfil(2)));
            //Talento Humano
            permisos.add(new Permiso("PER022306000036", Constantes.inactivo, new MenuOpcion(75), new Perfil(2)));
            //Financiero
            permisos.add(new Permiso("PER022306000037", Constantes.inactivo, new MenuOpcion(76), new Perfil(2)));
            //Importación
            permisos.add(new Permiso("PER022306000038", Constantes.inactivo, new MenuOpcion(77), new Perfil(2)));
            //Reporte
            permisos.add(new Permiso("PER022306000039", Constantes.activo, new MenuOpcion(78), new Perfil(2)));
            permisos.add(new Permiso("PER022306000040", Constantes.activo, new MenuOpcion(79), new Perfil(2)));
            permisos.add(new Permiso("PER022306000041", Constantes.activo, new MenuOpcion(80), new Perfil(2)));
            permisos.add(new Permiso("PER022306000042", Constantes.activo, new MenuOpcion(81), new Perfil(2)));
            permisos.add(new Permiso("PER022306000043", Constantes.activo, new MenuOpcion(82), new Perfil(2)));
            permisos.add(new Permiso("PER022306000044", Constantes.activo, new MenuOpcion(83), new Perfil(2)));
            permisos.add(new Permiso("PER022306000045", Constantes.activo, new MenuOpcion(84), new Perfil(2)));
            permisos.add(new Permiso("PER022306000046", Constantes.activo, new MenuOpcion(85), new Perfil(2)));
            permisos.add(new Permiso("PER022306000047", Constantes.activo, new MenuOpcion(86), new Perfil(2)));
            permisos.add(new Permiso("PER022306000048", Constantes.activo, new MenuOpcion(87), new Perfil(2)));
            permisos.add(new Permiso("PER022306000049", Constantes.activo, new MenuOpcion(88), new Perfil(2)));
            permisos.add(new Permiso("PER022306000050", Constantes.activo, new MenuOpcion(89), new Perfil(2)));
            permisos.add(new Permiso("PER022306000051", Constantes.activo, new MenuOpcion(90), new Perfil(2)));
            //Accesos
            permisos.add(new Permiso("PER022306000052", Constantes.activo, new MenuOpcion(91), new Perfil(2)));
            permisos.add(new Permiso("PER022306000053", Constantes.activo, new MenuOpcion(92), new Perfil(2)));
            permisos.add(new Permiso("PER022306000054", Constantes.activo, new MenuOpcion(93), new Perfil(2)));
            permisos.add(new Permiso("PER022306000055", Constantes.activo, new MenuOpcion(97), new Perfil(2)));
            permisos.add(new Permiso("PER022306000056", Constantes.activo, new MenuOpcion(99), new Perfil(2)));
            permisos.add(new Permiso("PER022306000057", Constantes.activo, new MenuOpcion(100), new Perfil(2)));
            //Configuración
            permisos.add(new Permiso("PER022306000058", Constantes.activo, new MenuOpcion(102), new Perfil(2)));
            permisos.add(new Permiso("PER022306000059", Constantes.activo, new MenuOpcion(104), new Perfil(2)));
            permisos.add(new Permiso("PER022306000060", Constantes.activo, new MenuOpcion(105), new Perfil(2)));
            permisos.add(new Permiso("PER022306000061", Constantes.activo, new MenuOpcion(106), new Perfil(2)));
            permisos.add(new Permiso("PER022306000062", Constantes.activo, new MenuOpcion(107), new Perfil(2)));
            permisos.add(new Permiso("PER022306000063", Constantes.activo, new MenuOpcion(108), new Perfil(2)));
            permisos.add(new Permiso("PER022306000064", Constantes.activo, new MenuOpcion(109), new Perfil(2)));
            permisos.add(new Permiso("PER022306000065", Constantes.activo, new MenuOpcion(110), new Perfil(2)));
            //Indicadores
            permisos.add(new Permiso("PER022306000066", Constantes.activo, new MenuOpcion(114), new Perfil(2)));
            //Control
            permisos.add(new Permiso("PER022306000067", Constantes.inactivo, new MenuOpcion(115), new Perfil(2)));
            //Auditoría
            permisos.add(new Permiso("PER022306000068", Constantes.inactivo, new MenuOpcion(116), new Perfil(2)));
            //Tutoriales
            permisos.add(new Permiso("PER022306000069", Constantes.inactivo, new MenuOpcion(117), new Perfil(2)));
        //RECAUDADOR
            //Clientes
            permisos.add(new Permiso("PER032306000001", Constantes.inactivo, new MenuOpcion(1), new Perfil(3)));
            permisos.add(new Permiso("PER032306000002", Constantes.activo, new MenuOpcion(2), new Perfil(3)));
            permisos.add(new Permiso("PER032306000003", Constantes.inactivo, new MenuOpcion(13), new Perfil(3)));
            permisos.add(new Permiso("PER032306000004", Constantes.inactivo, new MenuOpcion(14), new Perfil(3)));
            permisos.add(new Permiso("PER032306000005", Constantes.inactivo, new MenuOpcion(15), new Perfil(3)));
            permisos.add(new Permiso("PER032306000006", Constantes.inactivo, new MenuOpcion(16), new Perfil(3)));
            permisos.add(new Permiso("PER032306000007", Constantes.inactivo, new MenuOpcion(17), new Perfil(3)));
            //Compras
            permisos.add(new Permiso("PER032306000008", Constantes.inactivo, new MenuOpcion(18), new Perfil(3)));
            permisos.add(new Permiso("PER032306000009", Constantes.activo, new MenuOpcion(19), new Perfil(3)));
            permisos.add(new Permiso("PER032306000010", Constantes.activo, new MenuOpcion(23), new Perfil(3)));
            permisos.add(new Permiso("PER032306000011", Constantes.activo, new MenuOpcion(25), new Perfil(3)));
            permisos.add(new Permiso("PER032306000012", Constantes.activo, new MenuOpcion(27), new Perfil(3)));
            //Ventas
            permisos.add(new Permiso("PER032306000013", Constantes.activo, new MenuOpcion(29), new Perfil(3)));
            permisos.add(new Permiso("PER032306000014", Constantes.activo, new MenuOpcion(30), new Perfil(3)));
            permisos.add(new Permiso("PER032306000015", Constantes.activo, new MenuOpcion(32), new Perfil(3)));
            permisos.add(new Permiso("PER032306000016", Constantes.inactivo, new MenuOpcion(34), new Perfil(3)));
            permisos.add(new Permiso("PER032306000017", Constantes.inactivo, new MenuOpcion(35), new Perfil(3)));
            permisos.add(new Permiso("PER032306000018", Constantes.inactivo, new MenuOpcion(36), new Perfil(3)));
            permisos.add(new Permiso("PER032306000019", Constantes.activo, new MenuOpcion(50), new Perfil(3)));
            permisos.add(new Permiso("PER032306000020", Constantes.inactivo, new MenuOpcion(51), new Perfil(3)));
            permisos.add(new Permiso("PER032306000021", Constantes.inactivo, new MenuOpcion(52), new Perfil(3)));
            //Inventarios
            permisos.add(new Permiso("PER032306000022", Constantes.inactivo, new MenuOpcion(54), new Perfil(3)));
            permisos.add(new Permiso("PER032306000023", Constantes.activo, new MenuOpcion(55), new Perfil(3)));
            permisos.add(new Permiso("PER032306000024", Constantes.activo, new MenuOpcion(58), new Perfil(3)));
            permisos.add(new Permiso("PER032306000025", Constantes.inactivo, new MenuOpcion(59), new Perfil(3)));
            permisos.add(new Permiso("PER032306000026", Constantes.inactivo, new MenuOpcion(60), new Perfil(3)));
            permisos.add(new Permiso("PER032306000027", Constantes.inactivo, new MenuOpcion(63), new Perfil(3)));
            //Caja Bancos
            //permisos.add(new Permiso("PER032306000028", Constantes.inactivo, new MenuOpcion(66), new Perfil(3)));
            //permisos.add(new Permiso("PER032306000029", Constantes.inactivo, new MenuOpcion(67), new Perfil(3)));
            //Cuenta Por Cobrar
            //permisos.add(new Permiso("PER032306000030", Constantes.inactivo, new MenuOpcion(68), new Perfil(3)));
            //Cuenta Por Pagar
            //permisos.add(new Permiso("PER032306000031", Constantes.inactivo, new MenuOpcion(69), new Perfil(3)));
            //Activos Fijos
            //permisos.add(new Permiso("PER032306000032", Constantes.inactivo, new MenuOpcion(70), new Perfil(3)));
            //Producción
            //permisos.add(new Permiso("PER032306000033", Constantes.inactivo, new MenuOpcion(71), new Perfil(3)));
            //Contabilidad
            //permisos.add(new Permiso("PER032306000034", Constantes.inactivo, new MenuOpcion(72), new Perfil(3)));
            //permisos.add(new Permiso("PER032306000035", Constantes.inactivo, new MenuOpcion(73), new Perfil(3)));
            //Talento Humano
            //permisos.add(new Permiso("PER032306000036", Constantes.inactivo, new MenuOpcion(75), new Perfil(3)));
            //Financiero
            //permisos.add(new Permiso("PER032306000037", Constantes.inactivo, new MenuOpcion(76), new Perfil(3)));
            //Importación
            //permisos.add(new Permiso("PER032306000038", Constantes.inactivo, new MenuOpcion(77), new Perfil(3)));
            //Reporte
            permisos.add(new Permiso("PER032306000039", Constantes.activo, new MenuOpcion(78), new Perfil(3)));
            permisos.add(new Permiso("PER032306000040", Constantes.activo, new MenuOpcion(79), new Perfil(3)));
            permisos.add(new Permiso("PER032306000041", Constantes.activo, new MenuOpcion(80), new Perfil(3)));
            permisos.add(new Permiso("PER032306000042", Constantes.activo, new MenuOpcion(81), new Perfil(3)));
            permisos.add(new Permiso("PER032306000043", Constantes.activo, new MenuOpcion(82), new Perfil(3)));
            permisos.add(new Permiso("PER032306000044", Constantes.activo, new MenuOpcion(83), new Perfil(3)));
            permisos.add(new Permiso("PER032306000045", Constantes.activo, new MenuOpcion(84), new Perfil(3)));
            permisos.add(new Permiso("PER032306000046", Constantes.activo, new MenuOpcion(85), new Perfil(3)));
            permisos.add(new Permiso("PER032306000047", Constantes.activo, new MenuOpcion(86), new Perfil(3)));
            permisos.add(new Permiso("PER032306000048", Constantes.activo, new MenuOpcion(87), new Perfil(3)));
            permisos.add(new Permiso("PER032306000049", Constantes.activo, new MenuOpcion(88), new Perfil(3)));
            permisos.add(new Permiso("PER032306000050", Constantes.activo, new MenuOpcion(89), new Perfil(3)));
            permisos.add(new Permiso("PER032306000051", Constantes.activo, new MenuOpcion(90), new Perfil(3)));
            //Accesos
            /*permisos.add(new Permiso("PER032306000052", Constantes.activo, new MenuOpcion(91), new Perfil(3)));
            permisos.add(new Permiso("PER032306000053", Constantes.activo, new MenuOpcion(92), new Perfil(3)));
            permisos.add(new Permiso("PER032306000054", Constantes.activo, new MenuOpcion(93), new Perfil(3)));
            permisos.add(new Permiso("PER032306000055", Constantes.activo, new MenuOpcion(97), new Perfil(3)));
            permisos.add(new Permiso("PER032306000056", Constantes.activo, new MenuOpcion(99), new Perfil(3)));
            permisos.add(new Permiso("PER032306000057", Constantes.activo, new MenuOpcion(100), new Perfil(3)));*/
            //Configuración
            /*permisos.add(new Permiso("PER032306000058", Constantes.activo, new MenuOpcion(102), new Perfil(3)));
            permisos.add(new Permiso("PER032306000059", Constantes.activo, new MenuOpcion(104), new Perfil(3)));
            permisos.add(new Permiso("PER032306000060", Constantes.activo, new MenuOpcion(105), new Perfil(3)));
            permisos.add(new Permiso("PER032306000061", Constantes.activo, new MenuOpcion(106), new Perfil(3)));
            permisos.add(new Permiso("PER032306000062", Constantes.activo, new MenuOpcion(107), new Perfil(3)));
            permisos.add(new Permiso("PER032306000063", Constantes.activo, new MenuOpcion(108), new Perfil(3)));
            permisos.add(new Permiso("PER032306000064", Constantes.activo, new MenuOpcion(109), new Perfil(3)));
            permisos.add(new Permiso("PER032306000065", Constantes.activo, new MenuOpcion(110), new Perfil(3)));*/
            //Indicadores
            //permisos.add(new Permiso("PER032306000066", Constantes.activo, new MenuOpcion(114), new Perfil(3)));
            //Control
            //permisos.add(new Permiso("PER032306000067", Constantes.inactivo, new MenuOpcion(115), new Perfil(3)));
            //Auditoría
            //permisos.add(new Permiso("PER032306000068", Constantes.inactivo, new MenuOpcion(116), new Perfil(3)));
            //Tutoriales
            //permisos.add(new Permiso("PER032306000069", Constantes.inactivo, new MenuOpcion(117), new Perfil(3)));

            rep.saveAll(permisos);
        }
    }
}
