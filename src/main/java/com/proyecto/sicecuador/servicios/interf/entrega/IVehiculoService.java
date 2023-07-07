package com.proyecto.vision.servicios.interf.entrega;

import java.util.List;

import com.proyecto.vision.modelos.entrega.Vehiculo;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IVehiculoService extends IGenericoService<Vehiculo> {
    Vehiculo activar(Vehiculo vehiculo);
    Vehiculo inactivar(Vehiculo vehiculo);
    List<Vehiculo> consultarPorEmpresa(long empresaId);
    List<Vehiculo> consultarPorEstado(String estado);
    List<Vehiculo> consultarPorEmpresaYEstado(long empresaId, String estado);
}
