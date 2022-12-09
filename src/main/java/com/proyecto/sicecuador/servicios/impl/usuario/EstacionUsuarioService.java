package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.usuario.EstacionUsuario;
import com.proyecto.sicecuador.repositorios.usuario.IEstacionUsuarioRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IEstacionUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@Service
public class EstacionUsuarioService implements IEstacionUsuarioService {

	@Autowired
	private IEstacionUsuarioRepository rep;
	
	@Override
	public EstacionUsuario crear(EstacionUsuario estacionUsuario) {
		Optional<String>codigo=Util.generarCodigo(Constantes.tabla_estacion_usuario);
		if (codigo.isEmpty()) {
			throw new CodigoNoExistenteException();
		}
		return rep.save(estacionUsuario);
	}

	@Override
	public EstacionUsuario actualizar(EstacionUsuario estacionUsuario) {
		return rep.save(estacionUsuario);
	}

	@Override
	public EstacionUsuario obtener(long id) {
		Optional<EstacionUsuario> res = rep.findById(id);
		if(res.isPresent()) {
			return res.get();
		}
		throw new EntidadNoExistenteException(Constantes.estacion_usuario);
	}

	@Override
	public List<EstacionUsuario> consultar() {
		return rep.findAll();
	}

	@Override
	public Page<EstacionUsuario> consultarPagina(Pageable pageable) {
		return rep.findAll(pageable);
	}

	@Override
	public void importar(MultipartFile archivo_temporal) {
	}

}
