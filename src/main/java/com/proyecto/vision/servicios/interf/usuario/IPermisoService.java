package com.proyecto.vision.servicios.interf.usuario;

import com.proyecto.vision.modelos.usuario.Perfil;
import com.proyecto.vision.modelos.usuario.Permiso;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IPermisoService extends IGenericoService<Permiso> {
    Permiso activar(Permiso permiso);
    Permiso inactivar(Permiso permiso);
    List<Permiso> consultarPorEstado(String estado);
}
