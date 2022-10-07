package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.EquivalenciaMedida;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;
import java.util.Optional;

public interface ITablaEquivalenciaMedidaService extends IGenericoService<EquivalenciaMedida> {
    Optional<EquivalenciaMedida> obtenerMedida1Medida2(EquivalenciaMedida tabla);
    List<EquivalenciaMedida> buscar(EquivalenciaMedida tem);
    List<EquivalenciaMedida> buscarMedidasEquivalentes(EquivalenciaMedida equivalencias);
}
