package com.proyecto.vision.servicios.interf.cliente;

import com.proyecto.vision.modelos.cliente.RetencionCliente;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IRetencionClienteService extends IGenericoService<RetencionCliente> {
    void validar(RetencionCliente retencionCliente);
}
