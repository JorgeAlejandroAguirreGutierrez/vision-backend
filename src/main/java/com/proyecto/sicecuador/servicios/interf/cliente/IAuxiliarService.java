package com.proyecto.sicecuador.servicios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.Dependiente;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IAuxiliarService extends IGenericoService<Dependiente> {

    List<Dependiente> consultarRazonSocial(Dependiente auxiliar);
    List<Dependiente>consultarClienteID(Dependiente auxiliar);
}
