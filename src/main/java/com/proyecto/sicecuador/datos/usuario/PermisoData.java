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
            permisos.add(new Permiso("PER202301000001", Constantes.activo, new MenuOpcion(4), new Perfil(1)));
            permisos.add(new Permiso("PER202301000002", Constantes.activo, new MenuOpcion(13), new Perfil(1)));
            permisos.add(new Permiso("PER202301000003", Constantes.activo, new MenuOpcion(9), new Perfil(1)));
            permisos.add(new Permiso("PER202301000004", Constantes.activo, new MenuOpcion(8), new Perfil(1)));
            permisos.add(new Permiso("PER202301000005", Constantes.activo, new MenuOpcion(10), new Perfil(1)));
            permisos.add(new Permiso("PER202301000006", Constantes.activo, new MenuOpcion(11), new Perfil(1)));
            permisos.add(new Permiso("PER202301000007", Constantes.activo, new MenuOpcion(1), new Perfil(1)));
            permisos.add(new Permiso("PER202301000008", Constantes.activo, new MenuOpcion(26), new Perfil(1)));
            permisos.add(new Permiso("PER202301000009", Constantes.activo, new MenuOpcion(19), new Perfil(1)));
            permisos.add(new Permiso("PER202301000010", Constantes.activo, new MenuOpcion(21), new Perfil(1)));
            permisos.add(new Permiso("PER202301000011", Constantes.activo, new MenuOpcion(22), new Perfil(1)));
            permisos.add(new Permiso("PER202301000011", Constantes.activo, new MenuOpcion(24), new Perfil(1)));
            permisos.add(new Permiso("PER202301000012", Constantes.activo, new MenuOpcion(82), new Perfil(1)));
            permisos.add(new Permiso("PER202301000013", Constantes.activo, new MenuOpcion(87), new Perfil(1)));
            permisos.add(new Permiso("PER202301000014", Constantes.activo, new MenuOpcion(88), new Perfil(1)));
            permisos.add(new Permiso("PER202301000015", Constantes.activo, new MenuOpcion(89), new Perfil(1)));
            permisos.add(new Permiso("PER202301000016", Constantes.activo, new MenuOpcion(85), new Perfil(1)));
            permisos.add(new Permiso("PER202301000016", Constantes.activo, new MenuOpcion(83), new Perfil(1)));
            permisos.add(new Permiso("PER202301000017", Constantes.activo, new MenuOpcion(91), new Perfil(1)));
            permisos.add(new Permiso("PER202301000018", Constantes.activo, new MenuOpcion(92), new Perfil(1)));
            permisos.add(new Permiso("PER202301000018", Constantes.activo, new MenuOpcion(93), new Perfil(1)));
            permisos.add(new Permiso("PER202301000019", Constantes.activo, new MenuOpcion(49), new Perfil(1)));
            permisos.add(new Permiso("PER202301000020", Constantes.activo, new MenuOpcion(53), new Perfil(1)));
            permisos.add(new Permiso("PER202301000021", Constantes.activo, new MenuOpcion(50), new Perfil(1)));
            permisos.add(new Permiso("PER202301000022", Constantes.activo, new MenuOpcion(54), new Perfil(1)));
            permisos.add(new Permiso("PER202301000023", Constantes.inactivo, new MenuOpcion(55), new Perfil(1)));
            permisos.add(new Permiso("PER202301000024", Constantes.inactivo, new MenuOpcion(56), new Perfil(1)));
            permisos.add(new Permiso("PER202301000025", Constantes.activo, new MenuOpcion(45), new Perfil(1)));
            permisos.add(new Permiso("PER202301000026", Constantes.activo, new MenuOpcion(51), new Perfil(1)));
            permisos.add(new Permiso("PER202301000027", Constantes.activo, new MenuOpcion(48), new Perfil(1)));
            permisos.add(new Permiso("PER202301000028", Constantes.activo, new MenuOpcion(94), new Perfil(1)));
            permisos.add(new Permiso("PER202301000029", Constantes.activo, new MenuOpcion(95), new Perfil(1)));
            permisos.add(new Permiso("PER202301000030", Constantes.activo, new MenuOpcion(96), new Perfil(1)));
            permisos.add(new Permiso("PER202301000031", Constantes.activo, new MenuOpcion(97), new Perfil(1)));
            permisos.add(new Permiso("PER202301000032", Constantes.activo, new MenuOpcion(98), new Perfil(1)));
            permisos.add(new Permiso("PER202301000033", Constantes.activo, new MenuOpcion(42), new Perfil(1)));
            permisos.add(new Permiso("PER202301000034", Constantes.activo, new MenuOpcion(43), new Perfil(1)));
            permisos.add(new Permiso("PER202301000035", Constantes.activo, new MenuOpcion(99), new Perfil(1)));
            permisos.add(new Permiso("PER202301000036", Constantes.activo, new MenuOpcion(100), new Perfil(1)));
            permisos.add(new Permiso("PER202301000037", Constantes.activo, new MenuOpcion(101), new Perfil(1)));
            permisos.add(new Permiso("PER202301000038", Constantes.activo, new MenuOpcion(102), new Perfil(1)));
            permisos.add(new Permiso("PER202301000039", Constantes.activo, new MenuOpcion(81), new Perfil(1)));
            permisos.add(new Permiso("PER202301000040", Constantes.activo, new MenuOpcion(73), new Perfil(1)));
            permisos.add(new Permiso("PER202301000041", Constantes.activo, new MenuOpcion(74), new Perfil(1)));
            permisos.add(new Permiso("PER202301000042", Constantes.activo, new MenuOpcion(75), new Perfil(1)));
            permisos.add(new Permiso("PER202301000043", Constantes.activo, new MenuOpcion(77), new Perfil(1)));
            permisos.add(new Permiso("PER202301000044", Constantes.activo, new MenuOpcion(76), new Perfil(1)));
            permisos.add(new Permiso("PER202301000045", Constantes.activo, new MenuOpcion(78), new Perfil(1)));
            permisos.add(new Permiso("PER202301000046", Constantes.activo, new MenuOpcion(38), new Perfil(1)));
            permisos.add(new Permiso("PER202301000047", Constantes.activo, new MenuOpcion(29), new Perfil(1)));
            permisos.add(new Permiso("PER202301000048", Constantes.activo, new MenuOpcion(31), new Perfil(1)));
            permisos.add(new Permiso("PER202301000049", Constantes.activo, new MenuOpcion(35), new Perfil(1)));
            permisos.add(new Permiso("PER202301000050", Constantes.activo, new MenuOpcion(37), new Perfil(1)));
            permisos.add(new Permiso("PER202301000051", Constantes.activo, new MenuOpcion(34), new Perfil(1)));
            permisos.add(new Permiso("PER202301000052", Constantes.activo, new MenuOpcion(39), new Perfil(1)));
            permisos.add(new Permiso("PER202301000053", Constantes.activo, new MenuOpcion(40), new Perfil(1)));
            permisos.add(new Permiso("PER202301000054", Constantes.activo, new MenuOpcion(44), new Perfil(1)));
            permisos.add(new Permiso("PER202301000055", Constantes.activo, new MenuOpcion(103), new Perfil(1)));
            permisos.add(new Permiso("PER202301000056", Constantes.activo, new MenuOpcion(104), new Perfil(1)));
            permisos.add(new Permiso("PER202301000057", Constantes.activo, new MenuOpcion(105), new Perfil(1)));
            permisos.add(new Permiso("PER202301000057", Constantes.activo, new MenuOpcion(59), new Perfil(1)));
            permisos.add(new Permiso("PER202301000058", Constantes.activo, new MenuOpcion(4), new Perfil(2)));
            permisos.add(new Permiso("PER202301000059", Constantes.activo, new MenuOpcion(13), new Perfil(2)));
            permisos.add(new Permiso("PER202301000060", Constantes.activo, new MenuOpcion(9), new Perfil(2)));
            permisos.add(new Permiso("PER202301000061", Constantes.activo, new MenuOpcion(8), new Perfil(2)));
            permisos.add(new Permiso("PER202301000062", Constantes.inactivo, new MenuOpcion(10), new Perfil(2)));
            permisos.add(new Permiso("PER202301000063", Constantes.inactivo, new MenuOpcion(11), new Perfil(2)));
            permisos.add(new Permiso("PER202301000064", Constantes.inactivo, new MenuOpcion(1), new Perfil(2)));
            permisos.add(new Permiso("PER202301000065", Constantes.activo, new MenuOpcion(26), new Perfil(2)));
            permisos.add(new Permiso("PER202301000066", Constantes.activo, new MenuOpcion(19), new Perfil(2)));
            permisos.add(new Permiso("PER202301000067", Constantes.activo, new MenuOpcion(21), new Perfil(2)));
            permisos.add(new Permiso("PER202301000068", Constantes.activo, new MenuOpcion(22), new Perfil(2)));
            permisos.add(new Permiso("PER202301000069", Constantes.activo, new MenuOpcion(82), new Perfil(2)));
            permisos.add(new Permiso("PER202301000070", Constantes.inactivo, new MenuOpcion(87), new Perfil(2)));
            permisos.add(new Permiso("PER202301000071", Constantes.inactivo, new MenuOpcion(88), new Perfil(2)));
            permisos.add(new Permiso("PER202301000072", Constantes.inactivo, new MenuOpcion(89), new Perfil(2)));
            permisos.add(new Permiso("PER202301000073", Constantes.inactivo, new MenuOpcion(85), new Perfil(2)));
            permisos.add(new Permiso("PER202301000074", Constantes.inactivo, new MenuOpcion(92), new Perfil(2)));
            permisos.add(new Permiso("PER202301000075", Constantes.inactivo, new MenuOpcion(93), new Perfil(2)));
            permisos.add(new Permiso("PER202301000076", Constantes.activo, new MenuOpcion(49), new Perfil(2)));
            permisos.add(new Permiso("PER202301000077", Constantes.activo, new MenuOpcion(53), new Perfil(2)));
            permisos.add(new Permiso("PER202301000078", Constantes.activo, new MenuOpcion(50), new Perfil(2)));
            permisos.add(new Permiso("PER202301000079", Constantes.inactivo, new MenuOpcion(54), new Perfil(2)));
            permisos.add(new Permiso("PER202301000080", Constantes.inactivo, new MenuOpcion(55), new Perfil(2)));
            permisos.add(new Permiso("PER202301000081", Constantes.inactivo, new MenuOpcion(56), new Perfil(2)));
            permisos.add(new Permiso("PER202301000082", Constantes.inactivo, new MenuOpcion(45), new Perfil(2)));
            permisos.add(new Permiso("PER202301000083", Constantes.activo, new MenuOpcion(51), new Perfil(2)));
            permisos.add(new Permiso("PER202301000084", Constantes.activo, new MenuOpcion(48), new Perfil(2)));

            rep.saveAll(permisos);
        }
    }
}
