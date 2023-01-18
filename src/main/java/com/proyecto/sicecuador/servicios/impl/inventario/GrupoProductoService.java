package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
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
    public GrupoProducto crear(GrupoProducto grupoProducto) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_grupo_producto);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	grupoProducto.setCodigo(codigo.get());
    	grupoProducto.setEstado(Constantes.activo);
    	return rep.save(grupoProducto);
    }

    @Override
    public GrupoProducto actualizar(GrupoProducto grupoProducto) {
        return rep.save(grupoProducto);
    }

    @Override
    public GrupoProducto activar(GrupoProducto grupoProducto) {
        grupoProducto.setEstado(Constantes.activo);
        return rep.save(grupoProducto);
    }

    @Override
    public GrupoProducto inactivar(GrupoProducto grupoProducto) {
        grupoProducto.setEstado(Constantes.inactivo);
        return rep.save(grupoProducto);
    }
    @Override
    public GrupoProducto obtener(long id) {
        Optional<GrupoProducto> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.grupo_producto);
    }

    @Override
    public List<GrupoProducto> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<GrupoProducto> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
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
        List<String> grupos=rep.findGrupos(Constantes.activo);
        return grupos;
    }
    
    @Override
    public List<String> consultarSubgrupos(String grupo) {
        List<String> subgrupos=rep.findSubgrupos(grupo, Constantes.activo);
        return subgrupos;
    }
    
    @Override
    public List<String> consultarSecciones(String grupo, String subgrupo) {
        List<String> secciones=rep.findSecciones(grupo, subgrupo, Constantes.activo);
        return secciones;
    }
    
    @Override
    public List<String> consultarLineas(String grupo, String subgrupo, String seccion) {
        List<String> lineas=rep.findLineas(grupo, subgrupo, seccion, Constantes.activo);
        return lineas;
    }
    
    @Override
    public List<String> consultarSublineas(String grupo, String subgrupo, String seccion, String linea) {
        List<String> sublineas=rep.findSublineas(grupo, subgrupo, seccion, linea, Constantes.activo);
        return sublineas;
    }
    
    @Override
    public List<String> consultarPresentaciones(String grupo, String subgrupo, String seccion, String linea, String sublinea) {
        List<String> presentaciones=rep.findPresentaciones(grupo, subgrupo, seccion, linea, sublinea, Constantes.activo);
        return presentaciones;
    }
    
    @Override
    public GrupoProducto obtenerGrupoProducto(String grupo, String subgrupo, String seccion, String linea, String sublinea, String presentacion) {
        Optional<GrupoProducto> res=rep.findGrupoProducto(grupo, subgrupo, seccion, linea, sublinea, presentacion, Constantes.activo);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.grupo_producto);
    }


    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<GrupoProducto> grupos_productos=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,2);
            for (List<String> datos: info) {
                GrupoProducto caracteristica = new GrupoProducto(datos);
                grupos_productos.add(caracteristica);
            }
            rep.saveAll(grupos_productos);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
