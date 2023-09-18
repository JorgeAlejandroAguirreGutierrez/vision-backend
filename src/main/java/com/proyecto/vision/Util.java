package com.proyecto.vision;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.vision.modelos.configuracion.MenuOpcion;
import com.proyecto.vision.repositorios.configuracion.IMenuOpcionRepository;

@Component
public class Util {
    private static IMenuOpcionRepository menuOpcionRep;
	@Autowired
	private IMenuOpcionRepository autowiredMenuOpcionRep;
	private static EntityManager em;
	@Autowired
	private EntityManager autowiredEm;
	@PostConstruct
	private void init() {
        menuOpcionRep = this.autowiredMenuOpcionRep;
		em=this.autowiredEm;
	}

    public static boolean verificarCedula(String identificacion) {
        int numv = 10;
        int div = 11;
        int []coeficientes = null;
        if (Integer.parseInt(identificacion.substring(2,3)) < 6) {
            coeficientes = new int[]{2, 1, 2, 1, 2, 1, 2, 1, 2};
            div = 10;
        } else {
            if (Integer.parseInt(identificacion.substring(2,3)) == 6) {
                coeficientes = new int[]{3, 2, 7, 6, 5, 4, 3, 2};
                numv = 9;
            } else {
                coeficientes = new int[]{4, 3, 2, 7, 6, 5, 4, 3, 2};
            }
        }
        int total = 0;
        int provincias = 24;
        int calculo = 0;
        if ((Integer.parseInt(identificacion.substring(2,3)) <= 6 || Integer.parseInt(identificacion.substring(2,3)) == 9)
                && (Integer.parseInt(identificacion.substring(0,1) + identificacion.substring(1,2)) <= provincias)) {
            for (int i = 0; i < numv - 1; i++) {
                calculo = Integer.parseInt(identificacion.substring(i,i+1)) * coeficientes[i];
                if (div == 10) {
                    total += calculo > 9 ? calculo - 9 : calculo;
                } else {
                    total += calculo;
                }
            }
            return (div - (total % div)) >= 10 ? 0 == Integer.parseInt(identificacion.substring(numv-1,numv)) : (div - (total % div)) == Integer.parseInt(identificacion.substring(numv - 1, numv));
        } else {
            return false;
        }
    }

    public static boolean verificarPasaporte(String identificacion) {
        String [] arreglo_letras = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O","P","Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        List<String> letras = Arrays.asList(arreglo_letras);
        for (int i=0; i<identificacion.length(); i++){
            if (letras.contains(identificacion.substring(i,i+1))!=false){
                return true;
            }
        }
        return false;
    }

    public static boolean verificarPersonaNatural(String identificacion) {
        try {
            int provincia = Integer.parseInt(identificacion.substring(0,1) + identificacion.substring(1,2));
            int personaNatural = Integer.parseInt(identificacion.substring(2, 3));
            if (provincia >= 1 && provincia <= 24 && personaNatural >= 0 && personaNatural < 6) {
                return identificacion.substring(10,13) == "001" && verificarCedula(identificacion.substring(0,10));
            }
            return false;
        } catch (Exception $e) {
            return false;
        }
    }
    public static boolean verificarSociedadesPublicas(String identificacion) {
        int modulo = 11;
        int total = 0;
        int coeficientes[] = new int[]{3, 2, 7, 6, 5, 4, 3, 2};
        String numeroProvincia = identificacion.substring(0,1) + identificacion.substring(1,2);
        String establecimiento = identificacion.substring(9,13);
        if (Integer.parseInt(numeroProvincia) >= 1 && Integer.parseInt(numeroProvincia) <= 24 && establecimiento.equals("0001")) {
            int digitoVerificador = Integer.parseInt(identificacion.substring(8,9));
            for (int i = 0; i < coeficientes.length; i++) {
                total = total + (coeficientes[i] * Integer.parseInt(identificacion.substring(i,i+1)));
            }
            int digitoVerificadorObtenido = modulo - (total % modulo);
            return digitoVerificadorObtenido == digitoVerificador;
        }
        return false;
    }

    public static boolean verificarSociedadesPrivadas(String identificacion) {
        int modulo = 11;
        int total = 0;
        int coeficientes[] = new int []{4, 3, 2, 7, 6, 5, 4, 3, 2};
        String numeroProvincia = identificacion.substring(0,1) + identificacion.substring(1,2);
        String establecimiento = identificacion.substring(10,13);
        if (Integer.parseInt(numeroProvincia) >= 1 && Integer.parseInt(numeroProvincia) <= 24 && establecimiento.equals("001")) {
            int digitoVerificador = Integer.parseInt(identificacion.substring(9,10));
            for (int i = 0; i < coeficientes.length; i++) {
                total = total + (coeficientes[i] * Integer.parseInt(identificacion.substring(i,i+1)));
            }
            int digitoVerificadorObtenido = (total % modulo) == 0 ? 0 : modulo - (total % modulo);
            return digitoVerificadorObtenido == digitoVerificador;
        }
        return false;
    }

    public static boolean verificarPlaca(String identificacion) {
        String arreglo_letras[] = new String[]{"A", "B", "U", "C", "H", "X", "O", "E", "W", "G", "I", "L", "R", "M", "V", "N", "Q", "S", "P", "Y", "J", "K", "T", "Z"};
        List<String> letras = Arrays.asList(arreglo_letras);
        String primera_letra = identificacion.substring(0,1);
        boolean bandera1 = letras.contains(primera_letra);
        String segunda_letra = identificacion.substring(1,2);
        boolean bandera2 = letras.contains(segunda_letra);
        String tercera_letra = identificacion.substring(2,3);
        boolean bandera3 = letras.contains(tercera_letra);
        if (bandera1 !=false && bandera2 !=false && bandera3 !=false){
            return true;
        } else {
            return false;
        }
    }
    public static boolean verificarPlacaMoto(String identificacion) {
        String []arreglo_letras = new String[]{"A", "B", "U", "C", "H", "X", "O", "E", "W", "G", "I", "L", "R", "M", "V", "N", "Q", "S", "P", "Y", "J", "K", "T", "Z"};
        List<String> letras = Arrays.asList(arreglo_letras);
        String primera_letra = identificacion.substring(0,1);
        String segunda_letra = identificacion.substring(1,2);
        String sexta_letra = identificacion.substring(4,5);
        boolean bandera1=letras.contains(primera_letra);
        boolean bandera2=letras.contains(segunda_letra);
        boolean bandera3=letras.contains(sexta_letra);
        return bandera1!=false && bandera2!=false && bandera3!=false;
    }
	public static File archivoConvertir(MultipartFile archivo ) throws IOException
    {
        File archivo_convertir = new File( archivo.getOriginalFilename() );
        FileOutputStream archivo_salida = new FileOutputStream( archivo_convertir );
        archivo_salida.write( archivo.getBytes() );
        archivo_salida.close();
        return archivo_convertir;
    }

    public static List<List<String>> leerImportar(MultipartFile archivo_temporal, int pagina){
        List<List<String>> info=new ArrayList<>();
        InputStream archivo_excel=null;
        try {
            File archivo= archivoConvertir(archivo_temporal);
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
                    if(validar_datosImportacion(datos)){
                        info.add(datos);
                    }

                }
            }
            libro.close();
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

    private static boolean validar_datosImportacion(List<String>datos){
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

    public static Optional<String> conteoPorEmpresa(String tabla, long empresaId) {
        String sql = String.format("SELECT count(*) FROM %s WHERE empresa_id = %s;", tabla, empresaId);
        Query query= em.createNativeQuery(sql);
        Object conteo=query.getSingleResult();
        return Optional.of(String.valueOf(conteo));
    }

    public static Optional<String> generarCodigo(String tabla){
    	try {
    		Optional<MenuOpcion> menuOpcion = menuOpcionRep.findByTablaAndOperacion(tabla, Constantes.operacionCrear, Constantes.estadoActivo);
        	Optional<String> conteo = conteo(tabla);
        	if (menuOpcion.isPresent() && conteo.isPresent()) {
            	String rellenoConteo = String.format("%06d" , Long.parseLong(conteo.get())+1);
                Date fecha = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha);
                String año = calendar.get(Calendar.YEAR)+"";
                int mesC=calendar.get(Calendar.MONTH)+1;
                String mes="";
                if(mesC<10) {
                	mes= "0"+mesC;
                }
                return Optional.of(menuOpcion.get().getAbreviatura() + año + mes + rellenoConteo);
            }
        	return Optional.ofNullable(null);
    	}catch(Exception e) {
    		return Optional.ofNullable(null);
    	}
    }

    public static Optional<String> generarCodigoPorEmpresa(Date fecha, String tabla, long empresaId){
        try {
            Optional<MenuOpcion> menuOpcion = menuOpcionRep.findByTablaAndOperacion(tabla, Constantes.operacionCrear, Constantes.estadoActivo);
            Optional<String> conteo= conteoPorEmpresa(tabla, empresaId);
            if (menuOpcion.isPresent() && conteo.isPresent()) {
                String rellenoConteo = String.format("%06d" , Long.parseLong(conteo.get())+1);
                String empresa = String.format("%02d" , empresaId);
                if(fecha == null){
                    fecha = new Date();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha);
                String año = calendar.get(Calendar.YEAR)+"";
                año = año.substring(2,4);
                int mesC=calendar.get(Calendar.MONTH)+1;
                String mes = String.format("%02d" , mesC);
                return Optional.of(menuOpcion.get().getAbreviatura() + empresa + año + mes + rellenoConteo);
            }
            return Optional.ofNullable(null);
        }catch(Exception e) {
            return Optional.ofNullable(null);
        }
    }

    public static String generarSecuencial(long numero){
        String rellenoConteo = String.format("%09d" , numero);
        return rellenoConteo;
    }
    
    public static String generarCodigoNumerico(long numero){
        String rellenoConteo = String.format("%08d" , numero);
        return rellenoConteo;
    }
    
    public static Optional<String> generarGuiaNumero(String tabla){
    	try {
        	Optional<String> conteo= conteo(tabla);
        	if (conteo.isPresent()) {
            	String rellenoConteo = String.format("%06d" , Long.parseLong(conteo.get())+1);
            	String guiaNumero="GUIA"+rellenoConteo;
                return Optional.of(guiaNumero);
            }
        	return Optional.ofNullable(null);
    	}catch(Exception e) {
    		return Optional.ofNullable(null);
    	}
    }
    
    public static String soapFacturacionEletronica(String request){
    	return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ec=\"http://ec.gob.sri.ws.recepcion\">\r\n"
    			+ "    <soapenv:Header />\r\n"
    			+ "    <soapenv:Body>\r\n"
    			+ "        <ec:validarComprobante>\r\n"
    			+ "            <xml>"+ request + "</xml>"
    			+ "        </ec:validarComprobante>\r\n"
    			+ "    </soapenv:Body>\r\n"
    			+ "</soapenv:Envelope>";
    }
    
    public static String soapConsultaFacturacionEletronica(String request){
    	return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ec=\"http://ec.gob.sri.ws.autorizacion\">\r\n"
    			+ "   <soapenv:Header/>\r\n"
    			+ "   <soapenv:Body>\r\n"
    			+ "      <ec:autorizacionComprobante>\r\n"
    			+ "         <claveAccesoComprobante>" + request + "</claveAccesoComprobante>\r\n"
    			+ "      </ec:autorizacionComprobante>\r\n"
    			+ "   </soapenv:Body>\r\n"
    			+ "</soapenv:Envelope>";
    }
    
    public static JSONObject convertirXmlJson(String xml) {
    	JSONObject json = XML.toJSONObject(xml);
    	return json;
    }
}
