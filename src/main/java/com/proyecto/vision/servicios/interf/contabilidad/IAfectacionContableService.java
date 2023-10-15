package com.proyecto.vision.servicios.interf.contabilidad;

import com.proyecto.vision.modelos.contabilidad.AfectacionContable;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IAfectacionContableService extends IGenericoService<AfectacionContable> {
	void validar(AfectacionContable afectacionContable);
	AfectacionContable activar(AfectacionContable afectacionContable);
	AfectacionContable inactivar(AfectacionContable afectacionContable);
	List<AfectacionContable> consultarPorEstado(String estado);
}
