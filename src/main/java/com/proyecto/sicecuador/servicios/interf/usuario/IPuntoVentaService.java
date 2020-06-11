package com.proyecto.sicecuador.servicios.interf.usuario;

import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.PuntoVenta;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IPuntoVentaService extends IGenericoService<PuntoVenta> {
    List<PuntoVenta> consultarEstablecimiento(Establecimiento establecimiento);
}
