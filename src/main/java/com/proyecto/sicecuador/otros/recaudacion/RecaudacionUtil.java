package com.proyecto.sicecuador.otros.recaudacion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.Telefono;
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
            conteo=rep.findConteoTelefono();
            cheque.setCodigo(Util.generarCodigo(parametro, conteo));
        }
        for (Deposito deposito: recaudacion.getDepositos()) {
            parametro = rep.findByTablaAndTipo(tabla_deposito,tipo);
            conteo=rep.findConteoTelefono();
            deposito.setCodigo(Util.generarCodigo(parametro, conteo));
        }
        for (Transferencia transferencia: recaudacion.getTransferencias()) {
            parametro = rep.findByTablaAndTipo(tabla_transferencia,tipo);
            conteo=rep.findConteoTelefono();
            transferencia.setCodigo(Util.generarCodigo(parametro, conteo));
        }
        for (TarjetaCredito tarjeta_credito: recaudacion.getTarjetas_creditos()) {
            parametro = rep.findByTablaAndTipo(tabla_tarjeta_credito,tipo);
            conteo=rep.findConteoTelefono();
            tarjeta_credito.setCodigo(Util.generarCodigo(parametro, conteo));
        }
        for (TarjetaDebito tarjeta_debito: recaudacion.getTarjetas_debitos()) {
            parametro = rep.findByTablaAndTipo(tabla_tarjeta_debito,tipo);
            conteo=rep.findConteoTelefono();
            tarjeta_debito.setCodigo(Util.generarCodigo(parametro, conteo));
        }
        for (Compensacion compensacion: recaudacion.getCompensaciones()) {
            parametro = rep.findByTablaAndTipo(tabla_compensacion,tipo);
            conteo=rep.findConteoTelefono();
            compensacion.setCodigo(Util.generarCodigo(parametro, conteo));
        }
        for (RetencionVenta retencion_venta: recaudacion.getRetenciones_ventas()) {
            parametro = rep.findByTablaAndTipo(tabla_retencion_venta,tipo);
            conteo=rep.findConteoTelefono();
            retencion_venta.setCodigo(Util.generarCodigo(parametro, conteo));
        }
    }
}
