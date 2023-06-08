package com.proyecto.sicecuador.servicios.interf.entrega;

import java.util.List;

import com.proyecto.sicecuador.modelos.entrega.VehiculoTransporte;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IVehiculoTransporteService extends IGenericoService<VehiculoTransporte> {
    VehiculoTransporte activar(VehiculoTransporte vehiculoTransporte);
    VehiculoTransporte inactivar(VehiculoTransporte vehiculoTransporte);
    List<VehiculoTransporte> consultarPorEstado(String estado);
}
