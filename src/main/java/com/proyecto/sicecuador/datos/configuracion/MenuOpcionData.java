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
            opciones.add(new MenuOpcion("MEN202305000001", "CLIENTES", "GRUPOS DE CLIENTES","CREAR", Constantes.si, "grupo_cliente", "GCL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000002", "CLIENTES", "CLIENTES","CREAR", Constantes.si, "cliente", "CLI", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000003", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "dependiente", "DEP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000004", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "celular", "CEL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000005", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "celular_dependiente", "CED", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000006", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "correo", "COR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000007", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "correo_dependiente", "COD", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000008", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "telefono", "TEL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000009", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "telefono_dependiente", "TED", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000010", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "tipo_contribuyente", "TCO", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000011", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "retencion_cliente", "RCL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000012", "CLIENTES", "CLIENTES","CREAR", Constantes.no, "tipo_identificacion", "TID", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000013", "CLIENTES", "SEGMENTOS","CREAR", Constantes.si, "segmento", "SEG", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000014", "CLIENTES", "FORMAS DE PAGO","CREAR", Constantes.si, "forma_pago", "FPA", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000015", "CLIENTES", "ORIGEN DE INGRESOS","CREAR", Constantes.si, "origen_ingreso", "OIN", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000016", "CLIENTES", "PLAZOS DE CRÉDITO","CREAR", Constantes.si, "plazo_credito", "PCR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000017", "CLIENTES", "CALIFICACIÓN DE CLIENTES","CREAR", Constantes.si, "calificacion_cliente", "CCL", Constantes.activo));
            //COMPRAS
            opciones.add(new MenuOpcion("MEN202305000018", "COMPRAS", "GRUPOS DE PROVEEDORES","CREAR", Constantes.si, "grupo_proveedor", "GPR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000019", "COMPRAS", "PROVEEDORES","CREAR", Constantes.si, "proveedor", "PRV", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000020", "COMPRAS", "PROVEEDORES","CREAR", Constantes.no, "celular_proveedor", "CEP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000021", "COMPRAS", "PROVEEDORES","CREAR", Constantes.no, "correo_proveedor", "COP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000022", "COMPRAS", "PROVEEDORES","CREAR", Constantes.no, "telefono_proveedor", "TEP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000023", "COMPRAS", "FACTURAS DE COMPRA","CREAR", Constantes.si, "factura_compra", "FCO", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000024", "COMPRAS", "FACTURAS DE COMPRA","CREAR", Constantes.no, "factura_compra_linea", "FCL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000025", "COMPRAS", "NOTAS DE CRÉDITO COMPRA","CREAR", Constantes.si, "nota_credito_compra", "NCC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000026", "COMPRAS", "NOTAS DE CRÉDITO COMPRA","CREAR", Constantes.no, "nota_credito_compra_linea", "NCCL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000027", "COMPRAS", "NOTAS DE DÉBITO COMPRA","CREAR", Constantes.si, "nota_debito_compra", "NDC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000028", "COMPRAS", "NOTAS DE DÉBITO COMPRA","CREAR", Constantes.no, "nota_debito_compra_linea", "NDCL", Constantes.activo));
            //VENTAS
            opciones.add(new MenuOpcion("MEN202305000029", "VENTAS", "FACTURAS","CREAR", Constantes.si, "factura", "FAC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000030", "VENTAS", "NOTAS DE DÉBITO VENTA","CREAR", Constantes.si, "nota_debito_venta", "NDV", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000031", "VENTAS", "NOTAS DE DÉBITO VENTA","CREAR", Constantes.no, "nota_debito_venta_linea", "NDVL", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000032", "VENTAS", "NOTAS DE CRÉDITO VENTA","CREAR", Constantes.si, "nota_credito_venta", "NCV", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000033", "VENTAS", "NOTAS DE CRÉDITO VENTA","CREAR", Constantes.no, "nota_credito_venta_linea", "NCVE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000034", "VENTAS", "PEDIDO","CREAR", Constantes.si, "pedido", "PED", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000035", "VENTAS", "PROFORMA","CREAR", Constantes.si, "proforma", "PRF", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000036", "VENTAS", "EGRESO","CREAR", Constantes.si, "egreso", "EGR", Constantes.activo));
            //RECAUDACIÓN
            opciones.add(new MenuOpcion("MEN202305000037", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "amortizacion", "AMO", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000038", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "cheque_vista", "CHV", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000039", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "cheque_posfechado", "CHP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000040", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "credito", "CRE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000041", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "retencion", "CTP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000042", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "deposito", "DEP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000043", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "efectivo", "EFE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000044", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "tarjeta_credito", "TCR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000045", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "tarjeta_debito", "TDE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000046", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "recaudacion", "REC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000047", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "transferencia", "TRF", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000048", "VENTAS", "RECAUDACIÓN","CREAR", Constantes.no, "retencion_venta", "RVE", Constantes.activo));
            //ENTREGA
            opciones.add(new MenuOpcion("MEN202305000049", "VENTAS", "ENTREGA","CREAR", Constantes.no, "entrega", "ENT", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000050", "VENTAS", "GUIAS DE REMISION","CREAR", Constantes.si, "guia_remision", "GRE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000051", "VENTAS", "TRANSPORTISTAS","CREAR", Constantes.si, "transportista", "TRAN", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000052", "VENTAS", "VEHICULOS TRANSPORTES","CREAR", Constantes.si, "vehiculo_transporte", "VEH", Constantes.activo));
            //INVENTARIOS
            opciones.add(new MenuOpcion("MEN202305000053", "INVENTARIOS", "BODEGAS","CREAR", Constantes.si, "bodega", "BOD", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000054", "INVENTARIOS", "BODEGAS","CREAR", Constantes.no, "bodega_producto", "BOP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000055", "INVENTARIOS", "CATEGORÍA PRODUCTO","CREAR", Constantes.no, "categoria_producto", "CAP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000056", "INVENTARIOS", "EQUIVALENCIA DE MEDIDAS","CREAR", Constantes.no, "equivalencia_medida", "EQM", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000057", "INVENTARIOS", "GRUPOS DE PRODUCTOS","CREAR", Constantes.si, "grupo_producto", "GPR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000058", "INVENTARIOS", "KARDEX","CREAR", Constantes.si, "kardex", "KAR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000059", "INVENTARIOS", "MEDIDAS","CREAR", Constantes.si, "medida", "MED", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000060", "INVENTARIOS", "PRECIO","CREAR", Constantes.no, "precio", "PRE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000061", "INVENTARIOS", "PRODUCTOS","CREAR", Constantes.si, "producto", "PRO", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000062", "INVENTARIOS", "PROMOCIONES","CREAR", Constantes.si, "promocion", "PRM", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000063", "INVENTARIOS", "PROVEEDORES DE PRODUCTOS","CREAR", Constantes.no, "proveedor_producto", "PRP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000064", "INVENTARIOS", "TRANSFERENCIAS DE BODEGA","CREAR", Constantes.no, "transferencia_bodega", "TRB", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000065", "INVENTARIOS", "TIPO GASTO","CREAR", Constantes.no, "tipo_gasto", "TIG", Constantes.activo));
            //CAJA BANCOS
            opciones.add(new MenuOpcion("MEN202305000066", "CAJA BANCOS", "CUENTAS PROPIAS","CREAR", Constantes.si, "cuenta_propia", "CTP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000067", "CAJA BANCOS", "BANCOS","CREAR", Constantes.si, "banco", "BAN", Constantes.activo));
            //CUENTAS X COBRAR
            opciones.add(new MenuOpcion("MEN202305000068", "CUENTAS POR COBRAR", "CUENTAS POR COBRAR","CREAR", Constantes.no, "cuentas_cobrar", "CXC", Constantes.activo));
            //CUENTAS X PAGAR
            opciones.add(new MenuOpcion("MEN202305000069", "CUENTAS POR PAGAR", "CUENTAS POR PAGAR","CREAR", Constantes.no, "cuentas_pagar", "CXP", Constantes.activo));
            //ACTIVOS FIJOS
            opciones.add(new MenuOpcion("MEN202305000070", "ACTIVOS FIJOS", "ACTIVOS FIJOS","CREAR", Constantes.no, "activos_fijos", "ACF", Constantes.activo));
            //PRODUCCIÓN
            opciones.add(new MenuOpcion("MEN202305000071", "PRODUCCION", "PRODUCCION","CREAR", Constantes.no, "produccion", "PDC", Constantes.activo));
            //CONTABILIDAD
            opciones.add(new MenuOpcion("MEN202305000072", "CONTABILIDAD", "CUENTAS CONTABLES","CREAR", Constantes.si, "cuenta_contable", "CTC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000073", "CONTABILIDAD", "MOVIMIENTOS CONTABLES","CREAR", Constantes.si, "movimiento_contable", "MVC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000074", "CONTABILIDAD", "AFECTACION CONTABLE","CREAR", Constantes.no, "afectacion_contable", "AFC", Constantes.activo));
            //TALENTO HUMANO
            opciones.add(new MenuOpcion("MEN202305000075", "TALENTO HUMANO", "TALENTO HUMANO","CREAR", Constantes.no, "talento_humano", "TTH", Constantes.activo));
            //FINANCIERO
            opciones.add(new MenuOpcion("MEN202305000076", "FINANCIERO", "FINANCIERO","CREAR", Constantes.no, "financiero", "FIN", Constantes.activo));
            //IMPORTACIÓN
            opciones.add(new MenuOpcion("MEN202305000077", "IMPORTACION", "IMPORTACION","CREAR", Constantes.no, "importacion", "IMT", Constantes.activo));
            //REPORTES
            opciones.add(new MenuOpcion("MEN202305000078", "REPORTES", "REPORTES","CREAR", Constantes.no, "reportes", "REP", Constantes.activo));
            // ACCESOS
            opciones.add(new MenuOpcion("MEN202305000079", "ACCESOS", "USUARIOS","CREAR", Constantes.si, "usuario", "USR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000080", "ACCESOS", "EMPRESAS","CREAR", Constantes.si, "empresa", "EMP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000081", "ACCESOS", "ESTABLECIMIENTOS","CREAR", Constantes.si, "establecimiento", "EST", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000082", "ACCESOS", "ESTABLECIMIENTOS","CREAR", Constantes.no, "celular_establecimiento", "CEE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000083", "ACCESOS", "ESTABLECIMIENTOS","CREAR", Constantes.no, "correo_establecimiento", "COE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000084", "ACCESOS", "ESTABLECIMIENTOS","CREAR", Constantes.no, "telefono_establecimiento", "TEE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000085", "ACCESOS", "ESTACIONES","CREAR", Constantes.si, "estacion", "ESN", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000086", "ACCESOS", "ESTACIÓN USUARIO","CREAR", Constantes.no, "estacion_usuario", "ESU", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000087", "ACCESOS", "PERFILES","CREAR", Constantes.si, "perfil", "PER", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000088", "ACCESOS", "PERMISOS","CREAR", Constantes.si, "permiso", "PRM", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000089", "ACCESOS", "SESION","CREAR", Constantes.no, "sesion", "SES", Constantes.activo));
            //CONFIGURACIÓN
            opciones.add(new MenuOpcion("MEN202305000090", "CONFIGURACION", "UBICACIÓN","CREAR", Constantes.si, "ubicacion", "UBI", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000091", "CONFIGURACION", "UBICACIÓN","CREAR", Constantes.no, "direccion", "DIR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000092", "CONFIGURACION", "ESTADO CIVIL","CREAR", Constantes.si, "estado_civil", "ECV", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000093", "CONFIGURACION", "IMPUESTOS","CREAR", Constantes.si, "impuesto", "IMP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000094", "CONFIGURACION", "SECUENCIALES","CREAR", Constantes.si, "secuencial", "SEC", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000095", "CONFIGURACION", "TIPOS DE RETENCIÓN","CREAR", Constantes.si, "tipo_retencion", "TRE", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000096", "CONFIGURACION", "RÉGIMEN","CREAR", Constantes.si, "regimen", "REG", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000097", "CONFIGURACION", "IMPORTACIONES","CREAR", Constantes.si, "", "IMP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000098", "CONFIGURACION", "EXPORTACIONES","CREAR", Constantes.si, "", "EXP", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000099", "CONFIGURACION", "MENU","CREAR", Constantes.no, "menu", "MEN", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000100", "CONFIGURACION", "PARAMETROS","CREAR", Constantes.no, "parametro", "PAR", Constantes.activo));
            opciones.add(new MenuOpcion("MEN202305000101", "CONFIGURACION", "GENERO","CREAR", Constantes.no, "genero", "GEN", Constantes.activo));
            //INDICADORES
            opciones.add(new MenuOpcion("MEN202305000102", "INDICADORES", "DASHBOARD","CREAR", Constantes.si, "dashboard", "DAS", Constantes.activo));
            //CONTROL
            opciones.add(new MenuOpcion("MEN202305000103", "CONTROL", "CONTROL","CREAR", Constantes.no, "produccion", "PDC", Constantes.activo));
            //AUDITORÍA
            opciones.add(new MenuOpcion("MEN202305000104", "AUDITORIA", "AUDITORIA","CREAR", Constantes.no, "produccion", "PDC", Constantes.activo));
            //TUTORIALES
            opciones.add(new MenuOpcion("MEN202305000105", "TUTORIALES", "TUTORIALES","CREAR", Constantes.no, "produccion", "PDC", Constantes.activo));

            rep.saveAll(opciones);
        }
    }
}
