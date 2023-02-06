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
			// ARROBA
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000001", new Medida(1), new Medida(1), 1.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000002", new Medida(1), new Medida(3), 175000.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000003", new Medida(1), new Medida(4), 11.502, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000004", new Medida(1), new Medida(5), 25.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000005", new Medida(1), new Medida(7), 400.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000006", new Medida(1), new Medida(8), 0.22321429, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000007", new Medida(1), new Medida(9), 0.25, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000008", new Medida(1), new Medida(11), 1.78571429, Constantes.inactivo));
			// GRAMO
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000009", new Medida(2), new Medida(2), 1.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000010", new Medida(2), new Medida(4), 0.001, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000011", new Medida(2), new Medida(5), 0.00220462, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000012", new Medida(2), new Medida(6), 1000.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000013", new Medida(2), new Medida(7), 0.03527396, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000014", new Medida(2), new Medida(10), 0.00006852, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000015", new Medida(2), new Medida(12), 0.000001, Constantes.activo));
			// GRANO
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000016", new Medida(3), new Medida(1), 0.00000571, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000017", new Medida(3), new Medida(2), 0.0647989, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000018", new Medida(3), new Medida(3), 1.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000019", new Medida(3), new Medida(4), 0.0000648, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000020", new Medida(3), new Medida(5), 0.00014286, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000021", new Medida(3), new Medida(6), 64.7989, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000022", new Medida(3), new Medida(7), 0.00228571, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000023", new Medida(3), new Medida(8), 0.00000128, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000024", new Medida(3), new Medida(11), 0.0000102, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000025", new Medida(3), new Medida(12), 0.00000006, Constantes.inactivo));
			// KILOGRAMO
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000026", new Medida(4), new Medida(2), 1000.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000027", new Medida(4), new Medida(4), 1.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000028", new Medida(4), new Medida(5), 2.20462, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000029", new Medida(4), new Medida(6), 1000000.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000030", new Medida(4), new Medida(7), 35.27396195, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000031", new Medida(4), new Medida(10), 0.06852177, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000032", new Medida(4), new Medida(12), 0.001, Constantes.activo));
			// LIBRA
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000033", new Medida(5), new Medida(2), 453.59237, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000034", new Medida(5), new Medida(4), 0.45359237, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000035", new Medida(5), new Medida(5), 1.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000036", new Medida(5), new Medida(6), 453592.37, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000037", new Medida(5), new Medida(7), 16.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000038", new Medida(5), new Medida(10), 0.03108095, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000039", new Medida(5), new Medida(12), 0.00045359, Constantes.activo));
			// MILIGRAMO
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000040", new Medida(6), new Medida(2), 0.001, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000041", new Medida(6), new Medida(4), 0.000001, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000042", new Medida(6), new Medida(5), 0.0000022, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000043", new Medida(6), new Medida(6), 1.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000044", new Medida(6), new Medida(7), 0.00003527, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000045", new Medida(6), new Medida(10), 0.00000007, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000046", new Medida(6), new Medida(12), 0.000000001, Constantes.activo));
			// ONZA
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000047", new Medida(7), new Medida(2), 28.34952313, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000048", new Medida(7), new Medida(4), 0.02834952, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000049", new Medida(7), new Medida(5), 0.0625, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000050", new Medida(7), new Medida(6), 28349.523125, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000051", new Medida(7), new Medida(7), 1.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000052", new Medida(7), new Medida(10), 0.00194256, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000053", new Medida(7), new Medida(12), 0.00002835, Constantes.activo));
			// QUINTAL UK
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000054", new Medida(8), new Medida(3), 784000.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000055", new Medida(8), new Medida(5), 112.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000056", new Medida(8), new Medida(7), 1792.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000057", new Medida(8), new Medida(11), 8.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000058", new Medida(8), new Medida(13), 0.05, Constantes.inactivo));
			// QUIINTAL
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000059", new Medida(9), new Medida(1), 4.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000060", new Medida(9), new Medida(2), 100000.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000061", new Medida(9), new Medida(4), 100.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000062", new Medida(9), new Medida(5), 220.462, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000063", new Medida(9), new Medida(6), 100000000.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000064", new Medida(9), new Medida(7), 3527.40, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000065", new Medida(9), new Medida(9), 1.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000066", new Medida(9), new Medida(11), 15.7473, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000067", new Medida(9), new Medida(12), 0.10, Constantes.activo));
			// SLUG
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000068", new Medida(10), new Medida(2), 14593.90293721, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000069", new Medida(10), new Medida(4), 14.59390294, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000070", new Medida(10), new Medida(5), 32.17404856, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000071", new Medida(10), new Medida(6), 14593902.9372064, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000072", new Medida(10), new Medida(7), 514.7847769, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000073", new Medida(10), new Medida(10), 1.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000074", new Medida(10), new Medida(12), 0.0145939, Constantes.inactivo));
			// STONE
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000075", new Medida(11), new Medida(2), 6350.29, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000076", new Medida(11), new Medida(4), 6.35029, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000077", new Medida(11), new Medida(5), 14.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000078", new Medida(11), new Medida(6), 6350290.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000079", new Medida(11), new Medida(7), 224.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000080", new Medida(11), new Medida(11), 1.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000081", new Medida(11), new Medida(12), 0.00635029, Constantes.inactivo));
			// TONELADA
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000082", new Medida(12), new Medida(2), 1000000.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000083", new Medida(12), new Medida(4), 1000.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000084", new Medida(12), new Medida(5), 2204.62262185, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000085", new Medida(12), new Medida(6), 1000000000.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000086", new Medida(12), new Medida(7), 35274.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000087", new Medida(12), new Medida(10), 68.52176586, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000088", new Medida(12), new Medida(12), 1.00, Constantes.activo));
			// TONELADA UK
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000089", new Medida(13), new Medida(2), 1016050.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000090", new Medida(13), new Medida(4), 1016.05, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000091", new Medida(13), new Medida(5), 2240.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000092", new Medida(13), new Medida(6), 1016050000.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000093", new Medida(13), new Medida(7), 35840.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000094", new Medida(13), new Medida(11), 160.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000095", new Medida(13), new Medida(12), 1.01605, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000096", new Medida(13), new Medida(13), 1.00, Constantes.inactivo));
			// CENTÍMETRO CÚBICO
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000097", new Medida(14), new Medida(14), 1.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000098", new Medida(14), new Medida(15), 0.00105669, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000099", new Medida(14), new Medida(16), 0.067628, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000100", new Medida(14), new Medida(17), 0.202884, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000101", new Medida(14), new Medida(18), 0.00026417, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000102", new Medida(14), new Medida(19), 0.001, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000103", new Medida(14), new Medida(20), 0.000001, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000104", new Medida(14), new Medida(22), 0.033814, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000105", new Medida(14), new Medida(23), 0.00003532, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000106", new Medida(14), new Medida(24), 0.00175975, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000107", new Medida(14), new Medida(25), 0.00211338, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000108", new Medida(14), new Medida(26), 0.0610237, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000109", new Medida(14), new Medida(21), 0.00416667, Constantes.inactivo));
			// CUARTO DE GALÓN
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000110", new Medida(15), new Medida(15), 1.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000111", new Medida(15), new Medida(16), 64.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000112", new Medida(15), new Medida(17), 192.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000113", new Medida(15), new Medida(18), 0.25, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000114", new Medida(15), new Medida(19), 0.94635295, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000115", new Medida(15), new Medida(22), 32.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000116", new Medida(15), new Medida(24), 1.66534837, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000117", new Medida(15), new Medida(25), 2.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000118", new Medida(15), new Medida(21), 4.00, Constantes.activo));
			// CUCHARADA
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000119", new Medida(16), new Medida(15), 0.015625, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000120", new Medida(16), new Medida(16), 1.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000121", new Medida(16), new Medida(17), 3.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000122", new Medida(16), new Medida(18), 0.00390625, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000123", new Medida(16), new Medida(19), 0.01478676, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000124", new Medida(16), new Medida(22), 0.50, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000125", new Medida(16), new Medida(24), 0.02602107, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000126", new Medida(16), new Medida(25), 0.03125, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000127", new Medida(16), new Medida(21), 0.0625, Constantes.inactivo));
			// CUCHARADITA
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000128", new Medida(17), new Medida(15), 0.00520833, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000129", new Medida(17), new Medida(16), 0.33333333, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000130", new Medida(17), new Medida(17), 1.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000131", new Medida(17), new Medida(18), 0.00130208, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000132", new Medida(17), new Medida(19), 0.00492892, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000133", new Medida(17), new Medida(22), 0.16666667, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000134", new Medida(17), new Medida(24), 0.00867369, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000135", new Medida(17), new Medida(25), 0.01041667, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000136", new Medida(17), new Medida(21), 0.02083333, Constantes.inactivo));
			// GALÓN
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000137", new Medida(18), new Medida(15), 4.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000138", new Medida(18), new Medida(16), 256.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000139", new Medida(18), new Medida(17), 768.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000140", new Medida(18), new Medida(18), 1.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000141", new Medida(18), new Medida(19), 3.78541178, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000142", new Medida(18), new Medida(20), 0.00378541, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000143", new Medida(18), new Medida(22), 128.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000144", new Medida(18), new Medida(24), 6.66139348, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000145", new Medida(18), new Medida(25), 8.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000146", new Medida(18), new Medida(21), 16.00, Constantes.activo));
			// LITRO
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000147", new Medida(19), new Medida(14), 0.10, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000148", new Medida(19), new Medida(15), 1.05668821, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000149", new Medida(19), new Medida(16), 67.6280454, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000150", new Medida(19), new Medida(17), 202.88413621, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000151", new Medida(19), new Medida(18), 0.26417205, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000152", new Medida(19), new Medida(19), 1.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000153", new Medida(19), new Medida(20), 0.001, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000154", new Medida(19), new Medida(22), 33.8140227, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000155", new Medida(19), new Medida(24), 1.75975399, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000156", new Medida(19), new Medida(25), 2.11337642, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000157", new Medida(19), new Medida(21), 4.22675284, Constantes.activo));
			// METRO CÚBICO
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000158", new Medida(20), new Medida(14), 100.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000159", new Medida(20), new Medida(15), 1056.69, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000160", new Medida(20), new Medida(16), 67628.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000161", new Medida(20), new Medida(17), 202884.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000162", new Medida(20), new Medida(18), 264.172, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000163", new Medida(20), new Medida(19), 1000.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000164", new Medida(20), new Medida(20), 1.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000165", new Medida(20), new Medida(22), 33814.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000166", new Medida(20), new Medida(23), 35.3147, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000167", new Medida(20), new Medida(24), 1759.75, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000168", new Medida(20), new Medida(25), 2113.38, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000169", new Medida(20), new Medida(26), 61023.70, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM202301000170", new Medida(20), new Medida(21), 4166.67, Constantes.activo));
			// TAZA
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000171", new Medida(21), new Medida(15), 0.25, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000172", new Medida(21), new Medida(16), 16.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000173", new Medida(21), new Medida(17), 48.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000174", new Medida(21), new Medida(18), 0.0625, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000175", new Medida(21), new Medida(19), 0.23658824, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000176", new Medida(21), new Medida(22), 8.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000177", new Medida(21), new Medida(24), 0.41633709, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000178", new Medida(21), new Medida(25), 0.50, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000179", new Medida(21), new Medida(21), 1.00, Constantes.activo));
			// ONZA LÍQUIDA
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000180", new Medida(22), new Medida(15), 0.03125, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000181", new Medida(22), new Medida(16), 2.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000182", new Medida(22), new Medida(17), 6.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000183", new Medida(22), new Medida(18), 0.0078125, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000184", new Medida(22), new Medida(19), 0.02957353, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000185", new Medida(22), new Medida(22), 1.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000186", new Medida(22), new Medida(24), 0.05204214, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000187", new Medida(22), new Medida(25), 0.0625, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000188", new Medida(22), new Medida(21), 0.125, Constantes.inactivo));
			// PIE CÚBICO
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000189", new Medida(23), new Medida(14), 28316.80, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000190", new Medida(23), new Medida(15), 29.9221, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000191", new Medida(23), new Medida(16), 1915.01, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000192", new Medida(23), new Medida(17), 5745.04, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000193", new Medida(23), new Medida(18), 7.48052, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000194", new Medida(23), new Medida(19), 28.3168, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000195", new Medida(23), new Medida(20), 0.0283168, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000196", new Medida(23), new Medida(22), 957.506, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000197", new Medida(23), new Medida(23), 1.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000198", new Medida(23), new Medida(24), 49.8307, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000199", new Medida(23), new Medida(25), 59.8442, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000200", new Medida(23), new Medida(26), 1728.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000201", new Medida(23), new Medida(21), 117.987, Constantes.inactivo));
			// PINTA UK
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000202", new Medida(24), new Medida(15), 0.60047496, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000203", new Medida(24), new Medida(16), 38.43039762, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000204", new Medida(24), new Medida(17), 115.29119285, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000205", new Medida(24), new Medida(18), 0.15011874, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000206", new Medida(24), new Medida(19), 0.56826125, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000207", new Medida(24), new Medida(22), 19.21519881, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000208", new Medida(24), new Medida(24), 1.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000209", new Medida(24), new Medida(25), 1.20094993, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000210", new Medida(24), new Medida(21), 2.40189985, Constantes.inactivo));
			// PINTA USA
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000211", new Medida(25), new Medida(15), 0.50, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000212", new Medida(25), new Medida(16), 32.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000213", new Medida(25), new Medida(17), 96.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000214", new Medida(25), new Medida(18), 0.125, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000215", new Medida(25), new Medida(19), 0.47317647, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000216", new Medida(25), new Medida(22), 16.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000217", new Medida(25), new Medida(24), 0.83267418, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000218", new Medida(25), new Medida(25), 1.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000219", new Medida(25), new Medida(21), 2.00, Constantes.inactivo));
			// PULGADA CÚBICA
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000220", new Medida(26), new Medida(14), 0.0016387, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000221", new Medida(26), new Medida(15), 0.017316, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000222", new Medida(26), new Medida(16), 1.10823, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000223", new Medida(26), new Medida(17), 3.32468, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000224", new Medida(26), new Medida(18), 0.004329, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000225", new Medida(26), new Medida(19), 0.0163871, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000226", new Medida(26), new Medida(20), 0.00001639, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000227", new Medida(26), new Medida(22), 0.554113, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000228", new Medida(26), new Medida(24), 0.0288372, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000229", new Medida(26), new Medida(25), 0.034632, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000230", new Medida(26), new Medida(26), 1.00, Constantes.inactivo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000231", new Medida(26), new Medida(21), 0.0682794, Constantes.inactivo));
			// UNIDAD
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000232", new Medida(27), new Medida(27), 1.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000233", new Medida(27), new Medida(28), 2.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000234", new Medida(27), new Medida(29), 4.00, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000235", new Medida(27), new Medida(30), 0.10, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000236", new Medida(27), new Medida(31), 0.08333334, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000237", new Medida(27), new Medida(32), 0.05, Constantes.activo));
			equivalenciasMedidas.add(new EquivalenciaMedida("EQM2023010000238", new Medida(27), new Medida(33), 0.08333334, Constantes.activo));

			rep.saveAll(equivalenciasMedidas);
		}
	}
}
