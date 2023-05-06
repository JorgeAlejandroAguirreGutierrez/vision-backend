package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.configuracion.MenuOpcion;
import com.proyecto.sicecuador.repositorios.configuracion.IMenuOpcionRepository;
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
            opciones.add(new MenuOpcion("MEN202305000001", "CLIENTES", "CALIFICACIÓN DE CLIENTES","CREAR", Constantes.si, "calificacion_cliente", "CCL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000002", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "celular", "CEL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000003", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "celular_dependiente", "CED", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000004", "CLIENTES", "CLIENTES","CREAR", Constantes.si, "cliente", "CLI", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000005", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "correo", "COR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000006", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "correo_dependiente", "COD", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000007", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "dependiente", "DEP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000008", "CLIENTES", "FORMAS DE PAGO","CREAR", Constantes.si, "forma_pago", "FPA", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000009", "CLIENTES", "GRUPOS DE CLIENTES","CREAR", Constantes.si, "grupo_cliente", "GCL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000010", "CLIENTES", "ORIGEN DE INGRESOS","CREAR", Constantes.si, "origen_ingreso", "OIN", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000011", "CLIENTES", "PLAZOS DE CRÉDITO","CREAR", Constantes.si, "plazo_credito", "PCR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000012", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "retencion_cliente", "RCL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000013", "CLIENTES", "SEGMENTOS","CREAR", Constantes.si, "segmento", "SEG", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000014", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "telefono", "TEL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000015", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "telefono_dependiente", "TED", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000016", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "tipo_contribuyente", "TCO", Constantes.activo));
            //COMPRAS
            opciones.add(new MenuOpcion("MEN202305000017", "COMPRAS", "PROVEEDORES","CREAR", Constantes.no, "celular_proveedor", "CEP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000018", "COMPRAS", "PROVEEDORES","CREAR", Constantes.no, "correo_proveedor", "COP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000019", "COMPRAS", "FACTURAS DE COMPRA","CREAR", Constantes.si, "factura_compra", "FCO", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000020", "COMPRAS", "FACTURAS DE COMPRA","CREAR", Constantes.no, "factura_compra_linea", "FCL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000021", "COMPRAS", "GRUPOS DE PROVEEDORES","CREAR", Constantes.si, "grupo_proveedor", "GPR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000022", "COMPRAS", "NOTAS DE CRÉDITO COMPRA","CREAR", Constantes.si, "nota_credito_compra", "NCC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000023", "COMPRAS", "NOTAS DE CRÉDITO COMPRA","CREAR", Constantes.no, "nota_credito_compra_linea", "NCCL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000024", "COMPRAS", "NOTAS DE DÉBITO COMPRA","CREAR", Constantes.si, "nota_debito_compra", "NDC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000025", "COMPRAS", "NOTAS DE DÉBITO COMPRA","CREAR", Constantes.no, "nota_debito_compra_linea", "NDCL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000026", "COMPRAS", "PROVEEDORES","CREAR", Constantes.si, "proveedor", "PRV", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000027", "COMPRAS", "PROVEEDORES","CREAR", Constantes.no, "telefono_proveedor", "TEP", Constantes.activo));
            //CONFIGURACIÓN
            opciones.add(new MenuOpcion("MEN202305000028", "CONFIGURACION", "UBICACIÓN","CREAR", Constantes.no, "direccion", "DIR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000029", "CONFIGURACION", "ESTADO CIVIL","CREAR", Constantes.si, "estado_civil", "ECV", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000030", "CONFIGURACION", "GENERO","CREAR", Constantes.no, "genero", "GEN", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000031", "CONFIGURACION", "IMPUESTOS","CREAR", Constantes.si, "impuesto", "IMP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000032", "CONFIGURACION", "MENU","CREAR", Constantes.no, "menu", "MEN", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000033", "CONFIGURACION", "PARAMETROS","CREAR", Constantes.no, "parametro", "PAR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000034", "CONFIGURACION", "RÉGIMEN","CREAR", Constantes.si, "regimen", "REG", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000035", "CONFIGURACION", "SECUENCIALES","CREAR", Constantes.si, "secuencial", "SEC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000036", "CONFIGURACION", "TIPO IDENTIFICACIÓN","CREAR", Constantes.no, "tipo_identificacion", "TID", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000037", "CONFIGURACION", "TIPOS DE RETENCIÓN","CREAR", Constantes.si, "tipo_retencion", "TRE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000038", "CONFIGURACION", "UBICACIÓN","CREAR", Constantes.si, "ubicacion", "UBI", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000039", "CONFIGURACION", "IMPORTACIONES","CREAR", Constantes.si, "", "IMP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000040", "CONFIGURACION", "EXPORTACIONES","CREAR", Constantes.si, "", "EXP", Constantes.activo));
            //CONTABILIDAD
            opciones.add(new MenuOpcion("MEN202305000041", "CONTABILIDAD", "AFECTACION CONTABLE","CREAR", Constantes.no, "afectacion_contable", "AFC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000042", "CONTABILIDAD", "CUENTAS CONTABLES","CREAR", Constantes.si, "cuenta_contable", "CTC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000043", "CONTABILIDAD", "MOVIMIENTOS CONTABLES","CREAR", Constantes.si, "movimiento_contable", "MVC", Constantes.activo));
            //INDICADORES
            opciones.add(new MenuOpcion("MEN202305000044", "INDICADORES", "DASHBOARD","CREAR", Constantes.si, "dashboard", "DAS", Constantes.activo));
            //INVENTARIOS
            opciones.add(new MenuOpcion("MEN202305000045", "INVENTARIOS", "BODEGAS","CREAR", Constantes.si, "bodega", "BOD", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000046", "INVENTARIOS", "BODEGAS","CREAR", Constantes.no, "bodega_producto", "BOP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000047", "INVENTARIOS", "CATEGORÍA PRODUCTO","CREAR", Constantes.no, "categoria_producto", "CAP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000048", "INVENTARIOS", "EQUIVALENCIA DE MEDIDAS","CREAR", Constantes.no, "equivalencia_medida", "EQM", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000049", "INVENTARIOS", "GRUPOS DE PRODUCTOS","CREAR", Constantes.si, "grupo_producto", "GPR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000050", "INVENTARIOS", "KARDEX","CREAR", Constantes.si, "kardex", "KAR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000051", "INVENTARIOS", "MEDIDAS","CREAR", Constantes.si, "medida", "MED", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000052", "INVENTARIOS", "PRECIO","CREAR", Constantes.no, "precio", "PRE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000053", "INVENTARIOS", "PRODUCTOS","CREAR", Constantes.si, "producto", "PRO", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000054", "INVENTARIOS", "PROMOCIONES","CREAR", Constantes.si, "promocion", "PRM", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000055", "INVENTARIOS", "PROVEEDORES DE PRODUCTOS","CREAR", Constantes.no, "proveedor_producto", "PRP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000056", "INVENTARIOS", "TRANSFERENCIAS DE BODEGA","CREAR", Constantes.no, "transferencia_bodega", "TRB", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000057", "INVENTARIOS", "TIPO GASTO","CREAR", Constantes.no, "tipo_gasto", "TIG", Constantes.activo));
            //RECAUDACIÓN
            opciones.add(new MenuOpcion("MEN202305000058", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "amortizacion", "AMO", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000059", "CAJA BANCOS", "BANCOS","CREAR", Constantes.si, "banco", "BAN", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000060", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "cheque_vista", "CHV", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000061", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "cheque_posfechado", "CHP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000062", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "credito", "CRE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000063", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "retencion", "CTP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000064", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "deposito", "DEP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000065", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "efectivo", "EFE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000066", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "tarjeta_credito", "TCR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000067", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "tarjeta_debito", "TDE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000068", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "recaudacion", "REC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000069", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "transferencia", "TRF", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000070", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "retencion_venta", "RVE", Constantes.activo));
            // USUARIOS
            opciones.add(new MenuOpcion("MEN202305000071", "ACCESOS", "ESTABLECIMIENTOS","CREAR", Constantes.no, "celular_establecimiento", "CEE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000072", "ACCESOS", "ESTABLECIMIENTOS","CREAR", Constantes.no, "correo_establecimiento", "COE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000073", "ACCESOS", "EMPRESAS","CREAR", Constantes.si, "empresa", "EMP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000074", "ACCESOS", "ESTABLECIMIENTOS","CREAR", Constantes.si, "establecimiento", "EST", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000075", "ACCESOS", "ESTACIONES","CREAR", Constantes.si, "estacion", "ESN", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000076", "ACCESOS", "ESTACIÓN USUARIO","CREAR", Constantes.no, "estacion_usuario", "ESU", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000077", "ACCESOS", "PERFILES","CREAR", Constantes.si, "perfil", "PER", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000078", "ACCESOS", "PERMISOS","CREAR", Constantes.si, "permiso", "PRM", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000079", "ACCESOS", "SESION","CREAR", Constantes.no, "sesion", "SES", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000080", "ACCESOS", "ESTABLECIMIENTOS","CREAR", Constantes.no, "telefono_establecimiento", "TEE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000081", "ACCESOS", "USUARIOS","CREAR", Constantes.si, "usuario", "USR", Constantes.activo));
            //VENTAS
            opciones.add(new MenuOpcion("MEN202305000082", "VENTAS", "FACTURAS","CREAR", Constantes.si, "factura", "FAC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000083", "VENTAS", "NOTAS DE DÉBITO VENTA","CREAR", Constantes.si, "nota_debito_venta", "NDV", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000084", "VENTAS", "NOTAS DE DÉBITO VENTA","CREAR", Constantes.no, "nota_debito_venta_linea", "NDVL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000085", "VENTAS", "NOTAS DE CRÉDITO VENTA","CREAR", Constantes.si, "nota_credito_venta", "NCV", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000086", "VENTAS", "NOTAS DE CRÉDITO VENTA","CREAR", Constantes.no, "nota_credito_venta_linea", "NCVE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000087", "VENTAS", "PEDIDO","CREAR", Constantes.si, "pedido", "PED", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000088", "VENTAS", "PROFORMA","CREAR", Constantes.si, "proforma", "PRF", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000089", "VENTAS", "EGRESO","CREAR", Constantes.si, "egreso", "EGR", Constantes.activo));
            //ENTREGA
            opciones.add(new MenuOpcion("MEN202305000090", "VENTAS", "ENTREGA","CREAR", Constantes.no, "entrega", "ENT", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000091", "VENTAS", "GUIAS DE REMISION","CREAR", Constantes.si, "guia_remision", "GRE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000092", "VENTAS", "TRANSPORTISTAS","CREAR", Constantes.si, "transportista", "TRAN", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000093", "VENTAS", "VEHICULOS TRANSPORTES","CREAR", Constantes.si, "vehiculo_transporte", "VEH", Constantes.activo));
            //CAJA BANCOS
            opciones.add(new MenuOpcion("MEN202305000094", "CAJA BANCOS", "CUENTAS PROPIAS","CREAR", Constantes.si, "cuenta_propia", "CTP", Constantes.activo));
            //FALTA IMPLEMENTAR
            opciones.add(new MenuOpcion("MEN202305000095", "CUENTAS POR COBRAR", "CUENTAS POR COBRAR","CREAR", Constantes.no, "cuentas_cobrar", "CXC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000096", "CUENTAS POR PAGAR", "CUENTAS POR PAGAR","CREAR", Constantes.no, "cuentas_pagar", "CXP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000097", "ACTIVOS FIJOS", "ACTIVOS FIJOS","CREAR", Constantes.no, "activos_fijos", "ACF", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000098", "PRODUCCION", "PRODUCCION","CREAR", Constantes.no, "produccion", "PDC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000099", "TALENTO HUMANO", "TALENTO HUMANO","CREAR", Constantes.no, "talento_humano", "TTH", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000100", "FINANCIERO", "FINANCIERO","CREAR", Constantes.no, "financiero", "FIN", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000101", "IMPORTACION", "IMPORTACION","CREAR", Constantes.no, "importacion", "IMT", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000102", "REPORTES", "REPORTES","CREAR", Constantes.no, "reportes", "REP", Constantes.activo));

            opciones.add(new MenuOpcion("MEN202305000103", "CONTROL", "CONTROL","CREAR", Constantes.no, "produccion", "PDC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000104", "AUDITORIA", "AUDITORIA","CREAR", Constantes.no, "produccion", "PDC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000105", "TUTORIALES", "TUTORIALES","CREAR", Constantes.no, "produccion", "PDC", Constantes.activo));

            rep.saveAll(opciones);
        }
    }
}
