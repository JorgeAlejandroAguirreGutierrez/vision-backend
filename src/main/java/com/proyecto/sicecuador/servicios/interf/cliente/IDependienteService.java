package com.proyecto.sicecuador.servicios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.Dependiente;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IDependienteService extends IGenericoService<Dependiente> {
    List<Dependiente> consultarPorRazonSocial(String razonSocial);
    List<Dependiente> consultarPorCliente(long clienteId);
}
