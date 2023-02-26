package com.proyecto.sicecuador;

public class Constantes {
	
	public static final String identificacion_consumidor_final="9999999999999";
	public static final String tipo_contribuyente_natural="NATURAL";
	public static final String tipo_contribuyente_juridica="JURIDICA";
	public static final String tipo_contribuyente_publica="PUBLICA";
	
	public static final String cedula_abreviatura="C";
	public static final String consumidor_final_abreviatura="CF";
	public static final String ruc_abreviatura="R";
	public static final String placa_abreviatura="PL";
	public static final String pasaporte_abreviatura="E";
	public static final String tipo_producto_bien="BIEN";
	public static final String recaudado="RECAUDADO";
	public static final String norecaudado="NO RECAUDADO";
	
	public static final String pendiente="PENDIENTE";
	public static final String entregado="ENTREGADO";
	public static final String sinGuia="SIN GUIA";
    public static final String factura_compra = "FACTURA DE COMPRA";
    public static final String factura_compra_linea = "FACTURA DE COMPRA LINEA";
    public static final String nota_credito_compra = "NOTA CREDITO DE COMPRA";
    public static final String nota_credito_compra_linea = "NOTA CREDITO DE COMPRA LINEA";
    public static final String factura_venta = "FACTURA DE VENTA";
    public static final String operacion_compra = "COMPRA";
    public static final String operacion_venta = "VENTA";
    public static final String operacion_devolucion = "DEVOLUCION";
    public static final String operacion_descuento = "DESCUENTO";
    public static final String operacion_conjunta = "CONJUNTA";
	
	public static final String activo="ACTIVO";
	public static final String inactivo="INACTIVO";
	
    public static final String mensaje_consultar_exitoso="Exito en la consulta";
    public static final String mensaje_obtener_exitoso="Exito en la consulta";
    public static final String mensaje_crear_exitoso="Exito al procesar";
    public static final String mensaje_actualizar_exitoso="Exito al actualizar";
    public static final String mensaje_eliminar_exitoso="Exito al eliminar";
    public static final String mensaje_crear_factura_electronica_exitosa="Exito factura electronica con clave de acceso";
    public static final String mensaje_importacion_exitoso="Exito al importar";
    public static final String mensaje_exportacion_exitoso="Exito al exportar";
    public static final String mensaje_calcular_exitoso="Exito al calcular";
    public static final String mensaje_activar_exitoso="Exito al activar";
    public static final String mensaje_inactivar_exitoso="Exito al inactivar";

    public static final String mensaje_consulta_fallido="Error en la consulta los objetos";
    public static final String mensaje_obtener_fallido="Error al obtener un objeto";
    public static final String mensaje_crear_fallido="Error al crear un objeto";
    public static final String mensaje_actualizar_fallido="Error al actualizar un objeto";
    public static final String mensaje_eliminar_fallido="Exito al eliminar un objeto";
    public static final String mensaje_importacion_fallido="Error al importar";
    public static final String mensaje_exportacion_fallido="Error al exportar";
    public static final String mensaje_activar_fallido="Error al activar";
    public static final String mensaje_inactivar_fallido="Error al inactivar";

    public static final String mensaje_validacion_not_blank=" no debe estar en blanco";
    public static final String mensaje_validacion_not_null=" se debe registrar";

    public static final String tabla_amortizacion_alemana="ALEMANA";
    public static final String tabla_amortizacion_francesa="FRANCESA";
    public static final String periodo_mensual="MENSUAL";
    public static final String periodo_trimestral="TRIMESTRAL";
    
    public static final String vacio="";
    public static final String espacio=" ";
    public static final String si = "SI";
    public static final String no = "NO";
    
    public static String error_registro_existente="REGISTRO EXISTENTE";
    public static String error_registro_no_existente="REGISTRO NO EXISTENTE";
    
    public static String error_codigo_generico="4000";
    public static String error_codigo_modelo_existente="4001";
    public static String error_codigo_modelo_no_existente="4002";
    public static String error_codigo_codigo_no_existente="4003";
    public static String error_codigo_sesion_invalida="4004";
    public static String error_codigo_datos_invalidos="4005";
    public static String error_codigo_identificacion_invalida="4006";
    public static String error_codigo_secuencia_no_existente="4007";
    public static String error_codigo_cliente_no_existente="4008";
    public static String error_codigo_parametro_invalido="4009";

    public static String error_codigo_estado_invalido="4010";
    public static String error_codigo_dato_invalido="4011";
    public static String error_codigo_factura_electronica_invalida="4012";
    
    public static String error_generico="ERROR INTERNO DE SERVIDOR";
    public static String error_entidad_existente="ERROR ENTIDAD YA EXISTE";
    public static String error_entidad_no_existente="ERROR ENTIDAD NO EXISTENTE";
    public static String error_codigo_no_existente="ERROR CODIGO INTERNO NO EXISTENTE";
    public static String error_sesion_invalida="ERROR SESION INVALIDA";
    public static String error_datos_invalidos="ERROR DATOS INVALIDOS";
    public static String error_identificacion_invalida="ERROR IDENTIFICACION INVALIDA";
    public static String error_secuencia_no_existente="ERROR SECUENCIA NO EXISTENTE";
    public static String error_cliente_no_existente="ERROR CLIENTE NO EXISTENTE";
    public static String error_parametro_invalido="ERROR PARAMETRO INVALIDO";
    public static String error_estado_invalido="ERROR ESTADO INVALIDO";
    public static String error_dato_invalido="ERROR DATO INVALIDO";
    public static String error_factura_electronica_invalida="ERROR FACTURA ELECTRONICA INVALIDA CON ESTADO";
    
    //CODIGO PARA CREAR
    public static final String tipo="CREAR";
    
    //TABLAS
    //CLIENTE
    public static final String tabla_modelo="modelo";
    public static final String tabla_auxiliar="auxiliar";
    public static final String tabla_calificacion_cliente="calificacion_cliente";
    public static final String tabla_celular="celular";
    public static final String tabla_celular_dependiente="celular_dependiente";
    public static final String tabla_cliente="cliente";
    public static final String tabla_correo="correo";
    public static final String tabla_correo_dependiente="correo_dependiente";
    public static final String tabla_direccion="direccion";
    public static final String tabla_estado_civil="estado_civil";
    public static final String tabla_financiamiento="financiamiento";
    public static final String tabla_forma_pago="forma_pago";
    public static final String tabla_genero="genero";
    public static final String tabla_grupo_cliente="grupo_cliente";
    public static final String tabla_origen_ingreso="origen_ingreso";
    public static final String tabla_plazo_credito="plazo_credito";
    public static final String tabla_retencion_cliente="retencion_cliente";
    public static final String tabla_telefono="telefono";
    public static final String tabla_telefono_dependiente="telefono_dependiente";
    public static final String tabla_tipo_contribuyente="tipo_contribuyente";
    public static final String tabla_tipo_pago="tipo_pago";
    
    //COMPRA
    public static final String tabla_factura_compra="factura_compra";
    public static final String tabla_factura_compra_linea="factura_compra_linea";
    public static final String tabla_nota_credito_compra="nota_credito_compra";
    public static final String tabla_noa_credito_compra_linea="nota_credito_compra_linea";
    public static final String tabla_retencion_compra="retencion_compra";
    public static final String tabla_grupo_proveedor="grupo_proveedor";
    
    //COMPROBANTE
    public static final String tabla_factura_detalle="detalle_factura";
    public static final String tabla_egreso="egreso";
    public static final String tabla_factura="factura";
    public static final String tabla_pedido="pedido";
    public static final String tabla_proforma="proforma";
    public static final String tabla_tipo_comprobante="tipo_comprobante";
    
    //CONFIGURACION
    public static final String tabla_empresa="empresa";
    public static final String tabla_parametro="parametro";
    public static final String tabla_tipo_retencion="tipo_retencion";
    public static final String tabla_ubicacion="ubicacion";
    public static final String tabla_tipo_identificacion="tipo_identificacion";
    
    //ENTREGA
    public static final String tabla_entrega="entrega";
    public static final String tabla_transportista="transportista";
    public static final String tabla_vehiculo_transporte="vehiculo_transporte";
    
    //INVENTARIO
    public static final String tabla_bodega="bodega";
    public static final String tabla_caracteristica="caracteristica";
    public static final String tabla_categoria_producto="categoria_producto";
    public static final String tabla_grupo_producto="grupo_producto";
    public static final String tabla_impuesto="impuesto";
    public static final String tabla_kardex="kardex";
    public static final String tabla_linea_producto="linea_producto";
    public static final String tabla_medida="medida";
    public static final String tabla_medida_precio="medida_precio";
    public static final String tabla_precio="precio";
    public static final String tabla_presentacion_producto="presentacion_producto";
    public static final String tabla_producto="producto";
    public static final String tabla_proveedor="proveedor";
    public static final String tabla_segmento="segmento";
    public static final String tabla_sub_grupo_producto="sub_grupo_producto";
    public static final String tabla_sub_linea_producto="sub_linea_producto";
    public static final String tabla_equivalencia_medida="tabla_equivalencia_medida";
    public static final String tabla_tipo_gasto="tipo_gasto";
    
    //RECAUDACION
    public static final String tabla_amortizacion="amortizacion";
    public static final String tabla_banco="banco";
    public static final String tabla_cheque="cheque";
    public static final String tabla_compensacion="compensacion";
    public static final String tabla_credito="credito";
    public static final String tabla_cuenta_propia="cuenta_propia";
    public static final String tabla_deposito="deposito";
    public static final String tabla_franquicia_tarjeta="franquicia_tarjeta";
    public static final String tabla_operador_tarjeta="operador_tarjeta";
    public static final String tabla_rango_crediticio="rango_crediticio";
    public static final String tabla_recaudacion="recaudacion";
    public static final String tabla_retencion_venta="retencion_venta";
    public static final String tabla_retencion_venta_detalle="retencion_Venta_detalle";
    public static final String tabla_tarjeta_credito="tarjeta_credito";
    public static final String tabla_tarjeta_debito="tarjeta_debito";
    public static final String tabla_tranferencia="transferencia";
    
    //USUARIO
    public static final String tabla_celular_establecimiento="celular_establecimiento";
    public static final String tabla_correo_establecimiento="correo_establecimiento";
    public static final String tabla_establecimiento="establecimiento";
    public static final String tabla_estacion="estacion";
    public static final String tabla_estacion_usuario="estacion_usuario";
    public static final String tabla_perfil="perfil";
    public static final String tabla_permiso="permiso";
    public static final String tabla_sesion="sesion";
    public static final String tabla_telefono_establecimiento="telefono_establecimiento";
    public static final String tabla_usuario="usuario";
	
    //CONTABILIDAD
    public static final String tabla_afectacion_contable="afectacion_contable";
    public static final String tabla_movimiento_contable="movimiento_contable";
    public static final String tabla_cuenta_contable="cuenta_contable";
    
    //NOMBRES
    //CLIENTE
    public static final String modelo="MODELO";
    public static final String dependiente="DEPENDIENTE";
    public static final String calificacion_cliente="CALIFICACION CLIENTE";
    public static final String celular="CELULAR";
    public static final String celular_dependiente="CELULAR DEPENDIENTE";
    public static final String cliente="CLIENTE";
    public static final String tipo_identificacion="TIPO IDENTIFICACION";
    public static final String correo="CORREO";
    public static final String correo_dependiente="CORREO DEPENDIENTE";
    public static final String direccion="DIRECCION";
    public static final String estado_civil="ESTADO CIVIL";
    public static final String financiamiento="FINANCIAMIENTO";
    public static final String forma_pago="FORMA PAGO";
    public static final String genero="GENERO";
    public static final String grupo_cliente="GRUPO CLIENTE";
    public static final String origen_ingreso="ORIGEN INGRESO";
    public static final String plazo_credito="PLAZO CREDITO";
    public static final String retencion_cliente="RETENCION CLIENTE";
    public static final String telefono="TELEFONO";
    public static final String telefono_dependiente="TELEFONO DEPENDIENTE";
    public static final String tipo_contribuyente="TIPO CONTRIBUYENTE";
    public static final String tipo_pago="TIPO PAGO";
    
    //COMPRA
    public static final String retencion_compra="RETENCION COMPRA";
    public static final String grupo_proveedor="GRUPO PROVEEDOR";
    public static final String fantasma="EMPRESA FANTASMA";

    //COMPROBANTE
    public static final String factura_detalle="DETALLE FACTURA";
    public static final String egreso="EGRESO";
    public static final String factura="FACTURA";
    public static final String factura_electronica="FACTURA ELECTRONICA";
    public static final String pedido="PEDIDO";
    public static final String proforma="PROFORMA";
    public static final String tipo_comprobante="TIPO COMPROBANTE";
    
    //CONFIGURACION
    public static final String empresa="EMPRESA";
    public static final String parametro="PARAMETRO";
    public static final String tipo_retencion="TIPO RETENCION";
    public static final String ubicacion="UBICACION";
    
    //ENTREGA
    public static final String entrega="ENTREGA";
    public static final String transportista="TRANSPORTISTA";
    public static final String vehiculo_transporte="VEHICULO TRANSPORTE";
    public static final String cliente_direccion = "CLIENTE DIRECCION";
    public static final String nueva_direccion = "NUEVA DIRECCION";
    public static final String sin_guia = "SIN GUIA";
    
    //INVENTARIO
    public static final String bodega="BODEGA";
    public static final String caracteristica="CARACTERISTICA";
    public static final String categoria_producto="CATEGORIA PRODUCTO";
    public static final String grupo_producto="GRUPO PRODUCTO";
    public static final String impuesto="IMPUESTO";
    public static final String kardex="KARDEX";
    public static final String linea_producto="LINEA PRODUCTO";
    public static final String medida="MEDIDA";
    public static final String medida_precio="MEDIDA PRECIO";
    public static final String precio="PRECIO";
    public static final String precio_venta_publico_manual="PRECIO VENTA AL PUBLICO MANUAL";
    public static final String presentacion_producto="PRESENTACION PRODUCTO";
    public static final String producto="PRODUCTO";
    public static final String proveedor="PROVEEDOR";
    public static final String segmento="SEGMENTO";
    public static final String sub_grupo_producto="SUB GRUPO PRODUCTO";
    public static final String sub_linea_producto="SUB LINEA PRODUCTO";
    public static final String equivalencia_medida="TABLA EQUIVALENCIA MEDIDA";
    public static final String tipo_gasto="TIPO GASTO";
    public static final String producto_proveedor="PRODUCTO Y PROVEEDOR";
    public static final String producto_bodega="PRODUCTO Y BODEGA";
    
    //RECAUDACION
    public static final String amortizacion="AMORTIZACION";
    public static final String banco="BANCO";
    public static final String cheque="CHEQUE";
    public static final String compensacion="COMPENSACION";
    public static final String credito="CREDITO";
    public static final String cuenta_propia="CUENTA PROPIA";
    public static final String deposito="DEPOSITO";
    public static final String franquicia_tarjeta="FRANQUICIA TARJETA";
    public static final String operador_tarjeta="OPERADOR TARJETA";
    public static final String rango_crediticio="RANGO CREDITICIO";
    public static final String recaudacion="RECAUDACION";
    public static final String retencion_venta="RETENCION VENTA";
    public static final String retencion_venta_detalle="RETENCION VENTA DETALLE";
    public static final String tarjeta_credito="TARJETA CREDITO";
    public static final String tarjeta_debito="TARJETA DEBITO";
    public static final String tranferencia="TRANSFERENCIA";
    
    //USUARIO
    public static final String establecimiento="ESTABLECIMIENTO";
    public static final String perfil="PERFIL";
    public static final String permiso="PERMISO";
    public static final String estacion="ESTACION";
    public static final String sesion="SESION";
    public static final String usuario="USUARIO";
    public static final String estacion_usuario= "ESTACION USUARIO";
    public static final String celular_establecimiento="CELULAR ESTABLECIMIENTO";
    public static final String correo_establecimiento="CORREO ESTABLECIMIENTO";
    public static final String telefono_establecimiento="TELEFONO ESTABLECIMIENTO";
	
    //CONTABILIDAD
    public static final String afectacion_contable="AFECTACION CONTABLE";
    public static final String movimiento_contable="MOVIMIENTO CONTABLE";
    public static final String cuenta_contable="CUENTA CONTABLE";

    //CAMPOS DE TABLAS
    public static final String descripcion="DESCRIPCION";
    public static final String abreviatura="ABREVIATURA";
    public static final String numero="NUMERO";
    public static final String equivalencia="EQUIVALENCIA";

    public static final String grupo="GRUPO";
    public static final String subgrupo="SUBGRUPO";
    public static final String seccion="SECCION";
    public static final String linea="LINEA";
    public static final String sublinea="SUBLINEA";
    public static final String presentacion="PRESENTACION";
    public static final String identificacion="IDENTIFICACION";
    public static final String razonSocial="RAZON SOCIAL";
    public static final String especial="ESPECIAL";

    public static final String estado="ESTADO";
    public static final String obligadoContabilidad="OBLIGADO A CONTABILIDAD";
    public static final String referencia="REFERENCIA";
    public static final String montoFinanciamiento = "MONTO DE FINANCIAMIENTO";
    public static final String email="EMAIL";
    public static final String monto="MONTO";
    public static final String codigoSRI="Codigo SRI";
    public static final String subtipo="SUBTIPO";
    public static final String nombreComercial="NOMBRE COMERCIAL";
    public static final String secuencia="SECUENCIA";

    public static final String codigoNumerico="CODIGO NUMERICO";
    public static final String fecha="FECHA";
    public static final String logo="LOGO";
    public static final String nombreTabla="NOMBRE DE LA TABLA";
    public static final String tabla="TABLA";
    public static final String impuestoRetencion="IMPUESTO DE RETENCION";
    public static final String tipoRetencion="TIPO DE RETENCION";
    public static final String porcentaje="PORCENTAJE";
    public static final String plazo="PLAZO";
    public static final String codigoNorma="CODIGO DE LA NORMA";
    public static final String provincia="PROVINCIA";
    public static final String canton="CANTON";
    public static final String parroquia="PARROQUIA";
    public static final String cuenta = "CUENTA";
    public static final String clasificacion="CLASIFICACION";
    public static final String nivel="NIVEL";
    public static final String fe="FACTURACION ELECTRONICA";
    public static final String casillero="CASILLERO";
    public static final String mapeo="MAPEO";
    public static final String inventario="INVENTARIO";
    public static final String costoVenta="COSTO DE VENTA";
    public static final String devolucionCompra="DEVOLUCION COMPRA";
    public static final String descuentoCompra="DESCUENTO COMPRA";
    public static final String venta="VENTA";
    public static final String devolucionVenta="DEVOLUCION DE VENTA";
    public static final String descuentoVenta="DESCUENTO DE VENTA";
    public static final String devolucionCostoVenta="DEVOLUCION COSTO DE VENTA";
    public static final String cantidad="CANTIDAD";
    public static final String costoUnitario="COSTO UNITARIO";
    public static final String devolucion="DEVOLUCION";
    public static final String valorDescuentoLinea = "VALOR DESCUENTO POR LINEA";
    public static final String porcentajeDescuentoLinea = "PORCENTAJE DESCUENTO POR LINEA";
    public static final String nombre="NOMBRE";
    public static final String contrasena="CONTRASEÑA";
    public static final String confirmarContrasena="CONFIRMAR CONTRASEÑA";
    public static final String cambiarContrasena="CAMBIAR CONTRASEÑA";
    public static final String pregunta="PREGUNTA";
    public static final String respuesta="RESPUESTA";
    public static final String consignacion="CONSIGNACION";
    public static final String stockTotal="STOCK TOTAL";
    public static final String margenGanancia="MARGEN DE GANANCIA";
    public static final String total="TOTAL";
    public static final String multiempresa="MULTI EMPRESA";
    public static final String apodo="APODO";


    
    //CONSTANTE PARA REGISTRO DE TABLA EN PAGINACIÓN
    public static final int size=20;
    public static final String order="id";
    
    //CONSTANTES FACTURACION ELECTRONICA
    
    public static final String serie_sri="001001";
    public static final double cero=0;
    public static final long ceroId = 0;
    public static final double iva0 = 0;
    public static final double iva12 = 12;
    
    //TABLA 2
    public static final String emision_normal_sri="1";
    //TABLA 3
    public static final String factura_sri="01";
    public static final String liquidacion_de_compra_bienes_y_prestacion_de_servicios_sri="03";
    public static final String nota_de_credito_sri="04";
    public static final String nota_de_debito_sri="05";
    public static final String guia_de_remision_sri="06";
    public static final String comprobante_de_retencion_sri="07";
    //TABLA 4
    public static final String pruebas_sri="1";
    public static final String produccion_sri="2";
    
    //TABLA 16
    public static final String iva_sri="2";
    public static final String ice_sri="3";
    public static final String irbpnr_sri= "5";
    //TABLA 24
    public static final String sin_utilizacion_del_sistema_financiero="01";
    public static final String compensacion_de_deudas="15";
    public static final String tarjeta_de_debito="16";
    public static final String dinero_electronico="17";
    public static final String tarjeta_prepago="18";
    public static final String tarjeta_de_credito="19";
    public static final String otros_con_utilizacion_sistema_financiero="20";
    public static final String endoso_titulos="21";
    
    //OTRAS
    public static final String iva= "IVA";
    public static final String renta= "RENTA";
    public static final String bien= "BIEN";
    public static final String servicio = "SERVICIO";
    public static final String inicioTelefono = "0";
    public static final String inicioCelular = "09";
    public static final String arroba = "@";
    
    //NUMEROS
    public static final int uno = 1;
    public static final int dos = 2;
    public static final int tres = 3;
    public static final int cuatro = 4;
    public static final int cinco = 5;
    public static final int seis = 6;
    public static final int siete = 7;
    public static final int ocho = 8;
    public static final int nueve = 9;
    public static final int diez = 10;
    public static final int once= 11;
    
    public static final String urlFacturacionEletronicaSri = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline";
    public static final String urlConsultaFacturacionEletronicaSri = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline";
    public static final String utf8 = "utf-8";
    public static final String contentType = "Content-Type";
    public static final String moneda = "USD";
    public static final String contenTypeValor = "text/xml;charset=UTF-8";
    public static final String mensajeSri = "LA RESPUESTA DEL SRI A LA FACTURA ES: ";
    public static final String mensajeNoSri = "LA FACTURA YA FUE PROCESADO CON CLAVE DE ACCESO: ";
    public static final String mensajeClaveAccesoSri = " CON CLAVE DE ACCESO: ";
    public static final String certificadoSri = "certificado.p12";
    public static final String contrasenaCertificadoSri = "mPrimero1981";
    public static final String recibidaSri = "RECIBIDA";
    public static final String autorizadoSri = "AUTORIZADO";
    
    //ESTADOS FACTURA
    public static final String estadoEmitida="EMITIDA";
    public static final String estadoAnulada="ANULADA";
    public static final String estadoFacturada="FACTURADA";
    public static final String estadoNoFacturada="NO FACTURADA";
    
    //CONSTANTES PARA FACTURA FISICA
    public static final String facturaFisicaAmbienteValor="PRUEBAS";
    public static final String facturaFisicaEmisionValor= "NORMAL";
    
    //CONSTANTES PARA ENVIO DE CORREO
    public static final String mensajeCorreo=" LE ENVIA FACTURA ELECTRONICA: ";
    public static final String mensajeCorreoExitoso="SE ENVIA FACTURA A CORREO EXITOSAMENTE";
    public static final String mailSmtpHost="mail.smtp.host";
    public static final String mailSmtpUser="mail.smtp.user";
    public static final String mailSmtpClave="mail.smtp.clave";
    public static final String mailSmtpAuth="mail.smtp.auth";
    public static final String mailSmtpStarttlsEnable="mail.smtp.starttls.enable";
    public static final String mailSmtpPort="mail.smtp.port";
    public static final String valorMailSmtpHost="smtp.gmail.com";
    public static final String valorMailtSmtpAuth="true";
    public static final String valorMailtSmtpStarttlsEnable="true";
    public static final String valorMailSmtpPort="587";
    public static final String smtp = "smtp";
    public static final String smtpGmailCom = "smtp.gmail.com";
    public static final String extensionPdf =".pdf";
    public static final String extensionXml =".xml";
    public static final String applicationPdf="application/pdf";
    public static final String textXml="text/xml";
    
}
