package com.proyecto.vision;

public class Constantes {

    public static final String identificacion_consumidor_final="9999999999999";
    public static final String consumidor_final="CONSUMIDOR FINAL";
    public static final String tipo_contribuyente_natural="NATURAL";
    public static final String tipo_contribuyente_juridica="JURIDICA";
    public static final String tipo_contribuyente_publica="PUBLICA";
    public static final String tipo_contribuyente_privada="PRIVADA";
    public static final String correoPorDefecto="gerencia.evoluciontic@gmail.com";

    public static final String pathRecursos = "src/main/resources/";
    public static final String pathAvatares = "avatares";
    public static final String pathLogos = "logos";
    public static final String pathCertificados = "certificados";
    public static final String pathDrivers = "drivers";
    public static final String pathFacturas = "facturas";
    public static final String slash = "/";

    public static final String cedula_abreviatura="C";
    public static final String consumidor_final_abreviatura="CF";
    public static final String ruc_abreviatura="R";
    public static final String placa_abreviatura="PL";
    public static final String pasaporte_abreviatura="E";
    public static final String compra = "COMPRA";
    public static final String factura_recibida_sri = "Factura";
    public static final String codigo_cedula_sri = "05";
    public static final String codigo_consumidor_final_sri = "07";
    public static final String codigo_sociedades_publicas_sri = "04";
    public static final String codigo_sociedades_privadas_sri = "04";
    public static final String codigo_ruc_sri = "04";
    public static final String codigo_placa_sri = "07";
    public static final String codigo_pasaporte_sri = "06";

    public static final String origin = "Origin";
    public static final String url_cedula_consultas_ecuador = "https://apiston.consultasecuador.com/api/v1/pers/find-names?id=";
    public static final String url_placa_consultas_ecuador = "https://apiston.consultasecuador.com/api/v1/find-car-owner?placa=";
    public static final String consultas_ecuador = "https://consultasecuador.com";
    public static final String url_ruc_consultas_ecuador = "https://srienlinea.sri.gob.ec/facturacion-internet/consultas/publico/ruc-datos2.jspa?ruc=";

    public static final String pendiente="PENDIENTE";
    public static final String entregado="ENTREGADO";
    public static final String sinGuia="SIN GUIA";
    public static final String factura_compra = "FACTURA DE COMPRA";
    public static final String factura_compra_linea = "FACTURA DE COMPRA LINEA";
    public static final String nota_credito_compra = "NOTA CREDITO DE COMPRA";
    public static final String nota_credito_compra_linea = "NOTA CREDITO DE COMPRA LINEA";
    public static final String guia_remision = "GUIA DE REMISION";
    public static final String nota_credito_venta = "NOTA CREDITO DE VENTA";
    public static final String nota_credito_venta_linea = "NOTA CREDITO DE VENTA LINEA";
    public static final String nota_debito_compra = "NOTA DEBITO DE COMPRA";
    public static final String nota_debito_compra_linea = "NOTA DEBITO DE COMPRA LINEA";
    public static final String operacion_devolucion = "DEVOLUCION";
    public static final String operacion_descuento = "DESCUENTO";

    public static final String mensaje_exitoso="Exito";
    public static final String mensaje_consultar_exitoso="Exito en la consulta";
    public static final String mensaje_obtener_exitoso="Exito en la consulta";
    public static final String mensaje_crear_exitoso="Exito al procesar";
    public static final String mensaje_actualizar_exitoso="Exito al actualizar";
    public static final String mensaje_crear_factura_electronica_exitosa="Exito factura electronica con clave de acceso";
    public static final String mensaje_crear_nota_debito_electronica_exitosa="Exito nota debito electronica con clave de acceso";
    public static final String mensaje_crear_nota_credito_electronica_exitosa="Exito nota credito electronica con clave de acceso";
    public static final String mensaje_crear_guia_remision_electronica_exitosa="Exito guia remision electronica con clave de acceso";
    public static final String mensaje_calcular_exitoso="Exito al calcular";
    public static final String mensaje_activar_exitoso="Exito al activar";
    public static final String mensaje_inactivar_exitoso="Exito al inactivar";
    public static final String mensaje_recaudacion_exitosa="Exito al recaudar";
    public static final String mensaje_anular_exitoso="Exito al anular";

    public static final String vacio="";
    public static final String espacio=" ";
    public static final String guion = "-";
    public static final String guion_bajo = "_";
    public static final String salto = "\n";
    public static final String si = "SI";
    public static final String no = "NO";

    public static String error_codigo_generico="4000";
    public static String error_codigo_modelo_existente="4001";
    public static String error_codigo_modelo_no_existente="4002";
    public static String error_codigo_codigo_no_existente="4003";
    public static String error_codigo_sesion_invalida="4004";
    public static String error_codigo_datos_invalidos="4005";
    public static String error_codigo_identificacion_invalida="4006";
    public static String error_codigo_secuencial_no_existente="4007";
    public static String error_codigo_cliente_no_existente="4008";
    public static String error_codigo_parametro_invalido="4009";

    public static String error_codigo_estado_invalido="4010";
    public static String error_codigo_dato_invalido="4011";
    public static String error_codigo_factura_electronica_invalida="4012";
    public static String error_codigo_certificado_no_existente="4013";
    public static String error_codigo_suscripcion_invalida="4014";

    public static String error_generico="ERROR INTERNO DE SERVIDOR";
    public static String error_entidad_existente="ERROR ENTIDAD YA EXISTE";
    public static String error_entidad_no_existente="ERROR ENTIDAD NO EXISTENTE";
    public static String error_codigo_no_existente="ERROR CODIGO INTERNO NO EXISTENTE";
    public static String error_sesion_invalida="ERROR SESION INVALIDA";
    public static String error_datos_invalidos="ERROR DATOS INVALIDOS";
    public static String error_identificacion_invalida="ERROR IDENTIFICACION INVALIDA";
    public static String error_secuencial_no_existente="ERROR SECUENCIAL NO EXISTENTE";
    public static String error_cliente_no_existente="ERROR CLIENTE NO EXISTENTE";
    public static String error_parametro_invalido="ERROR PARAMETRO INVALIDO";
    public static String error_estado_invalido="ERROR ESTADO INVALIDO";
    public static String error_dato_invalido="ERROR DATO INVALIDO";
    public static String error_certificado_no_existente="ERROR CERTIFICADO PARA SRI NO EXISTENTE";
    public static String error_suscripcion_invalida="ERROR SUSCRIPCION INVALIDA POR FAVOR RENOVAR";

    //CODIGO PARA CREAR
    public static final String operacionCrear = "CREAR";

    //TABLAS
    //CLIENTE
    public static final String tabla_modelo="modelo";
    public static final String tabla_calificacion_cliente="calificacion_cliente";
    public static final String tabla_celular="celular";
    public static final String tabla_celular_dependiente="celular_dependiente";
    public static final String tabla_cliente="cliente";
    public static final String tabla_correo="correo";
    public static final String tabla_correo_dependiente="correo_dependiente";
    public static final String tabla_dependiente = "dependiente";
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

    //COMPRA
    public static final String tabla_grupo_proveedor="grupo_proveedor";
    public static final String tabla_telefono_proveedor="telefono_proveedor";
    public static final String tabla_celular_proveedor="celular_proveedor";
    public static final String tabla_correo_proveedor="correo_proveedor";
    public static final String tabla_factura_compra="factura_compra";
    public static final String tabla_factura_compra_linea="factura_compra_linea";
    public static final String tabla_nota_credito_compra="nota_credito_compra";
    public static final String tabla_nota_credito_compra_linea="nota_credito_compra_linea";
    public static final String tabla_nota_debito_compra="nota_debito_compra";
    public static final String tabla_nota_debito_compra_linea="nota_debito_compra_linea";
    public static final String tabla_gasto_personal="gasto_personal";
    public static final String tabla_gasto_personal_linea="gasto_personal_linea";

    //VENTAS
    public static final String operacion = "OPERACION";
    public static final String tabla_factura_linea="factura_linea";
    public static final String tabla_egreso="egreso";
    public static final String tabla_factura="factura";
    public static final String tabla_pedido="pedido";
    public static final String tabla_proforma="proforma";
    public static final String tabla_tipo_comprobante="tipo_comprobante";
    public static final String tabla_nota_credito="nota_credito";
    public static final String tabla_nota_credito_linea="nota_credito_linea";
    public static final String tabla_nota_debito="nota_debito";
    public static final String tabla_nota_debito_linea="nota_debito_linea";
    public static final String tabla_cierre_caja="cierre_caja";
    public static final String tabla_retencion="retencion";

    //CONFIGURACION
    public static final String tabla_empresa="empresa";
    public static final String tabla_parametro="parametro";
    public static final String tabla_tipo_retencion="tipo_retencion";
    public static final String tabla_ubicacion="ubicacion";
    public static final String tabla_tipo_identificacion="tipo_identificacion";
    public static final String tabla_secuencial="secuencial";
    public static final String tabla_menu_opcion="menu_opcion";
    public static final String tabla_sincronizacion="sincronizacion";

    //ENTREGA
    public static final String tabla_guia_remision="guia_remision";
    public static final String tabla_transportista="transportista";
    public static final String tabla_vehiculo="vehiculo";

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
    public static final String tabla_producto_proveedor="producto_proveedor";
    public static final String tabla_segmento="segmento";
    public static final String tabla_equivalencia_medida="equivalencia_medida";
    public static final String tabla_tipo_gasto="tipo_gasto";
    public static final String tabla_tipo_operacion="tipo_operacion";

    //CAJA BANCOS
    public static final String tabla_banco="banco";
    public static final String tabla_banco_propio="banco_propio";

    //RECAUDACION
    public static final String tabla_amortizacion="amortizacion";
    public static final String tabla_cuenta_propia="cuenta_propia";
    public static final String tabla_franquicia_tarjeta="franquicia_tarjeta";
    public static final String tabla_operador_tarjeta="operador_tarjeta";
    public static final String tabla_recaudacion="recaudacion";
    public static final String tabla_cheque="cheque";
    public static final String tabla_deposito="deposito";
    public static final String tabla_tarjeta_credito="tarjeta_credito";
    public static final String tabla_tarjeta_debito="tarjeta_debito";
    public static final String tabla_transferencia="transferencia";
    public static final String tabla_credito="credito";
    public static final String tabla_nd_cheque="nd_cheque";
    public static final String tabla_nd_deposito="nd_deposito";
    public static final String tabla_nd_tarjeta_credito="nd_tarjeta_credito";
    public static final String tabla_nd_tarjeta_debito="nd_tarjeta_debito";
    public static final String tabla_nd_transferencia="nd_transferencia";
    public static final String tabla_nd_credito="nd_credito";

    //ACCESO
    public static final String tabla_telefono_establecimiento="telefono_establecimiento";
    public static final String tabla_celular_establecimiento="celular_establecimiento";
    public static final String tabla_correo_establecimiento="correo_establecimiento";
    public static final String tabla_establecimiento="establecimiento";
    public static final String tabla_estacion="estacion";
    public static final String tabla_perfil="perfil";
    public static final String tabla_permiso="permiso";
    public static final String tabla_sesion="sesion";
    public static final String tabla_usuario="usuario";
    public static final String tabla_regimen="regimen";
    public static final String tabla_suscripcion="suscripcion";
    public static final String tabla_paquete="paquete";
    public static final String tabla_nuevo="nuevo";

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
    public static final String secuencial = "SECUENCIAL";
    public static final String sincronizacion = "SINCRONIZACION";

    //COMPRA
    public static final String retencion_compra="RETENCION COMPRA";
    public static final String grupo_proveedor="GRUPO PROVEEDOR";
    public static final String gasto_personal="GASTO PERSONAL";
    public static final String fantasma="EMPRESA FANTASMA";

    //COMPROBANTE
    public static final String factura_detalle="DETALLE FACTURA";
    public static final String egreso="EGRESO";
    public static final String factura="FACTURA";
    public static final String nota_credito="NOTA CREDITO";
    public static final String nota_debito="NOTA DEBITO";
    public static final String nota_debito_linea= "NOTA DEBITO LINEA";
    public static final String factura_electronica="FACTURA ELECTRONICA";
    public static final String pedido="PEDIDO";
    public static final String proforma="PROFORMA";
    public static final String tipo_comprobante="TIPO COMPROBANTE";
    public static final String factura_interna = "FACTURA INTERNA";
    public static final String mensaje_error_cierre_caja = "CIERRE DE CAJA EXISTENTE";
    public static final String mensaje_error_nota_debito_existente = "NOTA DE DEBITO EXISTENTE";
    public static final String mensaje_error_nota_credito_existente = "NOTA DE CREDITO EXISTENTE";
    public static final String mensaje_error_guia_remision_existente = "GUIA DE REMISION EXISTENTE";
    public static final String mensaje_error_factura_cerrada = "FACTURA CERRADA";

    //CONFIGURACION
    public static final String empresa="EMPRESA";
    public static final String menu_opcion=",MENU OPCION";
    public static final String parametro="PARAMETRO";
    public static final String tipo_retencion="TIPO RETENCION";
    public static final String ubicacion="UBICACION";
    public static final String correo_predeterminado="facturacion@futuristic.com.ec";

    //ENTREGA
    public static final String entrega="ENTREGA";
    public static final String transportista="TRANSPORTISTA";
    public static final String vehiculo_transporte="VEHICULO TRANSPORTE";
    public static final String cliente_direccion = "CLIENTE DIRECCION";
    public static final String nueva_direccion = "NUEVA DIRECCION";
    public static final String sin_guia = "SIN GUIA";

    //INVENTARIO
    public static final String bodega="BODEGA";
    public static final String categoria_producto="CATEGORIA PRODUCTO";
    public static final String grupo_producto="GRUPO PRODUCTO";
    public static final String impuesto="IMPUESTO";
    public static final String kardex="KARDEX";
    public static final String medida="MEDIDA";
    public static final String equivalencia_medida="EQUIVALENCIA MEDIDA";
    public static final String precio="PRECIO";
    public static final String producto="PRODUCTO";
    public static final String proveedor="PROVEEDOR";
    public static final String segmento="SEGMENTO";
    public static final String tipo_gasto="TIPO GASTO";
    public static final String tipo_operacion="TIPO OPERACION";
    public static final String bodegaNombreDefecto = "BODEGA MATRIZ";
    public static final String bodegaAbreviaturaDefecto = "BM001";
    public static final String calificacionCliente1DescripcionDefecto = "EXCELENTE";
    public static final String calificacionCliente1AbreviaturaDefecto = "EX";
    public static final String calificacionCliente2DescripcionDefecto = "MUY BUENO";
    public static final String calificacionCliente2AbreviaturaDefecto = "MB";
    public static final String calificacionCliente3DescripcionDefecto = "BUENO";
    public static final String calificacionCliente3AbreviaturaDefecto = "BU";
    public static final String calificacionCliente4DescripcionDefecto = "REGULAR";
    public static final String calificacionCliente4AbreviaturaDefecto = "RE";
    public static final String calificacionCliente5DescripcionDefecto = "MALO";
    public static final String calificacionCliente5AbreviaturaDefecto = "MA";
    public static final String clienteEtiquetaDefecto = "CASA";
    public static final String grupoClienteDescripcionDefecto="CLIENTE GENERAL";
    public static final String grupoClienteAbreviaturaDefecto="CLG";
    public static final String grupoProveedorDescripcionDefecto = "PROVEEDOR GENERAL";
    public static final String grupoProveedorAbreviaturaDefecto = "PRG";
    public static final String medidaTipoUnidad="UNIDAD";
    public static final String medidaDescripcionUnidad="UNIDAD";
    public static final String medidaAbreviaturaUnidad="UNI";
    public static final String medidaDescripcionMitad="MITAD";
    public static final String medidaAbreviaturaMitad="MIT";
    public static final String medidaDescripcionCuarto="CUARTO";
    public static final String medidaAbreviaturaCuarto="CUA";
    public static final String medidaTipoPeso="PESO";
    public static final String medidaDescripcionGramo="GRAMO";
    public static final String medidaAbreviaturaGramo="G";
    public static final String medidaDescripcionKilogramo="KILOGRAMO";
    public static final String medidaAbreviaturaKilogramo="KG";
    public static final String medidaDescripcionLibra="LIBRA";
    public static final String medidaAbreviaturaLibra="LB";
    public static final String medidaTipoVolumen="VOLUMEN";
    public static final String medidaDescripcionCentimetroCubico="CENTÍMETRO CÚBICO";
    public static final String medidaAbreviaturaCentimetroCubico="CM^3";
    public static final String medidaDescripcionGalon="GALÓN";
    public static final String medidaAbreviaturaGalon="GAL";
    public static final String medidaDescripcionLitro="LITRO";
    public static final String medidaAbreviaturaLitro="L";
    public static final String plazoCreditoDescripcionDefecto="SIN CREDITO";
    public static final String plazoCreditoAbreviaturaDefecto="SCR";
    public static final String segmentoDescripcionDefecto="CLIENTE FINAL";
    public static final String segmentoAbreviaturaDefecto="CLF";
    public static final long segmentoMargenGananciaDefecto=10;
    public static final String estacionDescripcionDefecto="VENTAS";
    public static final String estacionDispositivoDefecto="PC";
    public static final String estacionIpDefecto="192.168.1.1";
    public static final String usuarioContrasenaDefecto="827ccb0eea8a706c4c34a16891f84e7b";
    public static final String masculino="MASCULINO";
    public static final String femenino="FEMENINO";
    public static final String avatarMasculino="gerente.png";
    public static final String avatarFemenino="gerentef.png";
    public static final String usuarioPreguntaDefecto="¿CUAL ES TU MARCA DE VEHICULO PREFERIDO?";
    public static final String usuarioRespuestaDefecto="MAZDA";
    public static final String perfilGerencial="GERENCIAL";
    public static final String soltero="SOLTERO";
    public static final String salario="SALARIO";

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
    public static final String recaudacion="RECAUDACION";
    public static final String tarjeta_credito="TARJETA CREDITO";
    public static final String tarjeta_debito="TARJETA DEBITO";
    public static final String transferencia="TRANSFERENCIA";
    public static final String efectivo="EFECTIVO";
    public static final String cierre_caja="CIERRE CAJA";

    //ACCESO
    public static final String establecimiento="ESTABLECIMIENTO";
    public static final String perfil="PERFIL";
    public static final String permiso="PERMISO";
    public static final String estacion="ESTACION";
    public static final String punto_venta="PUNTO VENTA";
    public static final String sesion="SESION";
    public static final String usuario="USUARIO";
    public static final String regimen="REGIMEN";
    public static final String suscripcion="SUSCRIPCION";
    public static final String paquete="PAQUETE";
    public static final String granContribuyente="GRAN CONTRIBUYENTE";
    public static final String artesanoCalificado="ARTESANO CALIFICADO";
    public static final String facturacionInterna="FACTURACION INTERNA";
    public static final String secuenciaFacturaVenta="SECUENCIA FACTURA DE VENTA";
    public static final String secuenciaFacturaInterna="SECUENCIA FACTURA INTERNA";
    public static final String secuenciaRetencion="SECUENCIA RETENCION";
    public static final String secuenciaGuiaRemision="SECUENCIA GUIA REMISION";
    public static final String secuenciaNotaDebito="SECUENCIA DE NOTA DEBITO";
    public static final String secuenciaNotaCredito="SECUENCIA DE NOTA DE CREDITO";
    public static final String ruc_codigo_sri="04";
    public static final String propietario="PROPIETARIO";
    public static final String jpg=".jpg";
    public static final String p12=".p12";


    //CONTABILIDAD
    public static final String afectacion_contable="AFECTACION CONTABLE";
    public static final String movimiento_contable="MOVIMIENTO CONTABLE";
    public static final String cuenta_contable="CUENTA CONTABLE";

    //CAMPOS DE TABLAS
    public static final String descripcion="DESCRIPCION";
    public static final String abreviatura="ABREVIATURA";
    public static final String numero = "NUMERO";
    public static final String valor = "VALOR";
    public static final String comprobante = "COMPROBANTE";
    public static final String tipo_transaccion = "TIPO TRANSACCION";
    public static final String numero_transaccion = "NUMERO TRANSACCION";
    public static final String nombre_titular = "NOMBRE TITULAR";
    public static final String diferido = "DIFERIDO";
    public static final String titular = "TITULAR";
    public static final String lote = "LOTE";
    public static final String dato_tipo = "TIPO";
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
    public static final String agenteRetencion="AGENTE RETENCION";

    public static final String estado="ESTADO";
    public static final String obligadoContabilidad="OBLIGADO A CONTABILIDAD";
    public static final String referencia="REFERENCIA";
    public static final String montoFinanciamiento = "MONTO DE FINANCIAMIENTO";
    public static final String email="EMAIL";
    public static final String monto="MONTO";
    public static final String codigoSRI="Codigo SRI";
    public static final String subtipo="SUBTIPO";
    public static final String nombreComercial="NOMBRE COMERCIAL";

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
    public static final String devolucionVenta="DEV. VENTA";
    public static final String descuentoVenta="DES. VENTA";
    public static final String devolucionCostoVenta="DEVOLUCION COSTO DE VENTA";
    public static final String cantidad="CANTIDAD";
    public static final String costoUnitario="COSTO UNITARIO";
    public static final String devolucion="DEVOLUCION";
    public static final String valorDescuentoLinea = "VALOR DESCUENTO POR LINEA";
    public static final String porcentajeDescuentoLinea = "PORCENTAJE DESCUENTO POR LINEA";
    public static final String nombre="NOMBRE";
    public static final String contrasenaSRI="CONTRASEÑA SRI";
    public static final String contrasena="CONTRASEÑA";
    public static final String confirmarContrasena="CONFIRMAR CONTRASEÑA";
    public static final String cambiarContrasena="CAMBIAR CONTRASEÑA";
    public static final String pregunta="PREGUNTA";
    public static final String respuesta="RESPUESTA";
    public static final String consignacion="CONSIGNACION";
    public static final String stockTotal="STOCK TOTAL";
    public static final String margenGanancia="MARGEN DE GANANCIA";
    public static final String total="TOTAL";
    public static final String total_recaudacion="TOTAL RECAUDACION";
    public static final String multiempresa="MULTI EMPRESA";
    public static final String apodo="APODO";
    public static final String tipo="TIPO";
    public static final String motivoTraslado="MOTIVO TRASLADO";
    public static final String ruta="RUTA";
    public static final String fechaInicioTransporte="FECHA INICIO TRANSPORTE";
    public static final String fechaFinTransporte="FECHA FIN TRANSPORTE";
    public static final String minimoComprobantes="MINIMO DE COMPROBANTES";
    public static final String maximoComprobantes="MAXIMO DE COMPROBANTES";
    public static final String valorPuestaInicial="VALOR DE PUESTA INICIAL";
    public static final String porcentajeComision="PORCENTAJE DE COMISION";
    public static final String cantidadUsuarioRecaudacion="CANTIDAD DE USUARIOS PARA RECAUDACION";
    public static final String cantidadUsuarioGerente="CANTIDAD DE USUARIOS PARA GERENTE";
    public static final String cantidadUsuarioAdministrador="CANTIDAD DE USUARIOS PARA ADMINISTRADOR";
    public static final String mensual="MENSUAL";
    public static final String anual="ANUAL";
    public static final String mes="MES";
    public static final String anio="AÑO";

    //CONSTANTE PARA REGISTRO DE TABLA EN PAGINACIÓN
    public static final int size=20;
    public static final String order="id";

    //CONSTANTES FACTURACION ELECTRONICA
    public static final double cero = 0;
    public static final long ceroId = 0;
    public static final double iva12 = 12;

    //TABLA 2
    public static final String emision_normal_sri="1";
    //TABLA 3
    public static final String factura_sri="01";
    public static final String liquidacion_de_compra_bienes_y_prestacion_de_servicios_sri="03";
    public static final String nota_credito_sri="04";
    public static final String nota_debito_sri="05";
    public static final String guia_de_remision_sri="06";
    public static final String comprobante_de_retencion_sri="07";
    //TABLA 4
    public static final String pruebas_sri="1";
    public static final String produccion_sri="2";

    //TABLA 16
    public static final String iva_sri="2";
    public static final String iva_0_sri="0";
    public static final String iva_8_sri="8";
    public static final String iva_12_sri="2";
    public static final String iva_14_sri="3";
    public static final String iva_15_sri="4";
    public static final String iva_5_sri="5";
    public static final String iva_13_sri="10";

    //TABLA 24
    public static final String sin_utilizacion_del_sistema_financiero="01";
    public static final String tarjeta_de_debito="16";
    public static final String tarjeta_de_credito="19";
    public static final String otros_con_utilizacion_sistema_financiero="20";
    public static final String endoso_titulos="21";
    public static final String texto_sin_utilizacion_del_sistema_financiero="sin utilizacion del sistema financiero";
    public static final String texto_tarjeta_de_debito = "tarjeta de debito";
    public static final String texto_tarjeta_de_credito = "tarjeta de credito";
    public static final String texto_otros_con_utilizacion_sistema_financiero = "otros con utilizacion sistema financiero";
    public static final String texto_endoso_titulos = "endoso titulos";

    //OTRAS
    public static final String iva= "IVA";
    public static final String renta= "RENTA";
    public static final String bien= "BIEN";
    public static final String servicio = "SERVICIO";
    public static final String abreviatura_bien= "B";
    public static final String abreviatura_servicio = "S";
    public static final String inicioCelular = "09";
    public static final String arroba = "@";

    //COORDENADAS
    public static final double latCiudad = -1.6719601146175727;
    public static final double lngCiudad = -78.65041698970757;

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
    public static final double ciencuenta = 50;

    public static final String urlPruebasFacturacionEletronicaSri = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline";
    public static final String urlPruebasConsultaFacturacionEletronicaSri = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline";
    public static final String urlProduccionFacturacionEletronicaSri = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline";
    public static final String urlProduccionConsultaFacturacionEletronicaSri = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline";
    public static final String urlSri="https://srienlinea.sri.gob.ec/sri-en-linea/contribuyente/perfil";
    public static final String utf8 = "utf-8";
    public static final String contentType = "Content-Type";
    public static final String moneda = "USD";
    public static final String contenTypeValor = "text/xml;charset=UTF-8";
    public static final String recibidaSri = "RECIBIDA";
    public static final String autorizadoSri = "AUTORIZADO";
    public static final String noAutorizadoSri = "NO AUTORIZADO";
    public static final String devueltaSri = "DEVUELTA";
    public static final String contrasenaCertificado="CONTRASEÑA CERTIFICADO";
    public static final String contrasenaSri="CONTRASEÑA DEL SRI";

    //ESTADOS DEL SISTEMA
    public static final String estadoActivo="ACTIVO";
    public static final String estadoInactivo="INACTIVO";
    public static final String estadoRecibida="RECIBIDA";

    //ESTADOS VENTAS
    public static final String estadoEmitida = "EMITIDA";
    public static final String estadoRecaudada = "RECAUDADA";
    public static final String estadoAnulada = "ANULADA";
    public static final String estadoCerrada = "CERRADA";
    public static final String procesoSRIPendiente = "PENDIENTE";
    public static final String procesoSRIAutorizada = "AUTORIZADA";
    public static final String procesoSRIAnulada = "ANULADA";

    //ESTADOS COMPRAS
    public static final String estadoPagada = "PAGADA";
    public static final String estadoPorPagar = "POR PAGAR";

    //CONSTANTES PARA FACTURA FISICA
    public static final String facturaFisicaAmbientePruebasValor = "PRUEBAS";
    public static final String facturaFisicaAmbienteProduccionValor = "PRODUCCION";
    public static final String facturaFisicaEmisionValor = "NORMAL";

    //CONSTANTES PARA ENVIO DE CORREO
    public static final String mensajeCorreo=" LE ENVIA FACTURA ELECTRONICA: ";
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
    public static final String extensionTxt =".txt";
    public static final String applicationPdf="application/pdf";
    public static final String textXml="text/xml";
    public static final float fontSize5 = 5;
    public static final float fontSize6 = 6;
    public static final float fontSize7 = 7;
    public static final float fontSize10 = 10;
    public static final float fontSize16 = 16;
    public static final float fontSize30 = 30;
    public static final String nombreReporteVenta = "DETALLE DE VENTAS";
    public static final String nombreReporteKardex = "KARDEX DE MERCADERIAS";
    public static final String nombreReporteCaja = "CIERRE DE CAJA";
    public static final String nombreReporteExistencia = "EXISTENCIAS";
    public static final String fechaCorta = "dd-MM-yyyy";
    public static final String fechaCortaSri = "dd/MM/yyyy";
    public static final String fechaYHora = "dd-MM-yyyy hh:mm:ss";
    public static final String hora = "hh:mm:ss";
    public static final String signoDolar= "$";

    public static final double billete100 = 100.00;
    public static final double billete50 = 50.00;
    public static final double billete20 = 20.00;
    public static final double billete10 = 10.00;
    public static final double billete5 = 5.00;
    public static final double billete2 = 2.00;
    public static final double billete1 = 1.00;

    public static final double moneda100 = 1.00;
    public static final double moneda50 = 0.50;
    public static final double moneda25 = 0.25;
    public static final double moneda10 = 0.10;
    public static final double moneda5 = 0.05;
    public static final double moneda1 = 0.01;
}