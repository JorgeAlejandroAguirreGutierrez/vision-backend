package com.proyecto.sicecuador.servicios.interf.usuario;

import com.proyecto.sicecuador.modelos.usuario.Perfil;
import com.proyecto.sicecuador.modelos.usuario.Permiso;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IPermisoService extends IGenericoService<Permiso> {
    Permiso activar(Permiso permiso);
    Permiso inactivar(Permiso permiso);
    List<Permiso> consultarPorEstado(String estado);
}
