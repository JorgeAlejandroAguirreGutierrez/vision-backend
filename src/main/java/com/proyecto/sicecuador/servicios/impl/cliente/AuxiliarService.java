package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.Direccion;
import com.proyecto.sicecuador.otros.Util;
import com.proyecto.sicecuador.repositorios.interf.cliente.IAuxiliarRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IAuxiliarService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PostPersist;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AuxiliarService implements IAuxiliarService {
    @Autowired
    private IAuxiliarRepository rep;
    @Override
    public Auxiliar crear(Auxiliar auxiliar) {
        return rep.save(auxiliar);
    }

    @Override
    public Auxiliar actualizar(Auxiliar auxiliar) {
        return rep.save(auxiliar);
    }

    @Override
    public Auxiliar eliminar(Auxiliar auxiliar) {
        rep.deleteById(auxiliar.getId());
        return auxiliar;
    }

    @Override
    public Optional<Auxiliar> obtener(Auxiliar auxiliar) {
        return rep.findById(auxiliar.getId());
    }

    @Override
    public List<Auxiliar> consultar() {
        return rep.findAll();
    }

    @Override
    public List<Auxiliar> consultarRazonSocial(Auxiliar auxiliar) {
        return  rep.findAll(new Specification<Auxiliar>() {
            @Override
            public Predicate toPredicate(Root<Auxiliar> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (auxiliar.getRazon_social()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("razon_social"), "%"+auxiliar.getRazon_social()+"%")));
                }
                if (auxiliar.getCliente().getId()!=0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cliente").get("id"), auxiliar.getCliente().getId())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });

    }
    @Override
    public List<Auxiliar> consultarClienteID(Auxiliar auxiliar) {
        return  rep.findAll(new Specification<Auxiliar>() {
            @Override
            public Predicate toPredicate(Root<Auxiliar> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (auxiliar.getCliente().getId()!=0) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("cliente").get("id"), auxiliar.getCliente().getId())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public boolean importar(MultipartFile archivo_temporal) {
        List<Auxiliar> auxiliares=new ArrayList<>();
        List<String>info=new ArrayList<>();
        InputStream excelStream = null;
        try {
            File archivo=null;
            archivo_temporal.transferTo(archivo);
            FileInputStream archivo_excel = new FileInputStream(archivo);
            // Representación del más alto nivel de la hoja excel.
            HSSFWorkbook libro = new HSSFWorkbook(excelStream);
            // Elegimos la hoja que se pasa por parámetro.
            HSSFSheet hoja = libro.getSheetAt(0);
            // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
            HSSFRow fila;
            // Inicializo el objeto que leerá el valor de la celda
            HSSFCell cell;
            // Obtengo el número de filas ocupadas en la hoja
            int filas = hoja.getLastRowNum();
            // Obtengo el número de columnas ocupadas en la hoja
            int columnas = 0;
            // Cadena que usamos para almacenar la lectura de la celda
            String valor_celda;
            // Para este ejemplo vamos a recorrer las filas obteniendo los datos que queremos
            for (int registro = 0; registro < filas; registro++) {
                fila = hoja.getRow(registro);
                if (fila == null){
                    break;
                }else{
                    System.out.print("Row: " + registro + " -> ");
                    for (int columna = 0; columna < (columnas = fila.getLastCellNum()); columna++) {
                        /*
                            tenemos estos tipos de celda:
                            CELL_TYPE_BLANK, CELL_TYPE_NUMERIC, CELL_TYPE_BLANK, CELL_TYPE_FORMULA, CELL_TYPE_BOOLEAN, CELL_TYPE_ERROR
                        */
                        valor_celda = fila.getCell(columna) == null?"":
                                (fila.getCell(columna).getCellType() == CellType.STRING)?fila.getCell(columna).getStringCellValue():
                                        (fila.getCell(columna).getCellType() == CellType.NUMERIC)?"" + fila.getCell(columna).getNumericCellValue():
                                                (fila.getCell(columna).getCellType() == CellType.BOOLEAN)?"" + fila.getCell(columna).getBooleanCellValue():
                                                        (fila.getCell(columna).getCellType() == CellType.BLANK)?"BLANK":
                                                                (fila.getCell(columna).getCellType() == CellType.FORMULA)?"FORMULA":
                                                                        (fila.getCell(columna).getCellType() == CellType.ERROR)?"ERROR":"";
                        System.out.print("[Column " + columna + ": " + valor_celda + "] ");
                        info.add(valor_celda);
                    }
                    Auxiliar auxiliar=new Auxiliar(null, info.get(0), info.get(1).equals("1")?true:false, info.get(1).equals("1")?true:false, new Cliente(info.get(3)), new Direccion(4));
                    auxiliares.add(auxiliar);
                    System.out.println();
                }
                rep.saveAll(auxiliares);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                excelStream.close();
            } catch (IOException ex) {
                return false;
            }
        }
        return false;
    }
}
