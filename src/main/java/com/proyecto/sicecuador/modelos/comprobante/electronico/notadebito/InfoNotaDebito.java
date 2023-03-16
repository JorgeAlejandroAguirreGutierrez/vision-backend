package com.proyecto.sicecuador.modelos.comprobante.electronico.notadebito;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "infoNotaCredito")
@XmlType(propOrder={"fechaEmision", "dirEstablecimiento", "tipoIdentificacionComprador", "razonSocialComprador", "identificacionComprador", "obligadoContabilidad", "codDocModificado", "numDocModificado", "fechaEmisionDocSustento","totalSinImpuestos", "impuestos", "valorTotal", "pagos"})
@Data
public class InfoNotaDebito {
	private String fechaEmision;
	private String dirEstablecimiento;
	private String tipoIdentificacionComprador;
	private String razonSocialComprador;
	private String identificacionComprador;
	private String obligadoContabilidad;
	private String codDocModificado;
	private String numDocModificado;
	private String fechaEmisionDocSustento;
	private double totalSinImpuestos;
	private Impuestos impuestos;
	private double valorTotal;
	private Pagos pagos;
}
