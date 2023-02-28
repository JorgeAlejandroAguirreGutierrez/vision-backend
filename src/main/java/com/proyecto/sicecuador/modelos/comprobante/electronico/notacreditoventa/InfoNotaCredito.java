package com.proyecto.sicecuador.modelos.comprobante.electronico.notacreditoventa;

import com.proyecto.sicecuador.modelos.comprobante.electronico.notacreditoventa.TotalConImpuestos;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "infoNotaCredito")
@XmlType(propOrder={"fechaEmision", "tipoIdentificacionComprador", "razonSocialComprador", "identificacionComprador", "obligadoContabilidad", "codDocModificado", "numDocModificado", "fechaEmisionDocSustento","totalSinImpuestos", "valorModificacion", "moneda", "totalConImpuestos", "motivo"})
@Data
public class InfoNotaCredito {
	private String fechaEmision;
	private String tipoIdentificacionComprador;
	private String razonSocialComprador;
	private String identificacionComprador;
	private String obligadoContabilidad;
	private String codDocModificado;
	private String numDocModificado;
	private String fechaEmisionDocSustento;
	private double totalSinImpuestos;
	private double valorModificacion;
	private String moneda;
	private TotalConImpuestos totalConImpuestos;
	private String motivo;
}
