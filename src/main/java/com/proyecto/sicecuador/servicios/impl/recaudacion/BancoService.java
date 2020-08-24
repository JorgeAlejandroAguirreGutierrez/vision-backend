package com.proyecto.sicecuador.servicios.impl.recaudacion;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.recaudacion.Amortizacion;
import com.proyecto.sicecuador.modelos.recaudacion.Banco;
import com.proyecto.sicecuador.repositorios.interf.recaudacion.IBancoRepository;
import com.proyecto.sicecuador.servicios.interf.recaudacion.IBancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BancoService implements IBancoService {
    @Autowired
    private IBancoRepository rep;
    @Override
    public Banco crear(Banco banco) {
        return rep.save(banco);
    }

    @Override
    public Banco actualizar(Banco banco) {
        return rep.save(banco);
    }

    @Override
    public Banco eliminar(Banco banco) {
        rep.deleteById(banco.getId());
        return banco;
    }

    @Override
    public Optional<Banco> obtener(Banco banco) {
        return rep.findById(banco.getId());
    }

    @Override
    public List<Banco> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<Banco> bancos=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal,1);
            for (List<String> datos: info) {
                Banco banco = new Banco(datos);
                bancos.add(banco);
            }
            rep.saveAll(bancos);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
