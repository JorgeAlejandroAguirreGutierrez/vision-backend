package com.proyecto.sicecuador.servicios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.RetencionCliente;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IRetencionClienteService extends IGenericoService<RetencionCliente> {
    void validar(RetencionCliente retencionCliente);
}
