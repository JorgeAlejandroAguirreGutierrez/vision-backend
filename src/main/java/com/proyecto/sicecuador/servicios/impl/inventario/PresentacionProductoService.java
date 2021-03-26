package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.inventario.CategoriaProducto;
import com.proyecto.sicecuador.modelos.inventario.GrupoProducto;
import com.proyecto.sicecuador.modelos.inventario.LineaProducto;
import com.proyecto.sicecuador.modelos.inventario.PresentacionProducto;
import com.proyecto.sicecuador.modelos.inventario.SubGrupoProducto;
import com.proyecto.sicecuador.modelos.inventario.SubLineaProducto;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.ICategoriaProductoRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.IGrupoProductoRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.ILineaProductoRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.IPresentacionProductoRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.ISubGrupoProductoRepository;
import com.proyecto.sicecuador.repositorios.interf.inventario.ISubLineaProductoRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.IPresentacionProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PresentacionProductoService implements IPresentacionProductoService {
    @Autowired
    private IPresentacionProductoRepository rep;
    @Autowired
    private IGrupoProductoRepository grupoProductoRepository;
    @Autowired
    private ISubGrupoProductoRepository subGrupoProductoRepository;
    @Autowired
    private ICategoriaProductoRepository categoriaProductoRepository;
    @Autowired
    private ILineaProductoRepository lineaProductoRepository;
    @Autowired
    private ISubLineaProductoRepository subLineaProductoRepository;
    @Autowired
    private static IParametroRepository parametroRep;
    
    @Override
    public PresentacionProducto crear(PresentacionProducto presentacion_producto) {
    	String nombre_grupo=presentacion_producto.getSubLineaProducto().getLineaProducto().getCategoria_producto().getSubGrupoProducto().getGrupoProducto().getNombre();
    	String nombre_sub_grupo=presentacion_producto.getSubLineaProducto().getLineaProducto().getCategoria_producto().getSubGrupoProducto().getNombre();
    	String nombre_categoria=presentacion_producto.getSubLineaProducto().getLineaProducto().getCategoria_producto().getNombre();
    	String nombre_linea=presentacion_producto.getSubLineaProducto().getLineaProducto().getNombre();
    	String nombre_sub_linea=presentacion_producto.getSubLineaProducto().getNombre();
    	String nombre_presentacion=presentacion_producto.getNombre();
    	Optional<GrupoProducto> grupo_producto=grupoProductoRepository.findByNombre(nombre_grupo);
    	GrupoProducto grupo_producto_persistir=null;
    	if(!grupo_producto.isPresent()) {
        	grupo_producto_persistir=new GrupoProducto();
        	grupo_producto_persistir.setNombre(nombre_grupo);
        	grupo_producto_persistir=grupoProductoRepository.save(grupo_producto_persistir);
        } else {
        	grupo_producto_persistir=grupo_producto.get();
        }
    	Optional<SubGrupoProducto>sub_grupo_producto=subGrupoProductoRepository.findByNombre(nombre_sub_grupo);
    	SubGrupoProducto sub_grupo_producto_persistir=null;
    	if(!sub_grupo_producto.isPresent()) {
    		sub_grupo_producto_persistir=new SubGrupoProducto();
        	sub_grupo_producto_persistir.setNombre(nombre_grupo);
        	sub_grupo_producto_persistir.setGrupoProducto(grupo_producto_persistir);
        	sub_grupo_producto_persistir=subGrupoProductoRepository.save(sub_grupo_producto_persistir);
        } else {
        	sub_grupo_producto_persistir=sub_grupo_producto.get();
        }
    	Optional<CategoriaProducto>categoria_producto=categoriaProductoRepository.findByNombre(nombre_categoria);
    	CategoriaProducto categoria_producto_persistir=null;
    	if(!categoria_producto.isPresent()) {
    		categoria_producto_persistir=new CategoriaProducto();
        	categoria_producto_persistir.setNombre(nombre_categoria);
        	categoria_producto_persistir.setSubGrupoProducto(sub_grupo_producto_persistir);
        	categoria_producto_persistir=categoriaProductoRepository.save(categoria_producto_persistir);
    	} else {
    		categoria_producto_persistir=categoria_producto.get();
    	}
    	Optional<LineaProducto>linea_producto=lineaProductoRepository.findByNombre(nombre_linea);
    	LineaProducto linea_producto_persistir=null;
    	if(!linea_producto.isPresent()) {
    		linea_producto_persistir=new LineaProducto();
    		linea_producto_persistir.setNombre(nombre_linea);
    		linea_producto_persistir.setCategoria_producto(categoria_producto_persistir);
    		linea_producto_persistir=lineaProductoRepository.save(linea_producto_persistir);
    	} else {
    		linea_producto_persistir=linea_producto.get();
    	}
    	Optional<SubLineaProducto>sub_linea_producto=subLineaProductoRepository.findByNombre(nombre_sub_linea);
    	SubLineaProducto sub_linea_producto_persistir=null;
    	if(!sub_linea_producto.isPresent()) {
    		sub_linea_producto_persistir=new SubLineaProducto();
    		sub_linea_producto_persistir.setNombre(nombre_sub_linea);
    		sub_linea_producto_persistir.setLineaProducto(linea_producto_persistir);
    		sub_linea_producto_persistir=subLineaProductoRepository.save(sub_linea_producto_persistir);
    	} else {
    		sub_linea_producto_persistir=sub_linea_producto.get();
    	}
    	Optional<PresentacionProducto>presentacion_producto_p=rep.findByNombre(nombre_presentacion);
    	PresentacionProducto presentacion_producto_persistir=null;
    	if(!presentacion_producto_p.isPresent()) {
    		presentacion_producto_persistir=new PresentacionProducto();
    		presentacion_producto_persistir.setNombre(nombre_sub_linea);
    		presentacion_producto_persistir.setSubLineaProducto(sub_linea_producto_persistir);
    		presentacion_producto_persistir=rep.save(presentacion_producto_persistir);
    	} else {
    		presentacion_producto_persistir=presentacion_producto_p.get();
    	}
    	return presentacion_producto_persistir;
    }

    @Override
    public PresentacionProducto actualizar(PresentacionProducto presentacion_producto) {
        return rep.save(presentacion_producto);
    }

    @Override
    public PresentacionProducto eliminar(PresentacionProducto presentacion_producto) {
        rep.deleteById(presentacion_producto.getId());
        return presentacion_producto;
    }

    @Override
    public Optional<PresentacionProducto> obtener(PresentacionProducto presentacion_producto) {
        return rep.findById(presentacion_producto.getId());
    }

    @Override
    public List<PresentacionProducto> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<PresentacionProducto> buscar(PresentacionProducto presentacion_producto) {
        return  rep.findAll(new Specification<PresentacionProducto>() {
            @Override
            public Predicate toPredicate(Root<PresentacionProducto> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!presentacion_producto.getCodigo().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("codigo"), "%"+presentacion_producto.getCodigo()+"%")));
                }
                if (!presentacion_producto.getNombre().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("nombre"), "%"+presentacion_producto.getNombre()+"%")));
                }
                if (!presentacion_producto.getSubLineaProducto().getNombre().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("subLineaProducto").get("nombre"), "%"+presentacion_producto.getSubLineaProducto().getNombre()+"%")));
                }
                if (!presentacion_producto.getSubLineaProducto().getLineaProducto().getNombre().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("subLineaProducto").get("lineaProducto").get("nombre"), "%"+presentacion_producto.getSubLineaProducto().getLineaProducto().getNombre()+"%")));
                }
                if (!presentacion_producto.getSubLineaProducto().getLineaProducto().getCategoria_producto().getNombre().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("subLineaProducto").get("lineaProducto").get("categoriaProducto").get("nombre"), "%"+presentacion_producto.getSubLineaProducto().getLineaProducto().getCategoria_producto().getNombre()+"%")));
                }
                if (!presentacion_producto.getSubLineaProducto().getLineaProducto().getCategoria_producto().getSubGrupoProducto().getNombre().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("subLineaProducto").get("lineaProducto").get("categoriaProducto").get("subGrupoProducto").get("nombre"), "%"+presentacion_producto.getSubLineaProducto().getLineaProducto().getCategoria_producto().getSubGrupoProducto().getNombre()+"%")));
                }
                if (!presentacion_producto.getSubLineaProducto().getLineaProducto().getCategoria_producto().getSubGrupoProducto().getGrupoProducto().getNombre().equals(Constantes.vacio)) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("subLineaProducto").get("lineaProducto").get("categoriaProducto").get("subGrupoProducto").get("grupoProducto").get("nombre"), "%"+presentacion_producto.getSubLineaProducto().getLineaProducto().getCategoria_producto().getSubGrupoProducto().getGrupoProducto().getNombre()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<PresentacionProducto> presentaciones_productos=new ArrayList<>();
            List<List<String>>info= Util.leer_importar(archivo_temporal,0);
            for (List<String> datos: info) {
            	PresentacionProducto presentacion_producto = new PresentacionProducto(datos);
            	presentaciones_productos.add(presentacion_producto);
            }
            if(presentaciones_productos.isEmpty()){
                return false;
            }
            rep.saveAll(presentaciones_productos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
