package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.repositorios.configuracion.IParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(16)
public class ParametroData implements ApplicationRunner {
    @Autowired
    private IParametroRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Parametro> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Parametro> parametros = new ArrayList<>();
            parametros.add(new Parametro("PAR1", "CREAR", "", "auxiliar", "AUX"));
            parametros.add(new Parametro("PAR2", "CREAR", "", "categoria_cliente", "CCL"));
            parametros.add(new Parametro("PAR3", "CREAR", "", "celular", "CEL"));
            parametros.add(new Parametro("PAR4", "CREAR", "", "cliente", "CLI"));
            parametros.add(new Parametro("PAR5", "CREAR", "", "correo", "COR"));
            parametros.add(new Parametro("PAR6", "CREAR", "", "direccion", "DIR"));
            parametros.add(new Parametro("PAR7", "CREAR", "", "estado_civil", "ECV"));
            parametros.add(new Parametro("PAR8", "CREAR", "", "financiamiento", "FIN"));
            parametros.add(new Parametro("PAR9", "CREAR", "", "forma_pago", "FPA"));
            parametros.add(new Parametro("PAR10", "CREAR", "", "genero", "GEN"));
            parametros.add(new Parametro("PAR11", "CREAR", "", "grupo_cliente", "GCL"));
            parametros.add(new Parametro("PAR12", "CREAR", "", "origen_ingreso", "OIN"));
            parametros.add(new Parametro("PAR13", "CREAR", "", "plazo_credito", "PCR"));
            parametros.add(new Parametro("PAR14", "CREAR", "", "retencion_cliente", "RCL"));
            parametros.add(new Parametro("PAR15", "CREAR", "", "telefono", "TEL"));
            parametros.add(new Parametro("PAR16", "CREAR", "", "tipo_contribuyente", "TCO"));
            parametros.add(new Parametro("PAR17", "CREAR", "", "tipo_pago", "TPA"));
            parametros.add(new Parametro("PAR18", "CREAR", "", "factura", "FAC"));
            parametros.add(new Parametro("PAR19", "CREAR", "", "egreso", "EGRE"));
            parametros.add(new Parametro("PAR20", "CREAR", "", "ubicacion", "UBI"));
            parametros.add(new Parametro("PAR21", "CREAR", "", "guia_remision", "REM"));
            parametros.add(new Parametro("PAR22", "CREAR", "", "transportista", "TRAN"));
            parametros.add(new Parametro("PAR23", "CREAR", "", "vehiculo_transporte", "VEH"));
            parametros.add(new Parametro("PAR24", "CREAR", "", "bodega", "BOD"));
            parametros.add(new Parametro("PAR25", "CREAR", "", "impuesto", "IMP"));
            parametros.add(new Parametro("PAR26", "CREAR", "", "producto", "PRO"));
            parametros.add(new Parametro("PAR27", "CREAR", "", "servicio", "SRV"));
            parametros.add(new Parametro("PAR28", "CREAR", "", "cheque_vista", "CHV"));
            parametros.add(new Parametro("PAR29", "CREAR", "", "cheque_posfechado", "CHP"));
            parametros.add(new Parametro("PAR30", "CREAR", "", "credito", "CRE"));
            parametros.add(new Parametro("PAR31", "CREAR", "", "efectivo", "EFE"));
            parametros.add(new Parametro("PAR32", "CREAR", "", "deposito", "DEP"));
            parametros.add(new Parametro("PAR33", "CREAR", "", "transferencia", "TRF"));
            parametros.add(new Parametro("PAR34", "CREAR", "", "retencion_venta", "RVE"));
            parametros.add(new Parametro("PAR35", "CREAR", "", "tarjeta_credito", "TCR"));
            parametros.add(new Parametro("PAR36", "CREAR", "", "tarjeta_debito", "TDE"));
            parametros.add(new Parametro("PAR37", "CREAR", "", "usuario", "USR"));
            parametros.add(new Parametro("PAR61", "CREAR", "", "sesion", "SES"));
            parametros.add(new Parametro("PAR62", "CREAR", "", "telefono_auxiliar", "TEA"));
            parametros.add(new Parametro("PAR63", "CREAR", "", "celular_auxiliar", "CEA"));
            parametros.add(new Parametro("PAR64", "CREAR", "", "correo_auxiliar", "COA"));
            parametros.add(new Parametro("PAR65", "CREAR", "", "bodega_producto", "BOP"));
            parametros.add(new Parametro("PAR66", "CREAR", "", "factura_detalle", "FDE"));
            parametros.add(new Parametro("PAR67", "CREAR", "", "recaudacion", "REC"));
            parametros.add(new Parametro("PAR67", "CREAR", "", "grupo_producto", "GRP"));
            parametros.add(new Parametro("PAR67", "CREAR", "", "kardex", "KAR"));
            parametros.add(new Parametro("PAR67", "CREAR", "", "medida_precio", "MP"));
            parametros.add(new Parametro("PAR38", "CREAR", "", "parametro", "PAR"));
            parametros.add(new Parametro("PAR39", "TIPOS USUARIOS", "VENDEDOR", "", "V"));
            parametros.add(new Parametro("PAR40", "TIPOS USUARIOS", "CAJERO", "", "C"));
            parametros.add(new Parametro("PAR41", "TIPOS MEDIDAS", "KILOGRAMO", "", "KG"));
            parametros.add(new Parametro("PAR42", "CHEQUE", "A LA VISTA", "", "CHV"));
            parametros.add(new Parametro("PAR43", "CHEQUE", "POSFECHADO", "", "CHP"));
            parametros.add(new Parametro("PAR44", "TRANSACCION", "DEPOSITO", "", "DP"));
            parametros.add(new Parametro("PAR45", "TRANSACCION", "TRANSFERENCIA", "", "TF"));
            parametros.add(new Parametro("PAR46", "TARJETA", "CREDITO", "", "TCR"));
            parametros.add(new Parametro("PAR47", "TARJETA", "DEBITO", "", "TDB"));
            parametros.add(new Parametro("PAR48", "PERIODICIDAD", "MENSUAL", "", "M"));
            parametros.add(new Parametro("PAR49", "PERIODICIDAD", "QUINCENAL", "", "Q"));
            parametros.add(new Parametro("PAR50", "PERIODICIDAD", "TRIMESTRAL", "", "T"));
            parametros.add(new Parametro("PAR50", "PERIODICIDAD", "ANUAL", "", "A"));
            parametros.add(new Parametro("PAR51", "MODELO_AMORTIZACION", "ALEMANA", "", "AL"));
            parametros.add(new Parametro("PAR52", "MODELO_AMORTIZACION", "FRANCESA", "", "FR"));
            parametros.add(new Parametro("PAR53", "FORMA COBRO", "CONTADO", "", "C"));
            parametros.add(new Parametro("PAR54", "FORMA COBRO", "DIFERIDO", "", "D"));
            parametros.add(new Parametro("PAR55", "TIPO COMPROBANTE RETENCION", "FACTURA", "", "FAC"));
            parametros.add(new Parametro("PAR56", "TIPOS MEDIDAS", "UNIDAD", "", "U"));
            parametros.add(new Parametro("PAR57", "TIPOS MEDIDAS", "METRO", "", "M"));
            parametros.add(new Parametro("PAR58", "FORMA TIEMPO", "DIA", "", "D"));
            parametros.add(new Parametro("PAR59", "FORMA TIEMPO", "MES", "", "M"));
            parametros.add(new Parametro("PAR60", "FORMA TIEMPO", "ANIO", "", "A"));
            parametros.add(new Parametro("PAR68", "PERIODO_MENSUAL", "12", "", "PME"));
            parametros.add(new Parametro("PAR69", "PERIODO_QUINCENAL", "24", "", "PQU"));
            parametros.add(new Parametro("PAR70", "PERIODO_TRIMESTRAL", "4", "", "PTR"));
            parametros.add(new Parametro("PAR71", "PERIODO_ANUAL", "1", "", "PAN"));
            parametros.add(new Parametro("PAR72", "MENSUAL", "30", "", "MEN"));
            parametros.add(new Parametro("PAR73", "QUINCENAL", "15", "", "QUI"));
            parametros.add(new Parametro("PAR74", "TRIMESTRAL", "90", "", "TRI"));
            parametros.add(new Parametro("PAR75", "ANUAL", "365", "", "ANU"));
            parametros.add(new Parametro("PAR76", "CREAR", "", "amortizacion", "AMO"));
            parametros.add(new Parametro("PAR77", "LOGO", "siice.png", "", "LOG"));
            rep.saveAll(parametros);
        }
    }
}
