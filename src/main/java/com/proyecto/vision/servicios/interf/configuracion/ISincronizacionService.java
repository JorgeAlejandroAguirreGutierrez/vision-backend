package com.proyecto.vision.servicios.interf.configuracion;

import com.proyecto.vision.modelos.configuracion.Modelo;
import com.proyecto.vision.modelos.configuracion.Sincronizacion;
import com.proyecto.vision.servicios.interf.IGenericoService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISincronizacionService extends IGenericoService<Sincronizacion> {
	void validar(Sincronizacion sincronizacion);
	List<Sincronizacion> consultarPorEstado(String estado);
	Sincronizacion cargarArchivo(long sincronizacionId, MultipartFile multipartFile);
	List<Modelo> procesar(long sincronizacionId);
	void crearModelos(List<Modelo> modelos);
}
