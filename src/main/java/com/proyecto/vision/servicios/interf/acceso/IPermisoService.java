package com.proyecto.vision.servicios.interf.acceso;

import com.proyecto.vision.modelos.acceso.Permiso;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IPermisoService extends IGenericoService<Permiso> {
    Permiso activar(Permiso permiso);
    Permiso inactivar(Permiso permiso);
    List<Permiso> consultarPorEstado(String estado);
    List<Permiso> consultarPorPerfil(long perfilId);
}
