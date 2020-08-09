package com.proyecto.sicecuador.servicios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.servicios.interf.IGenericoService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IClienteService extends IGenericoService<Cliente> {

    Optional<Cliente> obtenerIdentificacion(Cliente cliente);
    Optional<Cliente> obtenerRazonSocial(Cliente cliente);
    List<Cliente> consultarIdentificacion(Cliente cliente);
    List<Cliente> consultarRazonSocial(Cliente cliente);
    Optional<Cliente> validarIdentificacion(Cliente cliente);
    boolean verificarPersonaNatural(String identificacion);
    boolean verificarCedula(String identificacion);
    boolean verificarSociedadesPublicas(String identificacion);
    boolean verificarSociedadesPrivadas(String identificacion);
    boolean verificarPlaca(String identificacion);
    boolean verificarPlacaMoto(String identificacion);
    boolean verificarPasaporte(String identificacion);
}
