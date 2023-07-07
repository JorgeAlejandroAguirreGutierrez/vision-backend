package com.proyecto.vision.servicios.interf.contabilidad;

import com.proyecto.vision.modelos.contabilidad.CuentaContable;
import com.proyecto.vision.modelos.contabilidad.MovimientoContable;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IMovimientoContableService extends IGenericoService<MovimientoContable> {
    void validar(MovimientoContable movimientoContable);
    MovimientoContable activar(MovimientoContable movimientoContable);
    MovimientoContable inactivar(MovimientoContable movimientoContable);
    List<MovimientoContable> buscar(MovimientoContable movimientoContable);
    List<MovimientoContable> consultarPorEstado(String estado);
    List<MovimientoContable> consultarPorEmpresa(long empresaId);
    List<MovimientoContable> consultarPorEmpresaYEstado(long empresaId, String estado);
}
