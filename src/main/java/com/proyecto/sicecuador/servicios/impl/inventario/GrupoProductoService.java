package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import com.proyecto.sicecuador.repositorios.inventario.IGrupoProductoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IGrupoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public GrupoProducto crear(GrupoProducto grupo_producto) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_grupo_producto);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	grupo_producto.setCodigo(codigo.get());
    	return rep.save(grupo_producto);
    }

    @Override
    public GrupoProducto actualizar(GrupoProducto grupo_producto) {
        return rep.save(grupo_producto);
    }

    @Override
    public GrupoProducto eliminar(GrupoProducto grupo_producto) {
        rep.deleteById(grupo_producto.getId());
        return grupo_producto;
    }

    @Override
    public Optional<GrupoProducto> obtener(GrupoProducto grupo_producto) {
        return rep.findById(grupo_producto.getId());
    }

    @Override
    public List<GrupoProducto> consultar() {
        return rep.findAll();
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
        List<String> grupos=rep.findGrupos();
        return grupos;
    }
    
    @Override
    public List<String> consultarSubgrupos(String grupo) {
        List<String> subgrupos=rep.findSubgrupos(grupo);
        return subgrupos;
    }
    
    @Override
    public List<String> consultarSecciones(String grupo, String subgrupo) {
        List<String> secciones=rep.findSecciones(grupo, subgrupo);
        return secciones;
    }
    
    @Override
    public List<String> consultarLineas(String grupo, String subgrupo, String seccion) {
        List<String> lineas=rep.findLineas(grupo, subgrupo, seccion);
        return lineas;
    }
    
    @Override
    public List<String> consultarSublineas(String grupo, String subgrupo, String seccion, String linea) {
        List<String> sublineas=rep.findSublineas(grupo, subgrupo, seccion, linea);
        return sublineas;
    }
    
    @Override
    public List<String> consultarPresentaciones(String grupo, String subgrupo, String seccion, String linea, String sublinea) {
        List<String> presentaciones=rep.findPresentaciones(grupo, subgrupo, seccion, linea, sublinea);
        return presentaciones;
    }
    
    @Override
    public Optional<GrupoProducto> obtenerGrupoProducto(String grupo, String subgrupo, String seccion, String linea, String sublinea, String presentacion) {
        Optional<GrupoProducto> grupoProducto=rep.findGrupoProducto(grupo, subgrupo, seccion, linea, sublinea, presentacion);
        return grupoProducto;
    }


    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<GrupoProducto> grupos_productos=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,2);
            for (List<String> datos: info) {
                GrupoProducto caracteristica = new GrupoProducto(datos);
                grupos_productos.add(caracteristica);
            }
            if(grupos_productos.isEmpty()){
                return false;
            }
            rep.saveAll(grupos_productos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
