package com.proyecto.vision.servicios.interf.cliente;

import com.proyecto.vision.modelos.cliente.Dependiente;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IDependienteService extends IGenericoService<Dependiente> {
    List<Dependiente> consultarPorRazonSocial(String razonSocial);
    List<Dependiente> consultarPorCliente(long clienteId);
}
