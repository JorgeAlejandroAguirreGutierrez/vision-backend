package com.proyecto.sicecuador.datos.configuracion;

import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.repositorios.configuracion.IUbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(11)
@Profile({"dev","prod"})
public class UbicacionData implements ApplicationRunner {
    @Autowired
    private IUbicacionRepository rep;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Ubicacion> ant=rep.findById((long) 1);
        if (!ant.isPresent()) {
            List<Ubicacion> ubicaciones = new ArrayList<>();
            ubicaciones.add(new Ubicacion("U1", "010150", "AZUAY", "CUENCA", "CUENCA"));
            ubicaciones.add(new Ubicacion("U2", "010101", "AZUAY", "CUENCA", "BELLAVISTA"));
            ubicaciones.add(new Ubicacion("U3", "010102", "AZUAY", "CUENCA", "CAÑARIBAMBA"));
            ubicaciones.add(new Ubicacion("U4", "010103", "AZUAY", "CUENCA", "EL BATAN"));
            ubicaciones.add(new Ubicacion("U5", "010104", "AZUAY", "CUENCA", "EL SAGRARIO"));
            ubicaciones.add(new Ubicacion("U5", "010105", "AZUAY", "CUENCA", "EL VECINO"));
            ubicaciones.add(new Ubicacion("U6", "060101", "CHIMBORAZO", "RIOBAMBA", "LIZARZABURU"));
            ubicaciones.add(new Ubicacion("U7", "060103", "CHIMBORAZO", "RIOBAMBA", "VELASCO"));
            ubicaciones.add(new Ubicacion("U8", "060104", "CHIMBORAZO", "RIOBAMBA", "VELOZ"));
            ubicaciones.add(new Ubicacion("U9", "060250", "ALAUSI", "ALAUSI", "ALAUSI"));
            ubicaciones.add(new Ubicacion("U10", "060251", "ALAUSI", "ACHUPALLAS", "ACHUPALLAS"));
            ubicaciones.add(new Ubicacion("U11", "060252", "ALAUSI", "CUMANDÁ", "CUMANDÁ"));
            ubicaciones.add(new Ubicacion("U12", "060253", "ALAUSI", "GUASUNTOS", "GUASUNTOS"));
            ubicaciones.add(new Ubicacion("U13", "060301", "COLTA", "CAJABAMBA", "CAJABAMBA"));
            ubicaciones.add(new Ubicacion("U14", "060302", "COLTA", "SICALPA", "SICALPA"));
            ubicaciones.add(new Ubicacion("U15", "060303", "COLTA", "VILLA LA UNIÓN", "VILLA LA UNIÓN"));
            ubicaciones.add(new Ubicacion("U16", "060450", "CHAMBO", "CHAMBO", "CHAMBO"));
            ubicaciones.add(new Ubicacion("U17", "060550", "CHUNCHI", "CHUNCHI", "CHUNCHI"));
            ubicaciones.add(new Ubicacion("U18", "060551", "CHUNCHI", "CAPZOL", "CAPZOL"));
            ubicaciones.add(new Ubicacion("U19", "060552", "CHUNCHI", "COMPUD", "COMPUD"));
            ubicaciones.add(new Ubicacion("U20", "060553", "CHUNCHI", "GONZOL", "GONZOL"));
            rep.saveAll(ubicaciones);
        }
    }
}
