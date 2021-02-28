package com.proyecto.sicecuador.otros.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.modelos.recaudacion.*;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import java.util.Optional;

@Component
public class RecaudacionUtil {
    private static IParametroRepository rep;
    private final String tabla="recaudacion";
    private final String tipo="CREAR";
    private final String tabla_cheque="cheque";
    private final String tabla_deposito="deposito";
    private final String tabla_transferencia="transferencia";
    private final String tabla_tarjeta_credito="tarjeta_credito";
    private final String tabla_tarjeta_debito="tarjeta_debito";
    private final String tabla_compensacion="compensacion";
    private final String tabla_retencion_venta="retencion_venta";
    private final String tabla_credito="credito";
    private final String tabla_amortizacion="amortizacion";


    @Autowired
    public void initParametro(IParametroRepository rep){
        this.rep = rep;
    }

    @PrePersist
    public void prePersist(Entidad entidad) {
        Optional<Parametro> parametro = rep.findByTablaAndTipo(tabla,tipo);
        long conteo=rep.findConteoRecaudacion();
        entidad.setCodigo(Util.generarCodigo(parametro, conteo));
        Recaudacion recaudacion=(Recaudacion)entidad;
        for (Cheque cheque: recaudacion.getCheques()) {
            parametro = rep.findByTablaAndTipo(tabla_cheque,tipo);
            conteo=rep.findConteoCheque();
            cheque.setCodigo(Util.generarCodigo(parametro, conteo));
        }
        for (Deposito deposito: recaudacion.getDepositos()) {
            parametro = rep.findByTablaAndTipo(tabla_deposito,tipo);
            conteo=rep.findConteoDeposito();
            deposito.setCodigo(Util.generarCodigo(parametro, conteo));
        }
        for (Transferencia transferencia: recaudacion.getTransferencias()) {
            parametro = rep.findByTablaAndTipo(tabla_transferencia,tipo);
            conteo=rep.findConteoTransferencia();
            transferencia.setCodigo(Util.generarCodigo(parametro, conteo));
        }
        for (TarjetaCredito tarjeta_credito: recaudacion.getTarjetasCreditos()) {
            parametro = rep.findByTablaAndTipo(tabla_tarjeta_credito,tipo);
            conteo=rep.findConteoTarjetaCredito();
            tarjeta_credito.setCodigo(Util.generarCodigo(parametro, conteo));
        }
        for (TarjetaDebito tarjeta_debito: recaudacion.getTarjetasDebitos()) {
            parametro = rep.findByTablaAndTipo(tabla_tarjeta_debito,tipo);
            conteo=rep.findConteoTarjetaDebito();
            tarjeta_debito.setCodigo(Util.generarCodigo(parametro, conteo));
        }
        for (Compensacion compensacion: recaudacion.getCompensaciones()) {
            parametro = rep.findByTablaAndTipo(tabla_compensacion,tipo);
            conteo=rep.findConteoCompensacion();
            compensacion.setCodigo(Util.generarCodigo(parametro, conteo));
        }
        for (RetencionVenta retencion_venta: recaudacion.getRetencionesVentas()) {
            parametro = rep.findByTablaAndTipo(tabla_retencion_venta,tipo);
            conteo=rep.findConteoRetencionVenta();
            retencion_venta.setCodigo(Util.generarCodigo(parametro, conteo));
        }
        parametro = rep.findByTablaAndTipo(tabla_credito,tipo);
        conteo=rep.findConteoCredito();
        recaudacion.getCredito().setCodigo(Util.generarCodigo(parametro, conteo));
        for (Amortizacion amortizacion: recaudacion.getCredito().getAmortizaciones()) {
            parametro = rep.findByTablaAndTipo(tabla_amortizacion,tipo);
            conteo=rep.findConteoAmortizacion();
            amortizacion.setCodigo(Util.generarCodigo(parametro, conteo));
        }

    }
}
