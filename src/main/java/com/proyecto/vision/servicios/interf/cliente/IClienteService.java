package com.proyecto.vision.servicios.interf.cliente;

import com.proyecto.vision.modelos.cliente.Cliente;
import com.proyecto.vision.servicios.interf.IGenericoService;

import java.util.List;

public interface IClienteService extends IGenericoService<Cliente> {
    void validar(Cliente cliente);
    List<Cliente> consultarPorEmpresa(long empresaId);
    List<Cliente> consultarPorEstado(String estado);
    List<Cliente> consultarPorEmpresaYEstado(long empresaId, String estado);
    Cliente obtenerPorIdentificacionYEmpresaYEstado(String identificacion, long empresaId, String estado);
    Cliente obtenerPorRazonSocialYEmpresaYEstado(String razonSocial, long empresaId, String estado);
    Cliente activar(Cliente cliente);
	Cliente inactivar(Cliente cliente);
    String existe(Cliente cliente);
    Cliente validarIdentificacionPorEmpresa(String identificacion, long empresaId);
    Cliente buscarClienteBase(Cliente cliente);
    Cliente buscarContribuyente(Cliente cliente);
}
