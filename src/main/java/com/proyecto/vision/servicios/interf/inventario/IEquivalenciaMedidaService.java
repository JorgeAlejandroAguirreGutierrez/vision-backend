package com.proyecto.vision.servicios.interf.inventario;

import com.proyecto.vision.modelos.inventario.EquivalenciaMedida;
import com.proyecto.vision.modelos.inventario.Medida;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;
import java.util.Optional;

public interface IEquivalenciaMedidaService extends IGenericoService<EquivalenciaMedida> {
    void validar(EquivalenciaMedida equivalenciaMedida);
    EquivalenciaMedida activar(EquivalenciaMedida equivalenciaMedida);
    EquivalenciaMedida inactivar(EquivalenciaMedida equivalenciaMedida);
    EquivalenciaMedida obtenerPorMedidaIniYMedidaFin(long medidaIniId, long medidaFinId);
    List<EquivalenciaMedida> consultarPorEstado(String estado);
    List<EquivalenciaMedida> buscar(EquivalenciaMedida equivalenciaMedida);
    List<EquivalenciaMedida> buscarMedidasEquivalentes(EquivalenciaMedida equivalenciaMedida);

}
