package com.proyecto.sicecuador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import com.proyecto.sicecuador.repositorios.interf.configuracion.IParametroRepository;
@Component
public class Util {
	
	private static IParametroRepository parametroRep;
	
	@Autowired
	private IParametroRepository autowiredParametroRep;
	
	private static EntityManager em;
	
	@Autowired
	private EntityManager autowiredEm;
	
	@PostConstruct
	private void init() {
		parametroRep = this.autowiredParametroRep;
		em=this.autowiredEm;
	}
	
	
	
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
            File archivo= archivo_convertir(archivo_temporal);
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

    public static Optional<String> conteo(String tabla) {
        String sql = String.format("SELECT count(*) FROM %s;", tabla);
        Query query= em.createNativeQuery(sql);
        Object conteo=query.getSingleResult();
        return Optional.of(String.valueOf(conteo));
    }
    
    public static Optional<String> generarCodigo(String tabla){
    	try {
    		Optional<Parametro> parametro = parametroRep.findByTablaAndTipo(tabla, Constantes.tipo);
        	Optional<String> conteo= conteo(tabla);
        	if (parametro.isPresent() && conteo.isPresent()) {
            	String rellenoConteo = String.format("%06d" , Long.parseLong(conteo.get()));
                Date fecha = new Date();
                String año = String.valueOf(fecha.getYear());
                String mes = String.valueOf(fecha.getMonth());
                return Optional.of(parametro.get().getAbreviatura() + año + mes + rellenoConteo);
            }
        	return Optional.ofNullable(null);
    	}catch(Exception e) {
    		return Optional.ofNullable(null);
    	}
    }
}
