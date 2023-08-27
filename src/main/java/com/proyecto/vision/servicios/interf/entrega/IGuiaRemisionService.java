package com.proyecto.vision.servicios.interf.entrega;

import java.util.List;
import java.util.Optional;
import com.proyecto.vision.modelos.entrega.GuiaRemision;
import com.proyecto.vision.modelos.venta.NotaCredito;
import com.proyecto.vision.servicios.interf.IGenericoService;

public interface IGuiaRemisionService extends IGenericoService<GuiaRemision> {
	Optional<GuiaRemision> obtenerPorFactura(long facturaId);
	void validar(GuiaRemision guiaRemision);
	GuiaRemision anular(GuiaRemision guiaRemision);
	List<GuiaRemision> consultarPorEmpresa(long empresaId);
	List<GuiaRemision> consultarPorEmpresaYEstado(long empresaId, String estado);
	List<GuiaRemision> consultarPorFacturaYEmpresaYEstado(long facturaId, long empresaId, String estado);
	List<GuiaRemision> consultarPorFacturaYEmpresaYEstadoDiferente(long facturaId, long empresaId, String estado);
}