package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.inventario.EquivalenciaMedida;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.repositorios.inventario.IEquivalenciaMedidaRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IEquivalenciaMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EquivalenciaMedidaService implements IEquivalenciaMedidaService {
    @Autowired
    private IEquivalenciaMedidaRepository rep;

    @Override
    public void validar(EquivalenciaMedida equivalenciaMedida) {
        if(equivalenciaMedida.getEquivalencia() == Constantes.cero) throw new DatoInvalidoException(Constantes.equivalencia);
        if(equivalenciaMedida.getMedidaIni().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.medida);
        if(equivalenciaMedida.getMedidaEqui().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.medida);
    }
    
    @Override
    public EquivalenciaMedida crear(EquivalenciaMedida equivalenciaMedida) {
    	validar(equivalenciaMedida);
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_equivalencia_medida);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	equivalenciaMedida.setCodigo(codigo.get());
    	equivalenciaMedida.setEstado(Constantes.activo);
        EquivalenciaMedida res = rep.save(equivalenciaMedida);
        res.normalizar();
        return res;
    }

    @Override
    public EquivalenciaMedida actualizar(EquivalenciaMedida equivalenciaMedida) {
		validar(equivalenciaMedida);
        EquivalenciaMedida res = rep.save(equivalenciaMedida);
        res.normalizar();
        return res;
    }

    @Override
    public EquivalenciaMedida activar(EquivalenciaMedida equivalenciaMedida) {
        validar(equivalenciaMedida);
        equivalenciaMedida.setEstado(Constantes.activo);
        EquivalenciaMedida res = rep.save(equivalenciaMedida);
        res.normalizar();
        return res;
    }

    @Override
    public EquivalenciaMedida inactivar(EquivalenciaMedida equivalenciaMedida) {
        validar(equivalenciaMedida);
        equivalenciaMedida.setEstado(Constantes.inactivo);
        EquivalenciaMedida res = rep.save(equivalenciaMedida);
        res.normalizar();
        return res;
    }

    @Override
    public EquivalenciaMedida obtener(long id) {
        Optional<EquivalenciaMedida> equivalenciaMedida = rep.findById(id);
        if(equivalenciaMedida.isPresent()) {
        	EquivalenciaMedida res = equivalenciaMedida.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.equivalencia_medida);
    }

    @Override
    public List<EquivalenciaMedida> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<EquivalenciaMedida> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<EquivalenciaMedida> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<EquivalenciaMedida> tablas=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal,0);
            for (List<String> datos: info) {
                EquivalenciaMedida tabla = new EquivalenciaMedida(datos);
                tablas.add(tabla);
            }
            rep.saveAll(tablas);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
