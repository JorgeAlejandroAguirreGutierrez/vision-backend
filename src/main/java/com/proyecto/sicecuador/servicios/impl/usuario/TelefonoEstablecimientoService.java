package com.proyecto.sicecuador.servicios.impl.usuario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.usuario.Establecimiento;
import com.proyecto.sicecuador.modelos.usuario.TelefonoEstablecimiento;
import com.proyecto.sicecuador.repositorios.usuario.IEstablecimientoRepository;
import com.proyecto.sicecuador.repositorios.usuario.ITelefonoEstablecimientoRepository;
import com.proyecto.sicecuador.servicios.interf.usuario.ITelefonoEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TelefonoEstablecimientoService implements ITelefonoEstablecimientoService {

	@Autowired
	private ITelefonoEstablecimientoRepository rep;
	@Autowired
	private IEstablecimientoRepository rep_establecimiento;
	
	@Override
	public TelefonoEstablecimiento crear(TelefonoEstablecimiento telefonoEstablecimiento) {
		Optional<String>codigo=Util.generarCodigo(Constantes.tabla_telefono_establecimiento);
		if (codigo.isEmpty()) {
			throw new CodigoNoExistenteException();
		}
		return rep.save(telefonoEstablecimiento);
	}

	@Override
	public TelefonoEstablecimiento actualizar(TelefonoEstablecimiento telefonoEstablecimiento) {
		return rep.save(telefonoEstablecimiento);
	}

	@Override
	public TelefonoEstablecimiento eliminar(TelefonoEstablecimiento telefonoEstablecimiento) {
		rep.deleteById(telefonoEstablecimiento.getId()); 
		return telefonoEstablecimiento;
	}

	@Override
	public Optional<TelefonoEstablecimiento> obtener(TelefonoEstablecimiento telefonoEstablecimiento) {
		return rep.findById(telefonoEstablecimiento.getId());
	}

	@Override
	public List<TelefonoEstablecimiento> consultar() {
		return rep.findAll();
	}

	@Override
	public Page<TelefonoEstablecimiento> consultarPagina(Pageable pageable) {
		return rep.findAll(pageable);
	}

	@Override
	public boolean importar(MultipartFile archivo_temporal) {
		try {
            List<TelefonoEstablecimiento> telefonos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal, 16);
            for (List<String> datos: info) {
                TelefonoEstablecimiento telefonoEstablecimiento = new TelefonoEstablecimiento(datos);
                Optional<Establecimiento> establecimiento=rep_establecimiento.findById(telefonoEstablecimiento.getEstablecimiento().getId());
                if(establecimiento.isPresent()){
                    telefonos.add(telefonoEstablecimiento);
                }
            }
            if(telefonos.isEmpty()){
                return false;
            }
            rep.saveAll(telefonos);
            return true;
        }catch (Exception e){
            return false;
        }
	}

}
