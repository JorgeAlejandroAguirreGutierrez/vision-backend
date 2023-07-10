package com.proyecto.vision.servicios.interf.cliente;

import com.proyecto.vision.modelos.cliente.CalificacionCliente;
import com.proyecto.vision.modelos.cliente.Cliente;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IClienteService extends IGenericoService<Cliente> {
    void validar(Cliente cliente);
    List<Cliente> consultarPorEmpresa(long empresaId);
    List<Cliente> consultarPorEstado(String estado);
    List<Cliente> consultarPorEmpresaYEstado(long empresaId, String estado);
	Cliente activar(Cliente cliente);
	Cliente inactivar(Cliente cliente);
    String existe(Cliente cliente);
    Cliente obtenerPorIdentificacion(String identificacion);
    Cliente obtenerPorRazonSocial(String razonSocial);
    List<Cliente> buscar(Cliente cliente);
    Cliente validarIdentificacionPorEmpresa(long empresaId, String identificacion);
    Cliente buscarClienteBase(Cliente cliente);
    Cliente buscarContribuyente(Cliente cliente);
}