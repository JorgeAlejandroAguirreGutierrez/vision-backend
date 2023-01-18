package com.proyecto.sicecuador.servicios.interf.contabilidad;

import com.proyecto.sicecuador.modelos.contabilidad.AfectacionContable;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IAfectacionContableService extends IGenericoService<AfectacionContable> {
	AfectacionContable activar(AfectacionContable afectacionContable);
	AfectacionContable inactivar(AfectacionContable afectacionContable);
	List<AfectacionContable> consultarActivos();
    List<AfectacionContable> buscar(AfectacionContable afectacionContable);
}
