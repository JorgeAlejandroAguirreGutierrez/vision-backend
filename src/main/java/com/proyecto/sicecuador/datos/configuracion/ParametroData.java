package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.repositorios.configuracion.IParametroRepository;
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
public class ParametroData implements ApplicationRunner {
    @Autowired
    private IParametroRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Parametro> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Parametro> parametros = new ArrayList<>();
            parametros.add(new Parametro("PAR1", "CREAR", "", "dependiente", "DEP", Constantes.activo));
            parametros.add(new Parametro("PAR2", "CREAR", "", "calificacion_cliente", "CCL", Constantes.activo));
            parametros.add(new Parametro("PAR3", "CREAR", "", "celular", "CEL", Constantes.activo));
            parametros.add(new Parametro("PAR4", "CREAR", "", "cliente", "CLI", Constantes.activo));
            parametros.add(new Parametro("PAR5", "CREAR", "", "correo", "COR", Constantes.activo));
            parametros.add(new Parametro("PAR6", "CREAR", "", "direccion", "DIR", Constantes.activo));
            parametros.add(new Parametro("PAR7", "CREAR", "", "estado_civil", "ECV", Constantes.activo));
            parametros.add(new Parametro("PAR8", "CREAR", "", "financiamiento", "FIN", Constantes.activo));
            parametros.add(new Parametro("PAR9", "CREAR", "", "forma_pago", "FPA", Constantes.activo));
            parametros.add(new Parametro("PAR10", "CREAR", "", "genero", "GEN", Constantes.activo));
            parametros.add(new Parametro("PAR11", "CREAR", "", "grupo_cliente", "GCL", Constantes.activo));
            parametros.add(new Parametro("PAR12", "CREAR", "", "origen_ingreso", "OIN", Constantes.activo));
            parametros.add(new Parametro("PAR13", "CREAR", "", "plazo_credito", "PCR", Constantes.activo));
            parametros.add(new Parametro("PAR14", "CREAR", "", "retencion_cliente", "RCL", Constantes.activo));
            parametros.add(new Parametro("PAR15", "CREAR", "", "telefono", "TEL", Constantes.activo));
            parametros.add(new Parametro("PAR16", "CREAR", "", "tipo_contribuyente", "TCO", Constantes.activo));
            parametros.add(new Parametro("PAR17", "CREAR", "", "tipo_pago", "TPA", Constantes.activo));
            parametros.add(new Parametro("PAR18", "CREAR", "", "factura", "FAC", Constantes.activo));
            parametros.add(new Parametro("PAR18", "CREAR", "", "nota_debito_venta", "NDV", Constantes.activo));
            parametros.add(new Parametro("PAR18", "CREAR", "", "nota_debito_venta_linea", "NDVL", Constantes.activo));
            parametros.add(new Parametro("PAR66", "CREAR", "", "nota_credito_venta", "NCV", Constantes.activo));
            parametros.add(new Parametro("PAR66", "CREAR", "", "nota_credito_venta_linea", "NCVE", Constantes.activo));
            parametros.add(new Parametro("PAR19", "CREAR", "", "egreso", "EGRE", Constantes.activo));
            parametros.add(new Parametro("PAR20", "CREAR", "", "ubicacion", "UBI", Constantes.activo));
            parametros.add(new Parametro("PAR21", "CREAR", "", "entrega", "ENT", Constantes.activo));
            parametros.add(new Parametro("PAR22", "CREAR", "", "transportista", "TRAN", Constantes.activo));
            parametros.add(new Parametro("PAR23", "CREAR", "", "vehiculo_transporte", "VEH", Constantes.activo));
            parametros.add(new Parametro("PAR24", "CREAR", "", "bodega", "BOD", Constantes.activo));
            parametros.add(new Parametro("PAR25", "CREAR", "", "impuesto", "IMP", Constantes.activo));
            parametros.add(new Parametro("PAR26", "CREAR", "", "producto", "PRO", Constantes.activo));
            parametros.add(new Parametro("PAR27", "CREAR", "", "servicio", "SRV", Constantes.activo));
            parametros.add(new Parametro("PAR28", "CREAR", "", "cheque_vista", "CHV", Constantes.activo));
            parametros.add(new Parametro("PAR29", "CREAR", "", "cheque_posfechado", "CHP", Constantes.activo));
            parametros.add(new Parametro("PAR30", "CREAR", "", "credito", "CRE", Constantes.activo));
            parametros.add(new Parametro("PAR31", "CREAR", "", "efectivo", "EFE", Constantes.activo));
            parametros.add(new Parametro("PAR32", "CREAR", "", "deposito", "DEP", Constantes.activo));
            parametros.add(new Parametro("PAR33", "CREAR", "", "transferencia", "TRF", Constantes.activo));
            parametros.add(new Parametro("PAR34", "CREAR", "", "retencion_venta", "RVE", Constantes.activo));
            parametros.add(new Parametro("PAR35", "CREAR", "", "tarjeta_credito", "TCR", Constantes.activo));
            parametros.add(new Parametro("PAR36", "CREAR", "", "tarjeta_debito", "TDE", Constantes.activo));
            parametros.add(new Parametro("PAR37", "CREAR", "", "usuario", "USR", Constantes.activo));
            parametros.add(new Parametro("PAR61", "CREAR", "", "sesion", "SES", Constantes.activo));
            parametros.add(new Parametro("PAR62", "CREAR", "", "telefono_auxiliar", "TEA", Constantes.activo));
            parametros.add(new Parametro("PAR63", "CREAR", "", "celular_auxiliar", "CEA", Constantes.activo));
            parametros.add(new Parametro("PAR64", "CREAR", "", "correo_auxiliar", "COA", Constantes.activo));
            parametros.add(new Parametro("PAR65", "CREAR", "", "bodega_producto", "BOP", Constantes.activo));
            parametros.add(new Parametro("PAR66", "CREAR", "", "factura_detalle", "FDE", Constantes.activo));
            parametros.add(new Parametro("PAR66", "CREAR", "", "factura_compra", "FCO", Constantes.activo));
            parametros.add(new Parametro("PAR66", "CREAR", "", "factura_compra_linea", "FCL", Constantes.activo));
            parametros.add(new Parametro("PAR66", "CREAR", "", "nota_credito_compra", "NCC", Constantes.activo));
            parametros.add(new Parametro("PAR66", "CREAR", "", "nota_credito_compra_linea", "NCCL", Constantes.activo));
            parametros.add(new Parametro("PAR66", "CREAR", "", "nota_debito_compra", "NDC", Constantes.activo));
            parametros.add(new Parametro("PAR66", "CREAR", "", "nota_debito_compra_linea", "NDCL", Constantes.activo));
            parametros.add(new Parametro("PAR67", "CREAR", "", "recaudacion", "REC", Constantes.activo));
            parametros.add(new Parametro("PAR67", "CREAR", "", "grupo_producto", "GRP", Constantes.activo));
            parametros.add(new Parametro("PAR67", "CREAR", "", "kardex", "KAR", Constantes.activo));
            parametros.add(new Parametro("PAR67", "CREAR", "", "medida_precio", "MP", Constantes.activo));
            parametros.add(new Parametro("PAR67", "CREAR", "", "tabla_equivalencia_medida", "TEM", Constantes.activo));
            parametros.add(new Parametro("PAR68", "CREAR", "", "medida", "MED", Constantes.activo));
            parametros.add(new Parametro("PAR69", "CREAR", "", "establecimiento", "EST", Constantes.activo));
            parametros.add(new Parametro("PAR70", "CREAR", "", "estacion", "ESC", Constantes.activo));
            parametros.add(new Parametro("PAR71", "CREAR", "", "perfil", "PER", Constantes.activo));
            parametros.add(new Parametro("PAR38", "CREAR", "", "parametro", "PAR", Constantes.activo));
            parametros.add(new Parametro("PAR39", "TIPOS USUARIOS", "VENDEDOR", "", "V", Constantes.activo));
            parametros.add(new Parametro("PAR40", "TIPOS USUARIOS", "CAJERO", "", "C", Constantes.activo));
            parametros.add(new Parametro("PAR41", "TIPOS MEDIDAS", "KILOGRAMO", "", "KG", Constantes.activo));
            parametros.add(new Parametro("PAR42", "CHEQUE", "A LA VISTA", "", "CHV", Constantes.activo));
            parametros.add(new Parametro("PAR43", "CHEQUE", "POSFECHADO", "", "CHP", Constantes.activo));
            parametros.add(new Parametro("PAR44", "TIPO TRANSACCION", "DIRECTA", "", "DIR", Constantes.activo));
            parametros.add(new Parametro("PAR45", "TIPO TRANSACCION", "VIA BCE", "", "BCE", Constantes.activo));
            parametros.add(new Parametro("PAR46", "TARJETA", "CREDITO", "", "TCR", Constantes.activo));
            parametros.add(new Parametro("PAR47", "TARJETA", "DEBITO", "", "TDB", Constantes.activo));
            parametros.add(new Parametro("PAR48", "PERIODICIDAD", "MENSUAL", "", "M", Constantes.activo));
            parametros.add(new Parametro("PAR49", "PERIODICIDAD", "QUINCENAL", "", "Q", Constantes.activo));
            parametros.add(new Parametro("PAR50", "PERIODICIDAD", "TRIMESTRAL", "", "T", Constantes.activo));
            parametros.add(new Parametro("PAR50", "PERIODICIDAD", "ANUAL", "", "A", Constantes.activo));
            parametros.add(new Parametro("PAR51", "AMORTIZACION", "ALEMANA", "", "AL", Constantes.activo));
            parametros.add(new Parametro("PAR52", "AMORTIZACION", "FRANCESA", "", "FR", Constantes.activo));
            parametros.add(new Parametro("PAR53", "FORMA COBRO", "CONTADO", "", "C", Constantes.activo));
            parametros.add(new Parametro("PAR54", "FORMA COBRO", "DIFERIDO", "", "D", Constantes.activo));
            parametros.add(new Parametro("PAR55", "TIPO COMPROBANTE RETENCION", "FACTURA", "", "FAC", Constantes.activo));
            parametros.add(new Parametro("PAR56", "TIPOS MEDIDAS", "UNIDAD", "", "U", Constantes.activo));
            parametros.add(new Parametro("PAR57", "TIPOS MEDIDAS", "METRO", "", "M", Constantes.activo));
            parametros.add(new Parametro("PAR58", "FORMA TIEMPO", "DIA", "", "D", Constantes.activo));
            parametros.add(new Parametro("PAR59", "FORMA TIEMPO", "MES", "", "M", Constantes.activo));
            parametros.add(new Parametro("PAR60", "FORMA TIEMPO", "ANIO", "", "A", Constantes.activo));
            parametros.add(new Parametro("PAR68", "PERIODO_MENSUAL", "12", "", "PME", Constantes.activo));
            parametros.add(new Parametro("PAR69", "PERIODO_QUINCENAL", "24", "", "PQU", Constantes.activo));
            parametros.add(new Parametro("PAR70", "PERIODO_TRIMESTRAL", "4", "", "PTR", Constantes.activo));
            parametros.add(new Parametro("PAR71", "PERIODO_ANUAL", "1", "", "PAN", Constantes.activo));
            parametros.add(new Parametro("PAR72", "MENSUAL", "30", "", "MEN", Constantes.activo));
            parametros.add(new Parametro("PAR73", "QUINCENAL", "15", "", "QUI", Constantes.activo));
            parametros.add(new Parametro("PAR74", "TRIMESTRAL", "90", "", "TRI", Constantes.activo));
            parametros.add(new Parametro("PAR75", "ANUAL", "365", "", "ANU", Constantes.activo));
            parametros.add(new Parametro("PAR76", "CREAR", "", "amortizacion", "AMO", Constantes.activo));
            parametros.add(new Parametro("PAR77", "LOGO", "siice.png", "", "LOG", Constantes.activo));
            
            parametros.add(new Parametro("PAR78", "CREAR", "", "proveedor", "PRV", Constantes.activo));
            parametros.add(new Parametro("PAR79", "CREAR", "", "afectacion_contable", "AFC", Constantes.activo));
            parametros.add(new Parametro("PAR80", "CREAR", "", "segmento", "SEG", Constantes.activo));
            parametros.add(new Parametro("PAR81", "CREAR", "", "tipo_retencion", "TRE", Constantes.activo));
            parametros.add(new Parametro("PAR82", "CREAR", "", "grupo_proveedor", "GPR", Constantes.activo));
            parametros.add(new Parametro("PAR83", "CREAR", "", "empresa", "EMP", Constantes.activo));
            parametros.add(new Parametro("PAR83", "CREAR", "", "guia_remision", "GRE", Constantes.activo));

            //MODULOS
            parametros.add(new Parametro("PAR82", "MODULO", "", "CLIENTES", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "COMPRAS", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "VENTAS", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "INVENTARIOS", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "CAJA BANCOS", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "CUENTAS POR COBRAR", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "CUENTAS POR PAGAR", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "ACTIVOS FIJOS", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "PRODUCCION", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "CONTABILIDAD", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "TALENTO HUMANO", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "FINANCIERO", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "IMPORTACION", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "REPORTES", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "ACCESOS", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "CONFIGURACION", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "INDICADORES", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "ORGANISMOS DE CONTROL", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "AUDITORIA", "", Constantes.activo));
            parametros.add(new Parametro("PAR82", "MODULO", "", "TUTORIALES", "", Constantes.activo));
            
            parametros.add(new Parametro("PAR82", "OPERACION", "", "CONSULTAR", "", Constantes.activo));
            parametros.add(new Parametro("PAR83", "OPERACION", "", "OBTENER", "", Constantes.activo));
            parametros.add(new Parametro("PAR84", "OPERACION", "", "CREAR", "", Constantes.activo));
            parametros.add(new Parametro("PAR85", "OPERACION", "", "ACTUALIZAR", "", Constantes.activo));
            parametros.add(new Parametro("PAR86", "OPERACION", "", "ACTIVAR", "", Constantes.activo));
            parametros.add(new Parametro("PAR87", "OPERACION", "", "INACTIVAR", "", Constantes.activo));
            rep.saveAll(parametros);
        }
    }
}
