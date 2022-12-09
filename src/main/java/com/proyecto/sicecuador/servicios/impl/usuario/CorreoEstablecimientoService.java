package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.CorreoEstablecimiento;
import com.proyecto.sicecuador.repositorios.usuario.IEstablecimientoRepository;
import com.proyecto.sicecuador.repositorios.usuario.ICorreoEstablecimientoRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.ICorreoEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CorreoEstablecimientoService implements ICorreoEstablecimientoService {

	@Autowired
	private ICorreoEstablecimientoRepository rep;
	@Autowired
	private IEstablecimientoRepository rep_establecimiento;
	
	@Override
	public CorreoEstablecimiento crear(CorreoEstablecimiento correoEstablecimiento) {
		Optional<String>codigo=Util.generarCodigo(Constantes.tabla_correo_establecimiento);
		if (codigo.isEmpty()) {
			throw new CodigoNoExistenteException();
		}
		return rep.save(correoEstablecimiento);
	}

	@Override
	public CorreoEstablecimiento actualizar(CorreoEstablecimiento correoEstablecimiento) {
		return rep.save(correoEstablecimiento);
	}

	@Override
	public CorreoEstablecimiento obtener(long id) {
		Optional<CorreoEstablecimiento> res= rep.findById(id);
		if(res.isPresent()) {
			return res.get();
		}
		throw new EntidadNoExistenteException(Constantes.correo_establecimiento);
	}

	@Override
	public List<CorreoEstablecimiento> consultar() {
		return rep.findAll();
	}

	@Override
	public Page<CorreoEstablecimiento> consultarPagina(Pageable pageable) {
		return rep.findAll(pageable);
	}

	@Override
	public void importar(MultipartFile archivo_temporal) {
		try {
            List<CorreoEstablecimiento> correos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal, 16);
            for (List<String> datos: info) {
                CorreoEstablecimiento correoEstablecimiento = new CorreoEstablecimiento(datos);
                Optional<Establecimiento> establecimiento=rep_establecimiento.findById(correoEstablecimiento.getEstablecimiento().getId());
                if(establecimiento.isPresent()){
                    correos.add(correoEstablecimiento);
                }
            }
            rep.saveAll(correos);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
	}

}
