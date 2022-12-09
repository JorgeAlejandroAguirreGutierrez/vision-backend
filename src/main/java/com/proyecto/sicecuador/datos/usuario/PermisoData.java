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

            permisos.add(new Permiso("PER1", "CLIENTES", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER2", "CLIENTES", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER3", "CLIENTES", "ACTUALIZAR", Constantes.si, Constantes.activo,  new Perfil(1)));
            permisos.add(new Permiso("PER4", "CLIENTES", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER5", "PROVEEDORES", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER6", "PROVEEDORES", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER7", "PROVEEDORES", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER8", "PROVEEDORES", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER9", "FACTURACION", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER10", "FACTURACION", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER11", "FACTURACION", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER12", "FACTURACION", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER13", "CONTABILIDAD", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER14", "CONTABILIDAD", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER15", "CONTABILIDAD", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER16", "CONTABILIDAD", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER17", "FINANCIERO", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER18", "FINANCIERO", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER19", "FINANCIERO", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER21", "INVENTARIOS", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER22", "INVENTARIOS", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER23", "INVENTARIOS", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER24", "INVENTARIOS", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER25", "ACTIVOS_FIJOS", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER26", "ACTIVOS_FIJOS", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER27", "ACTIVOS_FIJOS", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER28", "ACTIVOS_FIJOS", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER29", "TALENTO_HUMANO", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER30", "TALENTO_HUMANO", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER31", "TALENTO_HUMANO", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER32", "TALENTO_HUMANO", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER33", "PRODUCCION", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER34", "PRODUCCION", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER35", "PRODUCCION", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER36", "PRODUCCION", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER37", "IMPORTACION", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER38", "IMPORTACION", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER39", "IMPORTACION", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER40", "IMPORTACION", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER41", "ESTADISTICAS", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER42", "ESTADISTICAS", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER43", "ESTADISTICAS", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER44", "ESTADISTICAS", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER45", "ORGANISMOS_CONTROL", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER46", "ORGANISMOS_CONTROL", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER47", "ORGANISMOS_CONTROL", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER48", "ORGANISMOS_CONTROL", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER49", "AUDITORIA", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER50", "AUDITORIA", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER51", "AUDITORIA", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER52", "AUDITORIA", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER53", "TUTORIALES", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER54", "TUTORIALES", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER55", "TUTORIALES", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER56", "TUTORIALES", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER57", "CONFIGURACIONES", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER58", "CONFIGURACIONES", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER59", "CONFIGURACIONES", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER60", "CONFIGURACIONES", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER61", "CLIENTES", "CREAR", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER62", "CLIENTES", "OBTENER", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER63", "CLIENTES", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER64", "CLIENTES", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER65", "PROVEEDORES", "CREAR", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER66", "PROVEEDORES", "OBTENER", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER67", "PROVEEDORES", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER68", "PROVEEDORES", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER69", "FACTURACION", "CREAR", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER70", "FACTURACION", "OBTENER", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER71", "FACTURACION", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER72", "FACTURACION", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER73", "USUARIOS", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER74", "USUARIOS", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER75", "USUARIOS", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER76", "USUARIOS", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER77", "CAJA_BANCOS", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER78", "CAJA_BANCOS", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER79", "CAJA_BANCOS", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER80", "CAJA_BANCOS", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER81", "CUENTAS_COBRAR", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER82", "CUENTAS_COBRAR", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER83", "CUENTAS_COBRAR", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER84", "CUENTAS_COBRAR", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER85", "CUENTAS_PAGAR", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER86", "CUENTAS_PAGAR", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER87", "CUENTAS_PAGAR", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER88", "CUENTAS_PAGAR", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER89", "REPORTES", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER90", "REPORTES", "OBTENER", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER91", "REPORTES", "ACTUALIZAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER92", "REPORTES", "ELIMINAR", Constantes.si, Constantes.activo, new Perfil(1)));
            rep.saveAll(permisos);
        }
    }
}
