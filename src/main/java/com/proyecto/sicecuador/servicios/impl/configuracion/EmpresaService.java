package com.proyecto.sicecuador.servicios.impl.configuracion;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.configuracion.Empresa;
import com.proyecto.sicecuador.repositorios.configuracion.IEmpresaRepository;
import com.proyecto.sicecuador.servicios.interf.configuracion.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService implements IEmpresaService {
    @Autowired
    private IEmpresaRepository rep;

    @Override
    public Empresa crear(Empresa empresa) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_empresa);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	empresa.setCodigo(codigo.get());
    	empresa.setEstado(Constantes.activo);
    	return rep.save(empresa);
    }

    @Override
    public Empresa actualizar(Empresa empresa) {
        return rep.save(empresa);
    }

    @Override
    public Empresa activar(Empresa empresa) {
        empresa.setEstado(Constantes.activo);
        return rep.save(empresa);
    }

    @Override
    public Empresa inactivar(Empresa empresa) {
        empresa.setEstado(Constantes.inactivo);
        return rep.save(empresa);
    }

    @Override
    public Empresa obtener(long id) {
        Optional<Empresa> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.empresa);
    }

    @Override
    public List<Empresa> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Empresa> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Empresa> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivoTemporal) {
        try {
            List<Empresa> empresas=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivoTemporal, 0);
            for (List<String> datos: info) {
                Empresa empresa = new Empresa(datos);
                empresas.add(empresa);
            }
            rep.saveAll(empresas);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
