package com.proyecto.sicecuador.servicios.impl.inventario;

import com.proyecto.sicecuador.controladoras.Constantes;
import com.proyecto.sicecuador.modelos.inventario.SaldoInicialInventario;
import com.proyecto.sicecuador.repositorios.interf.inventario.ISaldoInicialInventarioRepository;
import com.proyecto.sicecuador.servicios.interf.inventario.ISaldoInicialInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaldoInicialInventarioService implements ISaldoInicialInventarioService {
    @Autowired
    private ISaldoInicialInventarioRepository rep;
    @Override
    public SaldoInicialInventario crear(SaldoInicialInventario saldo_inicial_inventario) {
        return rep.save(saldo_inicial_inventario);
    }

    @Override
    public SaldoInicialInventario actualizar(SaldoInicialInventario saldo_inicial_inventario) {
        return rep.save(saldo_inicial_inventario);
    }

    @Override
    public SaldoInicialInventario eliminar(SaldoInicialInventario saldo_inicial_inventario) {
        rep.deleteById(saldo_inicial_inventario.getId());
        return saldo_inicial_inventario;
    }

    @Override
    public Optional<SaldoInicialInventario> obtener(SaldoInicialInventario saldo_inicial_inventario) {
        return rep.findById(saldo_inicial_inventario.getId());
    }

    @Override
    public List<SaldoInicialInventario> consultar() {
        return rep.findAll();
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        try {
            List<SaldoInicialInventario> saldos_iniciales_inventarios=new ArrayList<>();
            List<List<String>>info= Constantes.leer_importar(archivo_temporal,8);
            for (List<String> datos: info) {
                SaldoInicialInventario saldo_inicial_inventario = new SaldoInicialInventario(datos);
                saldos_iniciales_inventarios.add(saldo_inicial_inventario);
            }
            if(saldos_iniciales_inventarios.isEmpty()){
                return false;
            }
            rep.saveAll(saldos_iniciales_inventarios);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
