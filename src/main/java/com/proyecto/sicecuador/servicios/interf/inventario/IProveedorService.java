package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.Proveedor;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IProveedorService extends IGenericoService<Proveedor> {
    //List<Proveedor> consultarProducto();
    List<Proveedor> buscar(Proveedor proveedor);
}
