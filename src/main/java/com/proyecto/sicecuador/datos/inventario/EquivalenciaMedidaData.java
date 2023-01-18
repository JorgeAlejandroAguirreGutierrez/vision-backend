package com.proyecto.sicecuador.datos.inventario;

import com.proyecto.sicecuador.modelos.inventario.Medida;
import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.modelos.inventario.EquivalenciaMedida;
import com.proyecto.sicecuador.repositorios.inventario.IEquivalenciaMedidaRepository;
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
@Order(40)
@Profile({ "dev", "prod" })
public class EquivalenciaMedidaData implements ApplicationRunner {
	@Autowired
	private IEquivalenciaMedidaRepository rep;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Optional<EquivalenciaMedida> ant = rep.findById((long) 1);
		if (!ant.isPresent()) {
			List<EquivalenciaMedida> equivalenciasMedidas = new ArrayList<>();
			// LIBRA
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000001", new Medida(1), new Medida(1), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000002", new Medida(1), new Medida(2), 0.454, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000003", new Medida(1), new Medida(3), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000004", new Medida(1), new Medida(4), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000005", new Medida(1), new Medida(5), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000006", new Medida(1), new Medida(6), 16, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000007", new Medida(1), new Medida(7), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000008", new Medida(1), new Medida(8), 453.592, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000009", new Medida(1), new Medida(9), 0.031, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000010", new Medida(1), new Medida(10), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000011", new Medida(1), new Medida(11), 0.001, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000012", new Medida(1), new Medida(12), 0, Constantes.activo));
			// KILOGRAMO
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000001", new Medida(2), new Medida(1), 2.204, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000002", new Medida(2), new Medida(2), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000003", new Medida(2), new Medida(3), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000004", new Medida(2), new Medida(4), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000005", new Medida(2), new Medida(5), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000006", new Medida(2), new Medida(6), 35.273, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000007", new Medida(2), new Medida(7), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000008", new Medida(2), new Medida(8), 1000, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000009", new Medida(2), new Medida(9), 0.068, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000010", new Medida(2), new Medida(10), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000011", new Medida(2), new Medida(11), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000012", new Medida(2), new Medida(12), 1000000, Constantes.activo));
			// QUINTAL US
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000001", new Medida(3), new Medida(1), 220.462, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000002", new Medida(3), new Medida(2), 100, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000003", new Medida(3), new Medida(3), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000004", new Medida(3), new Medida(4), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000005", new Medida(3), new Medida(5), 15.747, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000006", new Medida(3), new Medida(6), 3527.4, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000007", new Medida(3), new Medida(7), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000008", new Medida(3), new Medida(8), 100000, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000009", new Medida(3), new Medida(9), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000010", new Medida(3), new Medida(10), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000011", new Medida(3), new Medida(11), 0.1, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000012", new Medida(3), new Medida(12), 0, Constantes.activo));
			// QUINTAL UK
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000001", new Medida(4), new Medida(1), 112, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000002", new Medida(4), new Medida(2), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000003", new Medida(4), new Medida(3), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000004", new Medida(4), new Medida(4), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000005", new Medida(4), new Medida(5), 8, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000006", new Medida(4), new Medida(6), 1792, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000007", new Medida(4), new Medida(7), 784000, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000008", new Medida(4), new Medida(8), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000009", new Medida(4), new Medida(9), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000010", new Medida(4), new Medida(10), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000011", new Medida(4), new Medida(11), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000012", new Medida(4), new Medida(12), 0, Constantes.activo));
			// STONE
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000001", new Medida(5), new Medida(1), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000002", new Medida(5), new Medida(2), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000003", new Medida(5), new Medida(3), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000004", new Medida(5), new Medida(4), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000005", new Medida(5), new Medida(5), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000006", new Medida(5), new Medida(6), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000007", new Medida(5), new Medida(7), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000008", new Medida(5), new Medida(8), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000009", new Medida(5), new Medida(9), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000010", new Medida(5), new Medida(10), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000011", new Medida(5), new Medida(11), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000012", new Medida(5), new Medida(12), 0, Constantes.activo));
			// ONZA
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000001", new Medida(6), new Medida(1), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000002", new Medida(6), new Medida(2), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000003", new Medida(6), new Medida(3), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000004", new Medida(6), new Medida(4), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000005", new Medida(6), new Medida(5), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000006", new Medida(6), new Medida(6), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000007", new Medida(6), new Medida(7), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000008", new Medida(6), new Medida(8), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000009", new Medida(6), new Medida(9), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000010", new Medida(6), new Medida(10), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000011", new Medida(6), new Medida(11), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000012", new Medida(6), new Medida(12), 0, Constantes.activo));
			// GRANO
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000001", new Medida(7), new Medida(1), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000002", new Medida(7), new Medida(2), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000003", new Medida(7), new Medida(3), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000004", new Medida(7), new Medida(4), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000005", new Medida(7), new Medida(5), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000006", new Medida(7), new Medida(6), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000007", new Medida(7), new Medida(7), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000008", new Medida(7), new Medida(8), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000009", new Medida(7), new Medida(9), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000010", new Medida(7), new Medida(10), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000011", new Medida(7), new Medida(11), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000012", new Medida(7), new Medida(12), 0, Constantes.activo));
			// GRAMO
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000001", new Medida(8), new Medida(1), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000002", new Medida(8), new Medida(2), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000003", new Medida(8), new Medida(3), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000004", new Medida(8), new Medida(4), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000005", new Medida(8), new Medida(5), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000006", new Medida(8), new Medida(6), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000007", new Medida(8), new Medida(7), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000008", new Medida(8), new Medida(8), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000009", new Medida(8), new Medida(9), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000010", new Medida(8), new Medida(10), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000011", new Medida(8), new Medida(11), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000012", new Medida(8), new Medida(12), 0, Constantes.activo));
			// SLUG
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000001", new Medida(9), new Medida(1), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000002", new Medida(9), new Medida(2), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000003", new Medida(9), new Medida(3), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000004", new Medida(9), new Medida(4), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000005", new Medida(9), new Medida(5), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000006", new Medida(9), new Medida(6), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000007", new Medida(9), new Medida(7), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000008", new Medida(9), new Medida(8), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000009", new Medida(9), new Medida(9), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000010", new Medida(9), new Medida(10), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000011", new Medida(9), new Medida(11), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000012", new Medida(9), new Medida(12), 0, Constantes.activo));
			// TONELADA
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000001", new Medida(10), new Medida(1), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000002", new Medida(10), new Medida(2), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000003", new Medida(10), new Medida(3), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000004", new Medida(10), new Medida(4), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000005", new Medida(10), new Medida(5), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000006", new Medida(10), new Medida(6), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000007", new Medida(10), new Medida(7), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000008", new Medida(10), new Medida(8), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000009", new Medida(10), new Medida(9), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000010", new Medida(10), new Medida(10), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000011", new Medida(10), new Medida(11), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000012", new Medida(10), new Medida(12), 0, Constantes.activo));
			// TONELADA LARGA
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000001", new Medida(11), new Medida(1), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000002", new Medida(11), new Medida(2), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000003", new Medida(11), new Medida(3), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000004", new Medida(11), new Medida(4), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000005", new Medida(11), new Medida(5), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000006", new Medida(11), new Medida(6), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000007", new Medida(11), new Medida(7), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000008", new Medida(11), new Medida(8), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000009", new Medida(11), new Medida(9), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000010", new Medida(11), new Medida(10), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000011", new Medida(11), new Medida(11), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000012", new Medida(11), new Medida(12), 0, Constantes.activo));
			// MILIGRAMO
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000001", new Medida(12), new Medida(1), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000002", new Medida(12), new Medida(2), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000003", new Medida(12), new Medida(3), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000004", new Medida(12), new Medida(4), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000005", new Medida(12), new Medida(5), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000006", new Medida(12), new Medida(6), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000007", new Medida(12), new Medida(7), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000008", new Medida(12), new Medida(8), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000009", new Medida(12), new Medida(9), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000010", new Medida(12), new Medida(10), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000011", new Medida(12), new Medida(11), 0, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000012", new Medida(12), new Medida(12), 0, Constantes.activo));
			// UNIDAD
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000001", new Medida(13), new Medida(13), 1, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000002", new Medida(13), new Medida(14), 0.0834, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000003", new Medida(13), new Medida(15), 0.002, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000004", new Medida(13), new Medida(16), 0.1667, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("TEM011909000005", new Medida(13), new Medida(17), 0.0834, Constantes.activo));

			rep.saveAll(equivalenciasMedidas);
		}
	}
}
