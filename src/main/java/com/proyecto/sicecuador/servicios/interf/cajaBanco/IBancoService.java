package com.proyecto.sicecuador.servicios.interf.cajaBanco;

import java.util.List;

import com.proyecto.sicecuador.modelos.cajaBanco.Banco;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

public interface IBancoService extends IGenericoService<Banco> {
	void validar(Banco banco);
	Banco activar(Banco banco);
	Banco inactivar(Banco banco);
	List<Banco> consultarPorEstado(String estado);
	List<Banco> consultarPorEmpresa(long empresaId);
	List<Banco> consultarPorEmpresaYEstado(long empresaId, String estado);

}
