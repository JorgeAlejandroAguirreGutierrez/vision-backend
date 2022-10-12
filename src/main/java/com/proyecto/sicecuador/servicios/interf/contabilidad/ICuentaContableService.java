package com.proyecto.sicecuador.servicios.interf.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.CuentaContable;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface ICuentaContableService extends IGenericoService<CuentaContable> {
    //List<Proveedor> consultarProducto();
    List<CuentaContable> buscar(CuentaContable cuentaContable);
}
