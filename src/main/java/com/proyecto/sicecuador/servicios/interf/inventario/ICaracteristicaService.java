package com.proyecto.sicecuador.servicios.interf.inventario;

import com.proyecto.sicecuador.modelos.inventario.Bodega;
import com.proyecto.sicecuador.modelos.inventario.Caracteristica;
import com.proyecto.sicecuador.modelos.inventario.Producto;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICaracteristicaService extends IGenericoService<Caracteristica> {
    List<Caracteristica> consultarBienExistencias(Producto _producto);
    List<Caracteristica> consultarBienExistenciasBodega(Producto _producto, Bodega _bodega);

}
