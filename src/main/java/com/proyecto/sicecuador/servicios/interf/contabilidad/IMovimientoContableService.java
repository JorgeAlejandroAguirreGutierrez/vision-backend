package com.proyecto.sicecuador.servicios.interf.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.MovimientoContable;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IMovimientoContableService extends IGenericoService<MovimientoContable> {
    //List<Proveedor> consultarProducto();
    List<MovimientoContable> buscar(MovimientoContable movimientoContable);
}
