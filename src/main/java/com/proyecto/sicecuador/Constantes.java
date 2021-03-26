package com.proyecto.sicecuador;

public class Constantes {
    public static final String mensaje_consultar_exitoso="Exito en la consulta";
    public static final String mensaje_obtener_exitoso="Exito en la consulta";
    public static final String mensaje_crear_exitoso="Exito al procesar";
    public static final String mensaje_actualizar_exitoso="Exito al actualizar";
    public static final String mensaje_eliminar_exitoso="Exito al eliminar";
    public static final String mensaje_importacion_exitoso="Exito al importar";
    public static final String mensaje_exportacion_exitoso="Exito al exportar";

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
    public static String error_registro_existente="REGISTRO EXISTENTE";
    public static String error_registro_no_existente="REGISTRO NO EXISTENTE";
    
    public static String error_codigo_generico="4000";
    public static String error_codigo_modelo_existente="4001";
    public static String error_codigo_modelo_no_existente="4002";
    public static String error_codigo_codigo_interno_no_existente="4003";
    public static String error_codigo_sesion_invalida="4004";
    public static String error_codigo_datos_invalidos="4005";
    
    public static String error_generico="ERROR INTERNO DE SERVIDOR";
    public static String error_modelo_existente="ERROR ENTIDAD YA EXISTE";
    public static String error_modelo_no_existente="ERROR ENTIDAD NO EXISTENTE";
    public static String error_codigo_interno_no_existente="ERROR CODIGO INTERNO NO EXISTENTE";
    public static String error_sesion_invalida="ERROR SESION INVALIDA";
    public static String error_datos_invalidos="ERROR DATOS INVALIDOS";
    
    //CODIGO PARA CREAR
    public static final String tipo="CREAR";
    
    //TABLAS
    
    //CLIENTE
    public static final String tabla_modelo="modelo";
    public static final String tabla_auxiliar="auxiliar";
    public static final String tabla_categoria_cliente="categoria_cliente";
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
    
    //ENTREGA
    public static final String tabla_guia_remision="guia_remision";
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
    public static final String tabla_tipo_producto="tipo_producto";
    
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
	
}
