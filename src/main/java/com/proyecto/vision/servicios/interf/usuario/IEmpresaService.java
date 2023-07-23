package com.proyecto.vision.servicios.interf.usuario;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.proyecto.vision.modelos.compra.Proveedor;
import com.proyecto.vision.modelos.usuario.Empresa;
import com.proyecto.vision.servicios.interf.IGenericoService;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IEmpresaService extends IGenericoService<Empresa> {
	void validar(Empresa empresa);
	Empresa activar(Empresa empresa);
	Empresa inactivar(Empresa empresa);
	List<Empresa> consultarPorEstado(String estado);
	Empresa validarIdentificacion(String identificacion);
	Empresa subirCertificado(long empresaId, MultipartFile file) throws IOException;
	Resource bajarCertificado(long empresaId) throws MalformedURLException;
}
