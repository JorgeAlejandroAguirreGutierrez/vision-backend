package com.proyecto.sicecuador.controladoras;

import java.util.HashMap;
import java.util.Map;

public class Constantes {
    public static final String mensaje_consultar_exitoso="Exito en la consulta";
    public static final String mensaje_obtener_exitoso="Exito en la consulta";
    public static final String mensaje_crear_exitoso="Exito al procesar";
    public static final String mensaje_actualizar_exitoso="Exito al actualizar";
    public static final String mensaje_eliminar_exitoso="Exito al eliminar";

    public static final String mensaje_consulta_fallido="Error en la consulta los objetos";
    public static final String mensaje_obtener_faliido="Error al obtener un objeto";
    public static final String mensaje_crear_fallido="Error al crear un objeto";
    public static final String mensaje_actualizar_fallido="Error al actualizar un objeto";
    public static final String mensaje_eliminar_fallido="Exito al eliminar un objeto";

    public static final String mensaje_validacion_not_blank=" No debe estar en blanco";
    public static final String mensaje_validacion_not_null=" Debe exitir";

    public Map<String, String> modelos=new HashMap<String, String>() {{
        //CLIENTE
        modelos.put("AUXILIAR", "Auxiliar");
        modelos.put("CATEGORIA_CLIENTE", "Categoria Cliente");
        modelos.put("CELULAR", "Celular");
        modelos.put("CELULAR_AUXILIAR", "Celular Auxiliar");
        modelos.put("CLIENTE", "Cliente");
        modelos.put("CORREO", "Correo");
        modelos.put("CORREO_AUXILIAR", "Correo Auxiliar");
        modelos.put("DIRECCION", "Direccion");
        modelos.put("ESTADO_CIVIL", "Estado Civil");
        modelos.put("FINANCIAMIENTO", "Financiamiento");
        modelos.put("FORMA_PAGO", "Forma de Pago");
        modelos.put("GENERO", "Genero");
        modelos.put("GRUPO_CLIENTE", "Grupo Cliente");
        modelos.put("ORIGEN_INGRESO", "Oirgen Ingreso");
        modelos.put("PLAZO_CREDITO", "Plazo de Credito");
        modelos.put("RETENCION_CLIENTE", "Retencion de Cliente");
        modelos.put("TELEFONO", "Telefono");
        modelos.put("TELEFONO_AUXILIAR", "Telefono de Auxiliar");
        modelos.put("TIPO_CONTRIBUYENTE", "Tipo de Contribuyente");
        modelos.put("TIPO_PAGO", "Tipo de Pago");
        //COMPRA
        modelos.put("FACTURA_COMPRA", "Factura de Compra");
        modelos.put("RETENCION_COMPRA", "Retencion de Compra");
        //COMPROBANTE
        modelos.put("EGRESO", "Egreso");
        modelos.put("FACTURA", "Factura");
        modelos.put("FACTURA_DETALLE", "Factura Detalle");
        modelos.put("PEDIDO", "Pedido");
        modelos.put("PROFORMA", "Proforma");
        modelos.put("TIPO_COMPROBANTE", "Tipo de Comprobante");
        //CONFIGURACION
        modelos.put("EMPRESA", "Empresa");
        modelos.put("PARAMETRO", "Parametro");
        modelos.put("TIPO_RETENCION", "Tipo de Retencion");
        modelos.put("UBICACION", "Ubicacion");
        //ENTREGA
        modelos.put("GUIA_REMISION", "Guia de Remision");
        modelos.put("TRANSPORTISTA", "Transportista");
        modelos.put("VEHICULO_TRANSPORTE", "Vehiculo de Transporte");
        //INVENTARIO
        modelos.put("BODEGA", "Bodega");
        modelos.put("CARACTERISTICA", "Caracteristica");
        modelos.put("GRUPO_PRODUCTO", "Grupo de Producto");
        modelos.put("IMPUESTO", "Impuesto");
        modelos.put("KARDEX", "Kardex");
        modelos.put("MEDIDA", "Medida");
        modelos.put("PRECIO", "Precio");
        modelos.put("PRODUCTO", "Producto");
        modelos.put("PROVEEDOR", "Proveedor");
        modelos.put("TIPO_PRODUCTO", "Tipo de Producto");
        //RECAUDACION
        modelos.put("AMORTIZACION", "Amortizacion");
        modelos.put("BANCO", "Banco");
        modelos.put("CHEQUE", "Cheque");
        modelos.put("COMPENSACION", "Compensacion");
        modelos.put("CREDITO", "Credito");
        modelos.put("CREDITO_AMORTIZACION", "Credito de Amortizacion");
        modelos.put("CUENTA_PROPIA", "Cuenta Propia");
        modelos.put("DEPOSITO", "Deposito");
        modelos.put("FRANQUICIA_TARJETA", "Franquicia de Tarjeta");
        modelos.put("MODELO_TABLA", "Modelo de Tabla");
        modelos.put("OPERADOR_TARJETA", "Operador de Tarjeta");
        modelos.put("RECAUDACION", "Recaudacion");
        modelos.put("RETENCION_VENTA", "Retencion de Venta");
        modelos.put("RETENCION_VENTA_DETALLE", "Retencion de Venta Detalle");
        modelos.put("TARJETA_CREDITO", "Tarjeta de Credito");
        modelos.put("TARJETA_DEBITO", "Tarjeta de Debito");
        modelos.put("TRANSFERENCIA", "Transferencia");
        //USUARIO
        modelos.put("ESTABLECIMIENTO", "Establecimiento");
        modelos.put("PERFIL", "Perfil");
        modelos.put("PERMISO", "Transferencia");
        modelos.put("PUNTO_VENTA", "Punto de Venta");
        modelos.put("SESION", "Sesion");
        modelos.put("USUARIO", "Usuario");
    }};

}
