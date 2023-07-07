package com.proyecto.vision.servicios.interf.cajaBanco;

import java.util.List;

import com.proyecto.vision.modelos.cajaBanco.Banco;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IBancoService extends IGenericoService<Banco> {
	void validar(Banco banco);
	Banco activar(Banco banco);
	Banco inactivar(Banco banco);
	List<Banco> consultarPorEstado(String estado);
}
