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
            permisos.add(new Permiso("PER202301000001", "CLIENTES", "CLIENTES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000002", "CLIENTES", "SEGMENTOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000003", "CLIENTES", "GRUPOS DE CLIENTES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000004", "CLIENTES", "FORMAS DE PAGO", Constantes.si, Constantes.activo,  new Perfil(1)));
            permisos.add(new Permiso("PER202301000005", "CLIENTES", "ORIGEN DE INGRESOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000006", "CLIENTES", "PLAZOS DE CRÉDITO", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000007", "CLIENTES", "CALIFICACIÓN DE CLIENTES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000008", "COMPRAS", "PROVEEDORES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000009", "COMPRAS", "FACTURAS DE COMPRA", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000010", "COMPRAS", "GRUPOS DE PROVEEDORES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000011", "VENTAS", "FACTURAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000012", "VENTAS", "PEDIDOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000013", "VENTAS", "PROFORMAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000014", "VENTAS", "EGRESOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000015", "VENTAS", "TRANSPORTISTAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000016", "VENTAS", "VEHICULOS TRANSPORTES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000017", "INVENTARIOS", "GRUPOS DE PRODUCTOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000018", "INVENTARIOS", "PRODUCTOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000019", "INVENTARIOS", "KARDEX", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000020", "INVENTARIOS", "PROMOCIONES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000021", "INVENTARIOS", "PROVEEDORES DE PRODUCTOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000022", "INVENTARIOS", "TRANSFERENCIAS DE BODEGA", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000023", "INVENTARIOS", "BODEGAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000024", "INVENTARIOS", "MEDIDAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000025", "INVENTARIOS", "EQUIVALENCIA DE MEDIDAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000026", "CAJA_BANCOS", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000027", "CUENTAS_COBRAR", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000028", "CUENTAS_PAGAR", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000029", "ACTIVOS_FIJOS", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000030", "PRODUCCION", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000031", "CONTABILIDAD", "CUENTAS CONTABLES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000032", "CONTABILIDAD", "MOVIMIENTOS CONTABLES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000033", "TALENTO_HUMANO", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000034", "FINANCIERO", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000035", "IMPORTACION", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000036", "REPORTES", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000037", "ACCESOS", "USUARIOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000038", "ACCESOS", "EMPRESAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000039", "ACCESOS", "ESTABLECIMIENTOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000040", "ACCESOS", "ESTACIONES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000041", "ACCESOS", "PERFILES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000042", "ACCESOS", "ESTACIÓN USUARIO", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000043", "ACCESOS", "PERMISOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000044", "CONFIGURACIONES", "UBICACIÓN", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000045", "CONFIGURACIONES", "ESTADO CIVIL", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000046", "CONFIGURACIONES", "IMPUESTOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000047", "CONFIGURACIONES", "TIPOS DE PAGO", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000048", "CONFIGURACIONES", "TIPOS DE RETENCIÓN", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000049", "CONFIGURACIONES", "GENEROS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000050", "CONFIGURACIONES", "IMPORTACIONES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000051", "CONFIGURACIONES", "EXPORTACIONES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000052", "ESTADISTICAS", "DASHBOARD", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000053", "ORGANISMOS_CONTROL", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000054", "AUDITORIA", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000055", "TUTORIALES", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000056", "CLIENTES", "CLIENTES", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000057", "CLIENTES", "SEGMENTOS", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000058", "CLIENTES", "GRUPOS DE CLIENTES", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000059", "CLIENTES", "FORMAS DE PAGO", Constantes.si, Constantes.activo,  new Perfil(2)));
            permisos.add(new Permiso("PER202301000060", "CLIENTES", "ORIGEN DE INGRESOS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000061", "CLIENTES", "PLAZOS DE CRÉDITO", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000062", "CLIENTES", "CALIFICACIÓN DE CLIENTES", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000063", "COMPRAS", "PROVEEDORES", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000064", "COMPRAS", "FACTURAS DE COMPRA", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000065", "COMPRAS", "GRUPOS DE PROVEEDORES", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000066", "VENTAS", "FACTURAS", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000067", "VENTAS", "PEDIDOS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000068", "VENTAS", "PROFORMAS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000069", "VENTAS", "EGRESOS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000070", "VENTAS", "TRANSPORTISTAS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000071", "VENTAS", "VEHICULOS TRANSPORTES", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000072", "INVENTARIOS", "GRUPOS DE PRODUCTOS", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000073", "INVENTARIOS", "PRODUCTOS", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000074", "INVENTARIOS", "KARDEX", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000075", "INVENTARIOS", "PROMOCIONES", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000076", "INVENTARIOS", "PROVEEDORES DE PRODUCTOS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000077", "INVENTARIOS", "TRANSFERENCIAS DE BODEGA", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000078", "INVENTARIOS", "BODEGAS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000079", "INVENTARIOS", "MEDIDAS", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000080", "INVENTARIOS", "EQUIVALENCIA DE MEDIDAS", Constantes.si, Constantes.activo, new Perfil(2)));

            rep.saveAll(permisos);
        }
    }
}
