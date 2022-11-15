package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.Estacion;
import com.proyecto.sicecuador.repositorios.usuario.IEstablecimientoRepository;
import com.proyecto.sicecuador.repositorios.usuario.IEstacionRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.IEstacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstacionService implements IEstacionService {

	@Autowired
	private IEstacionRepository rep;
	@Autowired
	private IEstablecimientoRepository rep_establecimiento;
	
	@Override
	public Estacion crear(Estacion estacion) {
		Optional<String>codigo=Util.generarCodigo(Constantes.tabla_estacion);
		if (codigo.isEmpty()) {
			throw new CodigoNoExistenteException();
		}
		return rep.save(estacion);
	}

	@Override
	public Estacion actualizar(Estacion estacion) {
		return rep.save(estacion);
	}

	@Override
	public Estacion eliminar(Estacion estacion) {
		rep.deleteById(estacion.getId()); 
		return estacion;
	}

	@Override
	public Optional<Estacion> obtener(Estacion estacion) {
		return rep.findById(estacion.getId());
	}

	@Override
	public List<Estacion> consultar() {
		return rep.findAll();
	}

	@Override
	public Page<Estacion> consultarPagina(Pageable pageable) {
		return rep.findAll(pageable);
	}

	@Override
	public boolean importar(MultipartFile archivo_temporal) {
		try {
            List<Estacion> estaciones=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal, 16);
            for (List<String> datos: info) {
                Estacion estacion = new Estacion(datos);
                Optional<Establecimiento> establecimiento=rep_establecimiento.findById(estacion.getEstablecimiento().getId());
                if(establecimiento.isPresent()){
                    estaciones.add(estacion);
                }
            }
            if(estaciones.isEmpty()){
                return false;
            }
            rep.saveAll(estaciones);
            return true;
        }catch (Exception e){
            return false;
        }
	}

}
