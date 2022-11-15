package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.usuario.EstacionUsuario;
import com.proyecto.sicecuador.repositorios.usuario.IEstacionUsuarioRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IEstacionUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstacionUsuarioService implements IEstacionUsuarioService {

	@Autowired
	private IEstacionUsuarioRepository rep;
	
	@Override
	public EstacionUsuario crear(EstacionUsuario estacion_usuario) {
		Optional<String>codigo=Util.generarCodigo(Constantes.tabla_estacion_usuario);
		if (codigo.isEmpty()) {
			throw new CodigoNoExistenteException();
		}
		return rep.save(estacion_usuario);
	}

	@Override
	public EstacionUsuario actualizar(EstacionUsuario estacion_usuario) {
		return rep.save(estacion_usuario);
	}

	@Override
	public EstacionUsuario eliminar(EstacionUsuario estacion_usuario) {
		rep.deleteById(estacion_usuario.getId()); 
		return estacion_usuario;
	}

	@Override
	public Optional<EstacionUsuario> obtener(EstacionUsuario estacion_usuario) {
		return rep.findById(estacion_usuario.getId());
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
	public boolean importar(MultipartFile archivo_temporal) {
		return false;
	}

}
