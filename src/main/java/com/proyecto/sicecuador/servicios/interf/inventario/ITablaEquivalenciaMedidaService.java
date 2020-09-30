package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.modelos.inventario.TablaEquivalenciaMedida;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.Optional;

public interface ITablaEquivalenciaMedidaService extends IGenericoService<TablaEquivalenciaMedida> {
    Optional<TablaEquivalenciaMedida> obtenerMedida1Medida2(TablaEquivalenciaMedida tabla);
}
