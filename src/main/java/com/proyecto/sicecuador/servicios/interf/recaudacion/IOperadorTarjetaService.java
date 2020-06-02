package com.proyecto.sicecuador.servicios.interf.recaudacion;

import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.recaudacion.OperadorTarjeta;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IOperadorTarjetaService extends IGenericoService<OperadorTarjeta> {
    List<OperadorTarjeta> consultarTipo(OperadorTarjeta operador_tarjeta);
}
