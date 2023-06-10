package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.DatoInvalidoException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.inventario.CategoriaProducto;
import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import com.proyecto.sicecuador.repositorios.inventario.IGrupoProductoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IGrupoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

@Service
public class GrupoProductoService implements IGrupoProductoService {
    @Autowired
    private IGrupoProductoRepository rep;

    @Override
    public void validar(GrupoProducto grupoProducto) {
        if(grupoProducto.getGrupo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.grupo);
        if(grupoProducto.getSubgrupo().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.subgrupo);
        if(grupoProducto.getSeccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.seccion);
        if(grupoProducto.getCategoriaProducto().equals(Constantes.ceroId)) throw new DatoInvalidoException(Constantes.categoria_producto);
        if (grupoProducto.getCategoriaProducto().getId() == 1) {
            if (grupoProducto.getLinea().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.linea);
            if (grupoProducto.getSublinea().equals(Constantes.vacio))
                throw new DatoInvalidoException(Constantes.sublinea);
            if (grupoProducto.getPresentacion().equals(Constantes.vacio))
                throw new DatoInvalidoException(Constantes.sublinea);
        }
        if(grupoProducto.getCuentaContable().equals(Constantes.ceroId)) throw new DatoInvalidoException(Constantes.cuenta_contable);
    }
    
    @Override
    public GrupoProducto crear(GrupoProducto grupoProducto) {
    	validar(grupoProducto);
        Optional<String>codigo=Util.generarCodigo(Constantes.tabla_grupo_producto);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	grupoProducto.setCodigo(codigo.get());
    	grupoProducto.setEstado(Constantes.activo);
    	GrupoProducto res = rep.save(grupoProducto);
        res.normalizar();
        return res;
    }

    @Override
    public GrupoProducto actualizar(GrupoProducto grupoProducto) {
        validar(grupoProducto);
        GrupoProducto res = rep.save(grupoProducto);
        res.normalizar();
        return res;
    }

    @Override
    public GrupoProducto activar(GrupoProducto grupoProducto) {
        validar(grupoProducto);
        grupoProducto.setEstado(Constantes.activo);
        GrupoProducto res = rep.save(grupoProducto);
        res.normalizar();
        return res;
    }

    @Override
    public GrupoProducto inactivar(GrupoProducto grupoProducto) {
        validar(grupoProducto);
        grupoProducto.setEstado(Constantes.inactivo);
        GrupoProducto res = rep.save(grupoProducto);
        res.normalizar();
        return res;
    }
    @Override
    public GrupoProducto obtener(long id) {
        Optional<GrupoProducto> grupoProducto= rep.findById(id);
        if(grupoProducto.isPresent()) {
        	GrupoProducto res = grupoProducto.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.grupo_producto);
    }

    @Override
    public List<GrupoProducto> consultar() {
        return rep.consultar();
    }
    
    @Override
    public List<GrupoProducto> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<GrupoProducto> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<GrupoProducto> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }
    
    @Override
    public Page<GrupoProducto> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<GrupoProducto> buscar(GrupoProducto grupo_producto) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (!grupo_producto.getCodigo().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+grupo_producto.getCodigo()+"%")));
		    }
		    if (!grupo_producto.getGrupo().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("grupo"), "%"+grupo_producto.getGrupo()+"%")));
		    }
		    if (!grupo_producto.getSubgrupo().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("subgrupo"), "%"+grupo_producto.getSubgrupo()+"%")));
		    }
		    if (!grupo_producto.getSeccion().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("seccion"), "%"+grupo_producto.getSeccion()+"%")));
		    }
		    if (!grupo_producto.getLinea().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("linea"), "%"+grupo_producto.getLinea()+"%")));
		    }
		    if (!grupo_producto.getSublinea().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("sublinea"), "%"+grupo_producto.getSublinea()+"%")));
		    }
		    if (!grupo_producto.getPresentacion().equals(Constantes.vacio)) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("presentacion"), "%"+grupo_producto.getPresentacion()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }
    
    @Override
    public List<String> consultarGrupos() {
        return rep.findGrupos(Constantes.activo);
    }
    
    @Override
    public List<String> consultarSubgrupos(String grupo) {
        return rep.findSubgrupos(grupo, Constantes.activo);
    }
    
    @Override
    public List<String> consultarSecciones(String grupo, String subgrupo) {
        return rep.findSecciones(grupo, subgrupo, Constantes.activo);
    }
    
    @Override
    public List<String> consultarLineas(String grupo, String subgrupo, String seccion) {
        return rep.findLineas(grupo, subgrupo, seccion, Constantes.activo);
    }
    
    @Override
    public List<String> consultarSublineas(String grupo, String subgrupo, String seccion, String linea) {
        return rep.findSublineas(grupo, subgrupo, seccion, linea, Constantes.activo);
    }
    
    @Override
    public List<String> consultarPresentaciones(String grupo, String subgrupo, String seccion, String linea, String sublinea) {
        return rep.findPresentaciones(grupo, subgrupo, seccion, linea, sublinea, Constantes.activo);
    }
    
    @Override
    public GrupoProducto obtenerGrupoProducto(String grupo, String subgrupo, String seccion, String linea, String sublinea, String presentacion) {
        Optional<GrupoProducto> grupoProducto = rep.findGrupoProducto(grupo, subgrupo, seccion, linea, sublinea, presentacion, Constantes.activo);
        if(grupoProducto.isPresent()) {
        	GrupoProducto res = grupoProducto.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.grupo_producto);
    }
}
