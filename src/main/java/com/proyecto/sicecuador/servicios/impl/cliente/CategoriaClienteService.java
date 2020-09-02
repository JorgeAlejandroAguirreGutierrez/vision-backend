package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.cliente.CategoriaCliente;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.ICategoriaClienteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.ICategoriaClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CategoriaClienteService implements ICategoriaClienteService {
    @Autowired
    private ICategoriaClienteRepository rep;
    @Override
    public CategoriaCliente crear(CategoriaCliente categoria_cliente) {
        return rep.save(categoria_cliente);
    }

    @Override
    public CategoriaCliente actualizar(CategoriaCliente categoria_cliente) {
        return rep.save(categoria_cliente);
    }

    @Override
    public CategoriaCliente eliminar(CategoriaCliente categoria_cliente) {
        rep.deleteById(categoria_cliente.getId());
        return categoria_cliente;
    }

    @Override
    public Optional<CategoriaCliente> obtener(CategoriaCliente categoria_cliente) {
        return rep.findById(categoria_cliente.getId());
    }

    @Override
    public List<CategoriaCliente> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<CategoriaCliente> categorias_clientes=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal,1);
            for (List<String> datos: info) {
                CategoriaCliente categoria_cliente = new CategoriaCliente(datos);
                categorias_clientes.add(categoria_cliente);
            }
            if(categorias_clientes.isEmpty()){
                return false;
            }
            rep.saveAll(categorias_clientes);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
