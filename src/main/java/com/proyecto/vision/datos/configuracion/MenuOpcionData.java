package com.proyecto.vision.datos.configuracion;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.modelos.configuracion.MenuOpcion;
import com.proyecto.vision.repositorios.configuracion.IMenuOpcionRepository;
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
@Order(16)
@Profile({"dev","prod"})
public class MenuOpcionData implements ApplicationRunner {
    @Autowired
    private IMenuOpcionRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<MenuOpcion> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<MenuOpcion> opciones = new ArrayList<>();
            // CLIENTES
            opciones.add(new MenuOpcion("MEN202305000001", "CLIENTES", "GRUPOS DE CLIENTES","CREAR", Constantes.si, "grupo_cliente", "GCL", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000002", "CLIENTES", "CLIENTES","CREAR", Constantes.si, "cliente", "CLI", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000003", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "dependiente", "DEP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000004", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "celular", "CEL", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000005", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "celular_dependiente", "CED", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000006", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "correo", "COR", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000007", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "correo_dependiente", "COD", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000008", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "telefono", "TEL", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000009", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "telefono_dependiente", "TED", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000010", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "tipo_contribuyente", "TCO", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000011", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "retencion_cliente", "RCL", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000012", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "tipo_identificacion", "TID", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000013", "CLIENTES", "SEGMENTOS","CREAR", Constantes.si, "segmento", "SEG", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000014", "CLIENTES", "FORMAS DE PAGO","CREAR", Constantes.si, "forma_pago", "FPA", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000015", "CLIENTES", "ORIGEN DE INGRESOS","CREAR", Constantes.si, "origen_ingreso", "OIN", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000016", "CLIENTES", "PLAZOS DE CRÉDITO","CREAR", Constantes.si, "plazo_credito", "PCR", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000017", "CLIENTES", "CALIFICACIÓN DE CLIENTES","CREAR", Constantes.si, "calificacion_cliente", "CCL", Constantes.estadoActivo));
            //COMPRAS
            opciones.add(new MenuOpcion("MEN202305000018", "COMPRAS", "GRUPOS DE PROVEEDORES","CREAR", Constantes.si, "grupo_proveedor", "GPR", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000019", "COMPRAS", "PROVEEDORES","CREAR", Constantes.si, "proveedor", "PRV", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000020", "COMPRAS", "PROVEEDORES","CREAR", Constantes.no, "celular_proveedor", "CEP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000021", "COMPRAS", "PROVEEDORES","CREAR", Constantes.no, "correo_proveedor", "COP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000022", "COMPRAS", "PROVEEDORES","CREAR", Constantes.no, "telefono_proveedor", "TEP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000023", "COMPRAS", "FACTURAS DE COMPRA","CREAR", Constantes.si, "factura_compra", "FCO", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000024", "COMPRAS", "FACTURAS DE COMPRA","CREAR", Constantes.no, "factura_compra_linea", "FCL", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000025", "COMPRAS", "NOTAS DE CRÉDITO COMPRA","CREAR", Constantes.si, "nota_credito_compra", "NCC", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000026", "COMPRAS", "NOTAS DE CRÉDITO COMPRA","CREAR", Constantes.no, "nota_credito_compra_linea", "NCCL", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000027", "COMPRAS", "NOTAS DE DÉBITO COMPRA","CREAR", Constantes.si, "nota_debito_compra", "NDC", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000028", "COMPRAS", "NOTAS DE DÉBITO COMPRA","CREAR", Constantes.no, "nota_debito_compra_linea", "NDCL", Constantes.estadoActivo));
            //VENTAS
            opciones.add(new MenuOpcion("MEN202305000029", "VENTAS", "FACTURAS","CREAR", Constantes.si, "factura", "FAC", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000030", "VENTAS", "NOTAS DE DÉBITO VENTA","CREAR", Constantes.si, "nota_debito_venta", "NDV", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000031", "VENTAS", "NOTAS DE DÉBITO VENTA","CREAR", Constantes.no, "nota_debito_venta_linea", "NDVL", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000032", "VENTAS", "NOTAS DE CRÉDITO VENTA","CREAR", Constantes.si, "nota_credito_venta", "NCV", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000033", "VENTAS", "NOTAS DE CRÉDITO VENTA","CREAR", Constantes.no, "nota_credito_venta_linea", "NCVE", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000034", "VENTAS", "PEDIDO","CREAR", Constantes.si, "pedido", "PED", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000035", "VENTAS", "PROFORMA","CREAR", Constantes.si, "proforma", "PRF", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000036", "VENTAS", "EGRESO","CREAR", Constantes.si, "egreso", "EGR", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000037", "VENTAS", "CIERRE DE CAJA","CREAR", Constantes.si, "cierre_caja", "CIC", Constantes.estadoActivo));
            //RECAUDACIÓN
            opciones.add(new MenuOpcion("MEN202305000038", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "amortizacion", "AMO", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000039", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "cheque_vista", "CHV", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000040", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "cheque_posfechado", "CHP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000041", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "credito", "CRE", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000042", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "retencion", "CTP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000043", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "deposito", "DEP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000044", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "efectivo", "EFE", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000045", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "tarjeta_credito", "TCR", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000046", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "tarjeta_debito", "TDE", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000047", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "recaudacion", "REC", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000048", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "transferencia", "TRF", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000049", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "retencion_venta", "RVE", Constantes.estadoActivo));
            //ENTREGA
            opciones.add(new MenuOpcion("MEN202305000050", "VENTAS", "ENTREGA","CREAR", Constantes.no, "entrega", "ENT", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000051", "VENTAS", "GUIAS DE REMISION","CREAR", Constantes.si, "guia_remision", "GRE", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000052", "VENTAS", "TRANSPORTISTAS","CREAR", Constantes.si, "transportista", "TRAN", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000053", "VENTAS", "VEHICULOS TRANSPORTES","CREAR", Constantes.si, "vehiculo_transporte", "VEH", Constantes.estadoActivo));
            //INVENTARIOS
            opciones.add(new MenuOpcion("MEN202305000054", "INVENTARIOS", "CATEGORÍA PRODUCTO","CREAR", Constantes.no, "categoria_producto", "CAP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000055", "INVENTARIOS", "GRUPOS DE PRODUCTOS","CREAR", Constantes.si, "grupo_producto", "GPR", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000056", "INVENTARIOS", "PRODUCTOS","CREAR", Constantes.si, "producto", "PRO", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000057", "INVENTARIOS", "PRECIO","CREAR", Constantes.no, "precio", "PRE", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000058", "INVENTARIOS", "PROVEEDORES DE PRODUCTOS","CREAR", Constantes.no, "proveedor_producto", "PRP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000059", "INVENTARIOS", "KARDEX","CREAR", Constantes.si, "kardex", "KAR", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000060", "INVENTARIOS", "PROMOCIONES","CREAR", Constantes.si, "promocion", "PRM", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000061", "INVENTARIOS", "BODEGAS","CREAR", Constantes.si, "bodega", "BOD", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000062", "INVENTARIOS", "BODEGAS","CREAR", Constantes.no, "bodega_producto", "BOP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000063", "INVENTARIOS", "TRANSFERENCIAS DE BODEGA","CREAR", Constantes.no, "transferencia_bodega", "TRB", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000064", "INVENTARIOS", "MEDIDAS","CREAR", Constantes.si, "medida", "MED", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000065", "INVENTARIOS", "EQUIVALENCIA DE MEDIDAS","CREAR", Constantes.no, "equivalencia_medida", "EQM", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000066", "INVENTARIOS", "TIPO GASTO","CREAR", Constantes.no, "tipo_gasto", "TIG", Constantes.estadoActivo));
            //CAJA BANCOS
            opciones.add(new MenuOpcion("MEN202305000067", "CAJA BANCOS", "CUENTAS PROPIAS","CREAR", Constantes.si, "cuenta_propia", "CTP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000068", "CAJA BANCOS", "BANCOS","CREAR", Constantes.si, "banco", "BAN", Constantes.estadoActivo));
            //CUENTAS X COBRAR
            opciones.add(new MenuOpcion("MEN202305000069", "CUENTAS POR COBRAR", "CUENTAS POR COBRAR","CREAR", Constantes.no, "cuentas_cobrar", "CXC", Constantes.estadoActivo));
            //CUENTAS X PAGAR
            opciones.add(new MenuOpcion("MEN202305000070", "CUENTAS POR PAGAR", "CUENTAS POR PAGAR","CREAR", Constantes.no, "cuentas_pagar", "CXP", Constantes.estadoActivo));
            //ACTIVOS FIJOS
            opciones.add(new MenuOpcion("MEN202305000071", "ACTIVOS FIJOS", "ACTIVOS FIJOS","CREAR", Constantes.no, "activos_fijos", "ACF", Constantes.estadoActivo));
            //PRODUCCIÓN
            opciones.add(new MenuOpcion("MEN202305000072", "PRODUCCION", "PRODUCCION","CREAR", Constantes.no, "produccion", "PDC", Constantes.estadoActivo));
            //CONTABILIDAD
            opciones.add(new MenuOpcion("MEN202305000073", "CONTABILIDAD", "CUENTAS CONTABLES","CREAR", Constantes.si, "cuenta_contable", "CTC", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000074", "CONTABILIDAD", "MOVIMIENTOS CONTABLES","CREAR", Constantes.si, "movimiento_contable", "MVC", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000075", "CONTABILIDAD", "AFECTACION CONTABLE","CREAR", Constantes.no, "afectacion_contable", "AFC", Constantes.estadoActivo));
            //TALENTO HUMANO
            opciones.add(new MenuOpcion("MEN202305000076", "TALENTO HUMANO", "TALENTO HUMANO","CREAR", Constantes.no, "talento_humano", "TTH", Constantes.estadoActivo));
            //FINANCIERO
            opciones.add(new MenuOpcion("MEN202305000077", "FINANCIERO", "FINANCIERO","CREAR", Constantes.no, "financiero", "FIN", Constantes.estadoActivo));
            //IMPORTACIÓN
            opciones.add(new MenuOpcion("MEN202305000078", "IMPORTACION", "IMPORTACION","CREAR", Constantes.no, "importacion", "IMT", Constantes.estadoActivo));
            //REPORTES
            opciones.add(new MenuOpcion("MEN202305000079", "REPORTES", "CLIENTE","CREAR", Constantes.si, "", "REP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000080", "REPORTES", "COMPRAS","CREAR", Constantes.si, "", "REP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000081", "REPORTES", "VENTAS","CREAR", Constantes.si, "", "REP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000082", "REPORTES", "INVENTARIOS","CREAR", Constantes.si, "", "REP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000083", "REPORTES", "CAJA BANCOS","CREAR", Constantes.si, "", "REP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000084", "REPORTES", "CUENTAS POR COBRAR","CREAR", Constantes.si, "", "REP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000085", "REPORTES", "CUENTAS POR PAGAR","CREAR", Constantes.si, "", "REP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000086", "REPORTES", "ACTIVOS FIJOS","CREAR", Constantes.si, "", "REP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000087", "REPORTES", "PRODUCCIÓN","CREAR", Constantes.si, "", "REP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000088", "REPORTES", "CONTABILIDAD","CREAR", Constantes.si, "", "REP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000089", "REPORTES", "TALENTO HUMANO","CREAR", Constantes.si, "", "REP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000090", "REPORTES", "FINANCIERO","CREAR", Constantes.si, "", "REP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000091", "REPORTES", "IMPORTACIÓN","CREAR", Constantes.si, "", "REP", Constantes.estadoActivo));
            // ACCESOS
            opciones.add(new MenuOpcion("MEN202305000092", "ACCESOS", "USUARIOS","CREAR", Constantes.si, "usuario", "USR", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000093", "ACCESOS", "EMPRESAS","CREAR", Constantes.si, "empresa", "EMP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000094", "ACCESOS", "ESTABLECIMIENTOS","CREAR", Constantes.si, "establecimiento", "EST", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000095", "ACCESOS", "ESTABLECIMIENTOS","CREAR", Constantes.no, "celular_establecimiento", "CEE", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000096", "ACCESOS", "ESTABLECIMIENTOS","CREAR", Constantes.no, "correo_establecimiento", "COE", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000097", "ACCESOS", "ESTABLECIMIENTOS","CREAR", Constantes.no, "telefono_establecimiento", "TEE", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000098", "ACCESOS", "ESTACIONES","CREAR", Constantes.si, "estacion", "ESN", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000099", "ACCESOS", "ESTACIÓN USUARIO","CREAR", Constantes.no, "estacion_usuario", "ESU", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000100", "ACCESOS", "PERFILES","CREAR", Constantes.si, "perfil", "PER", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000101", "ACCESOS", "PERMISOS","CREAR", Constantes.si, "permiso", "PRM", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000102", "ACCESOS", "SESION","CREAR", Constantes.no, "sesion", "SES", Constantes.estadoActivo));
            //CONFIGURACIÓN
            opciones.add(new MenuOpcion("MEN202305000103", "CONFIGURACION", "UBICACIÓN","CREAR", Constantes.si, "ubicacion", "UBI", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000104", "CONFIGURACION", "UBICACIÓN","CREAR", Constantes.no, "direccion", "DIR", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000105", "CONFIGURACION", "ESTADO CIVIL","CREAR", Constantes.si, "estado_civil", "ECV", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000106", "CONFIGURACION", "IMPUESTOS","CREAR", Constantes.si, "impuesto", "IMP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000107", "CONFIGURACION", "SECUENCIALES","CREAR", Constantes.si, "secuencial", "SEC", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000108", "CONFIGURACION", "TIPOS DE RETENCIÓN","CREAR", Constantes.si, "tipo_retencion", "TRE", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000109", "CONFIGURACION", "RÉGIMEN","CREAR", Constantes.si, "regimen", "REG", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000110", "CONFIGURACION", "IMPORTACIONES","CREAR", Constantes.si, "", "IMP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000111", "CONFIGURACION", "EXPORTACIONES","CREAR", Constantes.si, "", "EXP", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000112", "CONFIGURACION", "MENU","CREAR", Constantes.no, "menu", "MEN", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000113", "CONFIGURACION", "PARAMETROS","CREAR", Constantes.no, "parametro", "PAR", Constantes.estadoActivo));
            opciones.add(new MenuOpcion("MEN202305000114", "CONFIGURACION", "GENERO","CREAR", Constantes.no, "genero", "GEN", Constantes.estadoActivo));
            //INDICADORES
            opciones.add(new MenuOpcion("MEN202305000115", "INDICADORES", "DASHBOARD","CREAR", Constantes.si, "dashboard", "DAS", Constantes.estadoActivo));
            //CONTROL
            opciones.add(new MenuOpcion("MEN202305000116", "CONTROL", "CONTROL","CREAR", Constantes.no, "produccion", "PDC", Constantes.estadoActivo));
            //AUDITORÍA
            opciones.add(new MenuOpcion("MEN202305000117", "AUDITORIA", "AUDITORIA","CREAR", Constantes.no, "produccion", "PDC", Constantes.estadoActivo));
            //TUTORIALES
            opciones.add(new MenuOpcion("MEN202305000118", "TUTORIALES", "TUTORIALES","CREAR", Constantes.no, "produccion", "PDC", Constantes.estadoActivo));

            rep.saveAll(opciones);
        }
    }
}
