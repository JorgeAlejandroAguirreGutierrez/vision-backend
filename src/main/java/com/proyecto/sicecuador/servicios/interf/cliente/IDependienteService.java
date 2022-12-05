package com.proyecto.sicecuador.servicios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.Dependiente;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;
import java.util.Optional;

public interface IDependienteService extends IGenericoService<Dependiente> {
	List<Dependiente> consultarActivos();
    List<Dependiente> consultarPorRazonSocial(Dependiente dependiente);
    List<Dependiente> consultarPorCliente(Dependiente dependiente);
    Dependiente activar(Dependiente dependiente);
    Dependiente inactivar(Dependiente dependiente);
}
