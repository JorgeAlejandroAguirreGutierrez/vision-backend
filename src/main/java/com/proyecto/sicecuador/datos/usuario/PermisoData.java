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
            permisos.add(new Permiso("PER202301000011", "COMPRAS", "NOTAS DE CRÉDITO COMPRA", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000011", "COMPRAS", "NOTAS DE DÉBITO COMPRA", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000012", "VENTAS", "FACTURAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000013", "VENTAS", "PEDIDOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000014", "VENTAS", "PROFORMAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000015", "VENTAS", "EGRESOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000016", "VENTAS", "NOTAS DE CRÉDITO VENTA", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000016", "VENTAS", "NOTAS DE DÉBITO VENTA", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000017", "VENTAS", "GUIAS DE REMISION", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000018", "VENTAS", "TRANSPORTISTAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000018", "VENTAS", "VEHICULOS TRANSPORTES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000019", "INVENTARIOS", "GRUPOS DE PRODUCTOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000020", "INVENTARIOS", "PRODUCTOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000021", "INVENTARIOS", "KARDEX", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000022", "INVENTARIOS", "PROMOCIONES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000023", "INVENTARIOS", "PROVEEDORES DE PRODUCTOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000024", "INVENTARIOS", "TRANSFERENCIAS DE BODEGA", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000025", "INVENTARIOS", "BODEGAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000026", "INVENTARIOS", "MEDIDAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000027", "INVENTARIOS", "EQUIVALENCIA DE MEDIDAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000028", "CAJA BANCOS", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000029", "CUENTAS POR COBRAR", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000030", "CUENTAS POR PAGAR", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000031", "ACTIVOS FIJOS", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000032", "PRODUCCION", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000033", "CONTABILIDAD", "CUENTAS CONTABLES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000034", "CONTABILIDAD", "MOVIMIENTOS CONTABLES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000035", "TALENTO HUMANO", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000036", "FINANCIERO", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000037", "IMPORTACION", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000038", "REPORTES", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000039", "ACCESOS", "USUARIOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000040", "ACCESOS", "EMPRESAS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000041", "ACCESOS", "ESTABLECIMIENTOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000042", "ACCESOS", "ESTACIONES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000043", "ACCESOS", "PERFILES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000044", "ACCESOS", "ESTACIÓN USUARIO", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000045", "ACCESOS", "PERMISOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000046", "CONFIGURACION", "UBICACIÓN", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000047", "CONFIGURACION", "ESTADO CIVIL", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000048", "CONFIGURACION", "IMPUESTOS", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000049", "CONFIGURACION", "TIPOS DE PAGO", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000050", "CONFIGURACION", "TIPOS DE RETENCIÓN", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000051", "CONFIGURACION", "RÉGIMEN", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000052", "CONFIGURACION", "IMPORTACIONES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000053", "CONFIGURACION", "EXPORTACIONES", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000054", "ESTADISTICAS", "DASHBOARD", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000055", "CONTROL", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000056", "AUDITORIA", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000057", "TUTORIALES", "CREAR", Constantes.si, Constantes.activo, new Perfil(1)));
            permisos.add(new Permiso("PER202301000058", "CLIENTES", "CLIENTES", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000059", "CLIENTES", "SEGMENTOS", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000060", "CLIENTES", "GRUPOS DE CLIENTES", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000061", "CLIENTES", "FORMAS DE PAGO", Constantes.si, Constantes.activo,  new Perfil(2)));
            permisos.add(new Permiso("PER202301000062", "CLIENTES", "ORIGEN DE INGRESOS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000063", "CLIENTES", "PLAZOS DE CRÉDITO", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000064", "CLIENTES", "CALIFICACIÓN DE CLIENTES", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000065", "COMPRAS", "PROVEEDORES", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000066", "COMPRAS", "FACTURAS DE COMPRA", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000067", "COMPRAS", "GRUPOS DE PROVEEDORES", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000068", "COMPRAS", "NOTAS DE CRÉDITO COMPRA", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000069", "VENTAS", "FACTURAS", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000070", "VENTAS", "PEDIDOS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000071", "VENTAS", "PROFORMAS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000072", "VENTAS", "EGRESOS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000073", "VENTAS", "NOTAS DE CRÉDITO VENTA", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000074", "VENTAS", "TRANSPORTISTAS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000075", "VENTAS", "VEHICULO TRANSPORTE", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000076", "INVENTARIOS", "GRUPOS DE PRODUCTOS", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000077", "INVENTARIOS", "PRODUCTOS", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000078", "INVENTARIOS", "KARDEX", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000079", "INVENTARIOS", "PROMOCIONES", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000080", "INVENTARIOS", "PROVEEDORES DE PRODUCTOS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000081", "INVENTARIOS", "TRANSFERENCIAS DE BODEGA", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000082", "INVENTARIOS", "BODEGAS", Constantes.si, Constantes.inactivo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000083", "INVENTARIOS", "MEDIDAS", Constantes.si, Constantes.activo, new Perfil(2)));
            permisos.add(new Permiso("PER202301000084", "INVENTARIOS", "EQUIVALENCIA DE MEDIDAS", Constantes.si, Constantes.activo, new Perfil(2)));

            rep.saveAll(permisos);
        }
    }
}
