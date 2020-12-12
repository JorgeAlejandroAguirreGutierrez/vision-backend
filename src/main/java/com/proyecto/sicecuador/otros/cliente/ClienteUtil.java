package com.proyecto.sicecuador.otros.cliente;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.*;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import java.util.Optional;

@Component
public class ClienteUtil {
    private static IParametroRepository rep;
    private final String tabla_cliente="cliente";
    private final String tabla_direccion="direccion";
    private final String tabla_financiamiento="financiamiento";
    private final String tabla_telefono="telefono";
    private final String tabla_celular="celular";
    private final String tabla_correo="correo";
    private final String tabla_telefono_auxiliar="telefono_auxiliar";
    private final String tabla_celular_auxiliar="celular_auxiliar";
    private final String tabla_correo_auxiliar="correo_auxiliar";
    private final String tipo="CREAR";

    @Autowired
    public void initParametro(IParametroRepository rep){
        this.rep = rep;
    }

    @PrePersist
    public void prePersist(Entidad entidad) {
        Cliente cliente=(Cliente)entidad;
        Optional<Parametro> parametro = rep.findByTablaAndTipo(tabla_cliente,tipo);
        long conteo=rep.findConteoCliente();
        cliente.setCodigo(Util.generarCodigo(parametro, conteo));
        if (cliente.getDireccion().getId()==0 && cliente.getFinanciamiento().getId()==0
        && cliente.getTelefonos()!= null && cliente.getCelulares()!= null && cliente.getCorreos()!= null){
            parametro = rep.findByTablaAndTipo(tabla_direccion,tipo);
            conteo=rep.findConteoDireccion();
            cliente.getDireccion().setCodigo(Util.generarCodigo(parametro, conteo));
            parametro = rep.findByTablaAndTipo(tabla_financiamiento,tipo);
            conteo=rep.findConteoDireccion();
            cliente.getFinanciamiento().setCodigo(Util.generarCodigo(parametro, conteo));
            for (Telefono telefono: cliente.getTelefonos()) {
                parametro = rep.findByTablaAndTipo(tabla_telefono,tipo);
                conteo=rep.findConteoTelefono();
                telefono.setCodigo(Util.generarCodigo(parametro, conteo));
            }
            for (Celular celular: cliente.getCelulares()) {
                parametro = rep.findByTablaAndTipo(tabla_celular,tipo);
                conteo=rep.findConteoCelular();
                celular.setCodigo(Util.generarCodigo(parametro, conteo));
            }
            for (Correo correo: cliente.getCorreos()) {
                parametro = rep.findByTablaAndTipo(tabla_correo,tipo);
                conteo=rep.findConteoCorreo();
                correo.setCodigo(Util.generarCodigo(parametro, conteo));
            }
            /*for (Auxiliar auxiliar: cliente.getAuxiliares()) {
                for (TelefonoAuxiliar telefono : auxiliar.getTelefonos()) {
                    parametro = rep.findByTablaAndTipo(tabla_telefono_auxiliar, tipo);
                    conteo = rep.findConteoTelefonoAuxiliar();
                    telefono.setCodigo(Util.generarCodigo(parametro, conteo));
                }
                for (CelularAuxiliar celular : auxiliar.getCelulares()) {
                    parametro = rep.findByTablaAndTipo(tabla_celular_auxiliar, tipo);
                    conteo = rep.findConteoCelularAuxiliar();
                    celular.setCodigo(Util.generarCodigo(parametro, conteo));
                }
                for (CorreoAuxiliar correo : auxiliar.getCorreos()) {
                    parametro = rep.findByTablaAndTipo(tabla_correo_auxiliar, tipo);
                    conteo = rep.findConteoCorreoAuxiliar();
                    correo.setCodigo(Util.generarCodigo(parametro, conteo));
                }
            }*/
        }
    }
}
