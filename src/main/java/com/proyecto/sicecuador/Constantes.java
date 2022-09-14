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
	public static final String norecaudado="NORECAUDADO";
	
	public static final String emitida="EMITIDA";
	public static final String noemitida="NOEMITIDA";
	
	public static final String pendiente="PENDIENTE";
	public static final String entregado="ENTREGADO";
	
	public static final String activo="ACTIVO";
	public static final String inactivo="INACTIVO";
	
    public static final String mensaje_consultar_exitoso="Exito en la consulta";
    public static final String mensaje_obtener_exitoso="Exito en la consulta";
    public static final String mensaje_crear_exitoso="Exito al procesar";
    public static final String mensaje_actualizar_exitoso="Exito al actualizar";
    public static final String mensaje_eliminar_exitoso="Exito al eliminar";
    public static final String mensaje_importacion_exitoso="Exito al importar";
    public static final String mensaje_exportacion_exitoso="Exito al exportar";
    public static final String mensaje_calcular_exitoso="Exito al calcular";

    public static final String mensaje_consulta_fallido="Error en la consulta los objetos";
    public static final String mensaje_obtener_fallido="Error al obtener un objeto";
    public static final String mensaje_crear_fallido="Error al crear un objeto";
    public static final String mensaje_actualizar_fallido="Error al actualizar un objeto";
    public static final String mensaje_eliminar_fallido="Exito al eliminar un objeto";
    public static final String mensaje_importacion_fallido="Error al importar";
    public static final String mensaje_exportacion_fallido="Error al exportar";

    public static final String mensaje_validacion_not_blank=" no debe estar en blanco";
    public static final String mensaje_validacion_not_null=" se debe registrar";

    public static final String tabla_amortizacion_alemana="ALEMANA";
    public static final String tabla_amortizacion_francesa="FRANCESA";
    public static final String periodo_mensual="MENSUAL";
    public static final String periodo_trimestral="TRIMESTRAL";
    
    public static String vacio="";
    public static String espacio=" ";
    
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
    
    public static String error_generico="ERROR INTERNO DE SERVIDOR";
    public static String error_entidad_existente="ERROR ENTIDAD YA EXISTE";
    public static String error_entidad_no_existente="ERROR ENTIDAD NO EXISTENTE";
    public static String error_codigo_no_existente="ERROR CODIGO INTERNO NO EXISTENTE";
    public static String error_sesion_invalida="ERROR SESION INVALIDA";
    public static String error_datos_invalidos="ERROR DATOS INVALIDOS";
    public static String error_identificacion_invalida="ERROR IDENTIFICACION INVALIDA";
    public static String error_secuencia_no_existente="ERROR SECUENCIA NO EXISTENTE";
    public static String error_cliente_no_existente="ERROR CLIENTE NO EXISTENTE";
    
    //CODIGO PARA CREAR
    public static final String tipo="CREAR";
    
    //TABLAS
    //CLIENTE
    public static final String tabla_modelo="modelo";
    public static final String tabla_auxiliar="auxiliar";
    public static final String tabla_calificacion_cliente="calificacion_cliente";
    public static final String tabla_celular="celular";
    public static final String tabla_celular_auxiliar="celular_auxiliar";
    public static final String tabla_cliente="cliente";
    public static final String tabla_correo="correo";
    public static final String tabla_correo_auxiliar="correo_auxiliar";
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
    public static final String tabla_telefono_auxiliar="telefono_auxiliar";
    public static final String tabla_tipo_contribuyente="tipo_contribuyente";
    public static final String tabla_tipo_pago="tipo_pago";
    
    //COMPRA
    public static final String tabla_factura_compra="factura_compra";
    public static final String tabla_retencion_compra="retencion_compra";
    
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
    public static final String tabla_establecimiento="establecimiento";
    public static final String tabla_perfil="perfil";
    public static final String tabla_permiso="permiso";
    public static final String tabla_punto_venta="punto_venta";
    public static final String tabla_sesion="sesion";
    public static final String tabla_usuario="usuario";
	
    //CONTABILIDAD
    public static final String tabla_afectacion_contable="afectacion_contable";
    
    //NOMBRES
    //CLIENTE
    public static final String modelo="MODELO";
    public static final String auxiliar="AUXILIAR";
    public static final String calificacion_cliente="CALIFICACION CLIENTE";
    public static final String celular="CELULAR";
    public static final String celular_auxiliar="CELULAR AUXILIAR";
    public static final String cliente="CLIENTE";
    public static final String correo="CORREO";
    public static final String correo_auxiliar="CORREO AUXILIAR";
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
    public static final String telefono_auxiliar="TELEFONO AUXILIAR";
    public static final String tipo_contribuyente="TIPO CONTRIBUYENTE";
    public static final String tipo_pago="TIPO PAGO";
    
    //COMPRA
    public static final String factura_compra="FACTURA COMPRA";
    public static final String retencion_compra="RETENCION COMPRA";
    
    //COMPROBANTE
    public static final String factura_detalle="DETALLE FACTURA";
    public static final String egreso="EGRESO";
    public static final String factura="FACTURA";
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
    public static final String presentacion_producto="PRESENTACION PRODUCTO";
    public static final String producto="PRODUCTO";
    public static final String proveedor="PROVEEDOR";
    public static final String segmento="SEGMENTO";
    public static final String sub_grupo_producto="SUB GRUPO PRODUCTO";
    public static final String sub_linea_producto="SUB LINEA PRODUCTO";
    public static final String equivalencia_medida="TABLA EQUIVALENCIA MEDIDA";
    public static final String tipo_gasto="TIPO GASTO";
    
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
    public static final String punto_venta="PUNTO VENTA";
    public static final String sesion="SESION";
    public static final String usuario="USUARIO";
	
    //CONTABILIDAD
    public static final String afectacion_contable="AFECTACION CONTABLE";
    
    //CONSTANTE PARA REGISTRO DE TABLA EN PAGINACIÓN
    public static final int size=20;
    public static final String order="id";
    
    //CONSTANTE FACTURACION ELECTRONICA
    public static final String ambienteFE="1";
    public static final String tipoEmisionFE="1";
}
