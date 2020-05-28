package com.proyecto.sicecuador.servicios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IAuxiliarService extends IGenericoService<Auxiliar> {

    List<Auxiliar> consultarRazonSocial(Auxiliar auxiliar);
    List<Auxiliar>consultarClienteID(Auxiliar auxiliar);
}
