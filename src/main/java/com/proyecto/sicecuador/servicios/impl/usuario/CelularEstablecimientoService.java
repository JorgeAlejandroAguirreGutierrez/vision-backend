package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.usuario.CelularEstablecimiento;
import com.proyecto.sicecuador.repositorios.usuario.IEstablecimientoRepository;
import com.proyecto.sicecuador.repositorios.usuario.ICelularEstablecimientoRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.ICelularEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CelularEstablecimientoService implements ICelularEstablecimientoService {

	@Autowired
	private ICelularEstablecimientoRepository rep;
	@Autowired
	private IEstablecimientoRepository rep_establecimiento;
	
	@Override
	public CelularEstablecimiento crear(CelularEstablecimiento celularEstablecimiento) {
		Optional<String>codigo=Util.generarCodigo(Constantes.tabla_celular_establecimiento);
		if (codigo.isEmpty()) {
			throw new CodigoNoExistenteException();
		}
		return rep.save(celularEstablecimiento);
	}

	@Override
	public CelularEstablecimiento actualizar(CelularEstablecimiento celularEstablecimiento) {
		return rep.save(celularEstablecimiento);
	}

	@Override
	public CelularEstablecimiento obtener(long id) {
		Optional<CelularEstablecimiento> res= rep.findById(id);
		if(res.isPresent()) {
			return res.get();
		}
		throw new EntidadNoExistenteException(Constantes.tabla_celular_establecimiento);
	}

	@Override
	public List<CelularEstablecimiento> consultar() {
		return rep.findAll();
	}

	@Override
	public Page<CelularEstablecimiento> consultarPagina(Pageable pageable) {
		return rep.findAll(pageable);
	}
}
