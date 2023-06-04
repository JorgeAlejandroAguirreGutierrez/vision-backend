package com.proyecto.sicecuador.servicios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.CalificacionCliente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;

import java.util.List;

public interface IClienteService extends IGenericoService<Cliente> {
    void validar(Cliente cliente);
    List<Cliente> consultarPorEmpresa(long empresaId);
    List<Cliente> consultarActivos();
	Cliente activar(Cliente cliente);
	Cliente inactivar(Cliente cliente);
    String existe(Cliente cliente);
    Cliente obtenerPorIdentificacion(String identificacion);
    Cliente obtenerPorRazonSocial(String razonSocial);
    List<Cliente> buscar(Cliente cliente);
    Cliente validarIdentificacion(String identificacion);
    Cliente buscarClienteBase(Cliente cliente);
    boolean verificarPersonaNatural(String identificacion);
    boolean verificarCedula(String identificacion);
    boolean verificarSociedadesPublicas(String identificacion);
    boolean verificarSociedadesPrivadas(String identificacion);
    boolean verificarPlaca(String identificacion);
    boolean verificarPlacaMoto(String identificacion);
    boolean verificarPasaporte(String identificacion);
}
