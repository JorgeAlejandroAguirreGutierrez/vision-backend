package com.proyecto.sicecuador.datos.usuario;

import com.proyecto.sicecuador.Constantes;
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
            permisos.add(new Permiso("PER1", "CLIENTES", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER2", "CLIENTES", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER3", "CLIENTES", "ACTUALIZAR", true, Constantes.activo,  new Perfil(1)));
            permisos.add(new Permiso("PER4", "CLIENTES", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER5", "PROVEEDORES", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER6", "PROVEEDORES", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER7", "PROVEEDORES", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER8", "PROVEEDORES", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER9", "FACTURACION", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER10", "FACTURACION", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER11", "FACTURACION", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER12", "FACTURACION", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER13", "CONTABILIDAD", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER14", "CONTABILIDAD", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER15", "CONTABILIDAD", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER16", "CONTABILIDAD", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER17", "FINANCIERO", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER18", "FINANCIERO", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER19", "FINANCIERO", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER21", "INVENTARIOS", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER22", "INVENTARIOS", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER23", "INVENTARIOS", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER24", "INVENTARIOS", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER25", "ACTIVOS_FIJOS", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER26", "ACTIVOS_FIJOS", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER27", "ACTIVOS_FIJOS", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER28", "ACTIVOS_FIJOS", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER29", "TALENTO_HUMANO", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER30", "TALENTO_HUMANO", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER31", "TALENTO_HUMANO", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER32", "TALENTO_HUMANO", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER33", "PRODUCCION", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER34", "PRODUCCION", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER35", "PRODUCCION", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER36", "PRODUCCION", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER37", "IMPORTACION", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER38", "IMPORTACION", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER39", "IMPORTACION", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER40", "IMPORTACION", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER41", "ESTADISTICAS", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER42", "ESTADISTICAS", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER43", "ESTADISTICAS", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER44", "ESTADISTICAS", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER45", "ORGANISMOS_CONTROL", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER46", "ORGANISMOS_CONTROL", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER47", "ORGANISMOS_CONTROL", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER48", "ORGANISMOS_CONTROL", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER49", "AUDITORIA", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER50", "AUDITORIA", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER51", "AUDITORIA", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER52", "AUDITORIA", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER53", "TUTORIALES", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER54", "TUTORIALES", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER55", "TUTORIALES", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER56", "TUTORIALES", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER57", "CONFIGURACIONES", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER58", "CONFIGURACIONES", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER59", "CONFIGURACIONES", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER60", "CONFIGURACIONES", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER61", "CLIENTES", "CREAR", true, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER62", "CLIENTES", "OBTENER", true, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER63", "CLIENTES", "ACTUALIZAR", true, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER64", "CLIENTES", "ELIMINAR", true, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER65", "PROVEEDORES", "CREAR", true, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER66", "PROVEEDORES", "OBTENER", true, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER67", "PROVEEDORES", "ACTUALIZAR", true, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER68", "PROVEEDORES", "ELIMINAR", true, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER69", "FACTURACION", "CREAR", true, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER70", "FACTURACION", "OBTENER", true, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER71", "FACTURACION", "ACTUALIZAR", true, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER72", "FACTURACION", "ELIMINAR", true, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER73", "USUARIOS", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER74", "USUARIOS", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER75", "USUARIOS", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER76", "USUARIOS", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER77", "CAJA_BANCOS", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER78", "CAJA_BANCOS", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER79", "CAJA_BANCOS", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER80", "CAJA_BANCOS", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER81", "CUENTAS_COBRAR", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER82", "CUENTAS_COBRAR", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER83", "CUENTAS_COBRAR", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER84", "CUENTAS_COBRAR", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER85", "CUENTAS_PAGAR", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER86", "CUENTAS_PAGAR", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER87", "CUENTAS_PAGAR", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER88", "CUENTAS_PAGAR", "ELIMINAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER89", "REPORTES", "CREAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER90", "REPORTES", "OBTENER", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER91", "REPORTES", "ACTUALIZAR", true, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER92", "REPORTES", "ELIMINAR", true, Constantes.activo, new Perfil(1)));

            rep.saveAll(permisos);
        }
    }
}
