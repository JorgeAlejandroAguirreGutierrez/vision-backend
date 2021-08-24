package com.proyecto.sicecuador.servicios.interf.proveedor;

import com.proyecto.sicecuador.modelos.proveedor.Proveedor;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IProveedorService extends IGenericoService<Proveedor> {
    //List<Proveedor> consultarProveedor();
    List<Proveedor> buscar(Proveedor proveedor);
}
