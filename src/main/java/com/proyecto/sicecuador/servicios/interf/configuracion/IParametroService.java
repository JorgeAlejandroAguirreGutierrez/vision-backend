package com.proyecto.sicecuador.servicios.interf.configuracion;

import com.proyecto.sicecuador.modelos.Entidad;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.Optional;

public interface IParametroService extends IGenericoService<Parametro> {
    Optional<Parametro> obtenerTipo(Parametro parametro);
    Optional<Parametro> obtenerTipoTabla(Parametro parametro);
}
