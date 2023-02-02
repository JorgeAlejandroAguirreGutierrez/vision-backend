package com.proyecto.sicecuador.servicios.impl.entrega;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.entrega.VehiculoTransporte;
import com.proyecto.sicecuador.repositorios.entrega.IVehiculoTransporteRepository;
import com.proyecto.sicecuador.servicios.interf.entrega.IVehiculoTransporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class VehiculoTransporteService implements IVehiculoTransporteService {
    @Autowired
    private IVehiculoTransporteRepository rep;
    
    @Override
    public VehiculoTransporte crear(VehiculoTransporte vehiculoTransporte) {
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_vehiculo_transporte);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	vehiculoTransporte.setCodigo(codigo.get());
    	vehiculoTransporte.setEstado(Constantes.activo);
    	return rep.save(vehiculoTransporte);
    }

    @Override
    public VehiculoTransporte actualizar(VehiculoTransporte vehiculoTransporte) {
        return rep.save(vehiculoTransporte);
    }

    @Override
    public VehiculoTransporte activar(VehiculoTransporte vehiculoTransporte) {
        vehiculoTransporte.setEstado(Constantes.activo);
        return rep.save(vehiculoTransporte);
    }

    @Override
    public VehiculoTransporte inactivar(VehiculoTransporte vehiculoTransporte) {
        vehiculoTransporte.setEstado(Constantes.inactivo);
        return rep.save(vehiculoTransporte);
    }

    @Override
    public VehiculoTransporte obtener(long id) {
        Optional<VehiculoTransporte> res= rep.findById(id);
        if(res.isPresent()) {
        	return res.get();
        }
        throw new EntidadNoExistenteException(Constantes.vehiculo_transporte);   
    }

    @Override
    public List<VehiculoTransporte> consultar() {
        return rep.findAll();
    }
    
    @Override
    public List<VehiculoTransporte> consultarActivos(){
    	return rep.consultarPorEstado(Constantes.activo);
    }

    @Override
    public Page<VehiculoTransporte> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public void importar(MultipartFile archivo_temporal) {
        try {
            List<VehiculoTransporte> vehiculos_transportes=new ArrayList<>();
            List<List<String>>info= Util.leerImportar(archivo_temporal,2);
            for (List<String> datos: info) {
                VehiculoTransporte vehculo_transporte = new VehiculoTransporte(datos);
                vehiculos_transportes.add(vehculo_transporte);
            }
            rep.saveAll(vehiculos_transportes);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
