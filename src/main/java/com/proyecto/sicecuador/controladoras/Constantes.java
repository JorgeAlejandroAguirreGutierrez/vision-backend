package com.proyecto.sicecuador.controladoras;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Map<String, String> modelos=new HashMap<String, String>() {{
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
    public static File archivo_convertir(MultipartFile archivo ) throws IOException
    {
        File archivo_convertir = new File( archivo.getOriginalFilename() );
        FileOutputStream archivo_salida = new FileOutputStream( archivo_convertir );
        archivo_salida.write( archivo.getBytes() );
        archivo_salida.close();
        return archivo_convertir;
    }

    public static List<List<String>> leer_importar(MultipartFile archivo_temporal, int pagina){
        List<List<String>> info=new ArrayList<>();
        InputStream archivo_excel=null;
        try {
            File archivo= Constantes.archivo_convertir(archivo_temporal);
            archivo_excel = new FileInputStream(archivo);
            // Representación del más alto nivel de la hoja excel.
            XSSFWorkbook libro = new XSSFWorkbook(archivo_excel);
            // Elegimos la hoja que se pasa por parámetro.
            XSSFSheet hoja = libro.getSheetAt(pagina);
            // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
            XSSFRow fila;
            // Inicializo el objeto que leerá el valor de la celda
            XSSFCell cell;
            // Obtengo el número de filas ocupadas en la hoja
            int filas = hoja.getLastRowNum();
            // Obtengo el número de columnas ocupadas en la hoja
            int columnas = 0;
            // Cadena que usamos para almacenar la lectura de la celda
            String valor_celda;
            // Para este ejemplo vamos a recorrer las filas obteniendo los datos que queremos
            for (int registro = 1; registro < filas; registro++) {
                List<String>datos=new ArrayList<>();
                fila = hoja.getRow(registro);
                if (fila == null){
                    break;
                }else {
                    System.out.print("Row: " + registro + " -> ");
                    for (int columna = 0; columna < (columnas = fila.getLastCellNum()); columna++) {
                        /*
                            tenemos estos tipos de celda:
                            CELL_TYPE_BLANK, CELL_TYPE_NUMERIC, CELL_TYPE_BLANK, CELL_TYPE_FORMULA, CELL_TYPE_BOOLEAN, CELL_TYPE_ERROR
                        */
                        valor_celda = fila.getCell(columna) == null ? "" :
                                (fila.getCell(columna).getCellType() == CellType.STRING) ? fila.getCell(columna).getStringCellValue() :
                                        (fila.getCell(columna).getCellType() == CellType.NUMERIC) ? "" + fila.getCell(columna).getNumericCellValue() :
                                                (fila.getCell(columna).getCellType() == CellType.BOOLEAN) ? "" + fila.getCell(columna).getBooleanCellValue() :
                                                        (fila.getCell(columna).getCellType() == CellType.BLANK) ? null :
                                                                (fila.getCell(columna).getCellType() == CellType.FORMULA) ? "FORMULA" :
                                                                        (fila.getCell(columna).getCellType() == CellType.ERROR) ? "ERROR" : "";
                        System.out.print("[Column " + columna + ": " + valor_celda + "] ");
                        datos.add(valor_celda);
                    }
                    if(validar_datos_importacion(datos)){
                        info.add(datos);
                    }

                }
            }
            return info;
        } catch (FileNotFoundException fileNotFoundException) {
            return null;
        } catch (IOException e) {
            return null;
        } finally {
            try {
                archivo_excel.close();
            } catch (IOException ex) {
                return null;
            }
        }
    }

    private static boolean validar_datos_importacion(List<String>datos){
        for (int i=0; i<datos.size(); i++){
            if (datos.get(i)!= null){
                return true;
            }
        }
        return false;
    } 
    
}
