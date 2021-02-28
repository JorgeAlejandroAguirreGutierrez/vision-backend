package com.proyecto.sicecuador.repositorios.interf.cliente;

import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.repositorios.interf.IGenericoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends IGenericoRepository<Cliente> {
}
