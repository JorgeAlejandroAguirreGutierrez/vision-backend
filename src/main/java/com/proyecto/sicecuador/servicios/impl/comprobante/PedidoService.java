package com.proyecto.sicecuador.servicios.impl.comprobante;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.comprobante.Pedido;
import com.proyecto.sicecuador.repositorios.comprobante.IPedidoRepository;
import com.proyecto.sicecuador.servicios.interf.comprobante.IPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@Service
public class PedidoService implements IPedidoService {
    @Autowired
    private IPedidoRepository rep;
    
    @Override
    public Pedido crear(Pedido pedido) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_pedido);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	pedido.setCodigo(codigo.get());
    	return rep.save(pedido);
    }

    @Override
    public Pedido actualizar(Pedido pedido) {
        return rep.save(pedido);
    }

    @Override
    public Pedido activar(Pedido pedido) {
        pedido.setEstado(Constantes.activo);
        return rep.save(pedido);
    }

    @Override
    public Pedido inactivar(Pedido pedido) {
        pedido.setEstado(Constantes.inactivo);
        return rep.save(pedido);
    }

    @Override
    public Pedido obtener(long id) {
        Optional<Pedido> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.pedido);
    }

    @Override
    public List<Pedido> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<Pedido> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<Pedido> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile file) {
    }
}
