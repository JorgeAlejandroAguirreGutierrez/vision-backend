package com.proyecto.sicecuador.datos.usuario;

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
            permisos.add(new Permiso("PER1", "CLIENTES", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER2", "CLIENTES", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER3", "CLIENTES", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER4", "CLIENTES", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER5", "PROVEEDORES", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER6", "PROVEEDORES", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER7", "PROVEEDORES", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER8", "PROVEEDORES", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER9", "FACTURACION", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER10", "FACTURACION", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER11", "FACTURACION", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER12", "FACTURACION", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER13", "CONTABILIDAD", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER14", "CONTABILIDAD", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER15", "CONTABILIDAD", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER16", "CONTABILIDAD", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER17", "FINANCIERO", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER18", "FINANCIERO", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER19", "FINANCIERO", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER21", "INVENTARIOS", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER22", "INVENTARIOS", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER23", "INVENTARIOS", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER24", "INVENTARIOS", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER25", "ACTIVOS_FIJOS", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER26", "ACTIVOS_FIJOS", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER27", "ACTIVOS_FIJOS", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER28", "ACTIVOS_FIJOS", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER29", "TALENTO_HUMANO", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER30", "TALENTO_HUMANO", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER31", "TALENTO_HUMANO", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER32", "TALENTO_HUMANO", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER33", "PRODUCCION", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER34", "PRODUCCION", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER35", "PRODUCCION", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER36", "PRODUCCION", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER37", "IMPORTACION", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER38", "IMPORTACION", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER39", "IMPORTACION", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER40", "IMPORTACION", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER41", "ESTADISTICAS", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER42", "ESTADISTICAS", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER43", "ESTADISTICAS", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER44", "ESTADISTICAS", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER45", "ORGANISMOS_CONTROL", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER46", "ORGANISMOS_CONTROL", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER47", "ORGANISMOS_CONTROL", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER48", "ORGANISMOS_CONTROL", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER49", "AUDITORIA", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER50", "AUDITORIA", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER51", "AUDITORIA", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER52", "AUDITORIA", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER53", "TUTORIALES", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER54", "TUTORIALES", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER55", "TUTORIALES", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER56", "TUTORIALES", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER57", "CONFIGURACIONES", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER58", "CONFIGURACIONES", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER59", "CONFIGURACIONES", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER60", "CONFIGURACIONES", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER61", "CLIENTES", "CREAR", true, new Perfil(2)));
            permisos.add(new Permiso("PER62", "CLIENTES", "OBTENER", true, new Perfil(2)));
            permisos.add(new Permiso("PER63", "CLIENTES", "ACTUALIZAR", true, new Perfil(2)));
            permisos.add(new Permiso("PER64", "CLIENTES", "ELIMINAR", true, new Perfil(2)));
            permisos.add(new Permiso("PER65", "PROVEEDORES", "CREAR", true, new Perfil(2)));
            permisos.add(new Permiso("PER66", "PROVEEDORES", "OBTENER", true, new Perfil(2)));
            permisos.add(new Permiso("PER67", "PROVEEDORES", "ACTUALIZAR", true, new Perfil(2)));
            permisos.add(new Permiso("PER68", "PROVEEDORES", "ELIMINAR", true, new Perfil(2)));
            permisos.add(new Permiso("PER69", "FACTURACION", "CREAR", true, new Perfil(2)));
            permisos.add(new Permiso("PER70", "FACTURACION", "OBTENER", true, new Perfil(2)));
            permisos.add(new Permiso("PER71", "FACTURACION", "ACTUALIZAR", true, new Perfil(2)));
            permisos.add(new Permiso("PER72", "FACTURACION", "ELIMINAR", true, new Perfil(2)));
            permisos.add(new Permiso("PER73", "USUARIOS", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER74", "USUARIOS", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER75", "USUARIOS", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER76", "USUARIOS", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER77", "CAJA_BANCOS", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER78", "CAJA_BANCOS", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER79", "CAJA_BANCOS", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER80", "CAJA_BANCOS", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER81", "CUENTAS_COBRAR", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER82", "CUENTAS_COBRAR", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER83", "CUENTAS_COBRAR", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER84", "CUENTAS_COBRAR", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER85", "CUENTAS_PAGAR", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER86", "CUENTAS_PAGAR", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER87", "CUENTAS_PAGAR", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER88", "CUENTAS_PAGAR", "ELIMINAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER89", "REPORTES", "CREAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER90", "REPORTES", "OBTENER", true, new Perfil(1)));
            permisos.add(new Permiso("PER91", "REPORTES", "ACTUALIZAR", true, new Perfil(1)));
            permisos.add(new Permiso("PER92", "REPORTES", "ELIMINAR", true, new Perfil(1)));

            rep.saveAll(permisos);
        }
    }
}
