package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.Auxiliar;
import com.proyecto.sicecuador.modelos.cliente.Cliente;
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
    public boolean importar(MultipartFile archivo) {
        InputStream excelStream = null;
        try {
            File archivo_temporal=null;
            archivo.transferTo(archivo_temporal);
            excelStream = new FileInputStream(archivo_temporal);
            // Representación del más alto nivel de la hoja excel.
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(excelStream);
            // Elegimos la hoja que se pasa por parámetro.
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
            HSSFRow hssfRow;
            // Inicializo el objeto que leerá el valor de la celda
            HSSFCell cell;
            // Obtengo el número de filas ocupadas en la hoja
            int rows = hssfSheet.getLastRowNum();
            // Obtengo el número de columnas ocupadas en la hoja
            int cols = 0;
            // Cadena que usamos para almacenar la lectura de la celda
            String cellValue;
            // Para este ejemplo vamos a recorrer las filas obteniendo los datos que queremos
            for (int r = 0; r < rows; r++) {
                hssfRow = hssfSheet.getRow(r);
                if (hssfRow == null){
                    break;
                }else{
                    System.out.print("Row: " + r + " -> ");
                    for (int c = 0; c < (cols = hssfRow.getLastCellNum()); c++) {
                        /*
                            tenemos estos tipos de celda:
                            CELL_TYPE_BLANK, CELL_TYPE_NUMERIC, CELL_TYPE_BLANK, CELL_TYPE_FORMULA, CELL_TYPE_BOOLEAN, CELL_TYPE_ERROR
                        */
                        cellValue = hssfRow.getCell(c) == null?"":
                                (hssfRow.getCell(c).getCellType() == CellType.STRING)?hssfRow.getCell(c).getStringCellValue():
                                        (hssfRow.getCell(c).getCellType() == CellType.NUMERIC)?"" + hssfRow.getCell(c).getNumericCellValue():
                                                (hssfRow.getCell(c).getCellType() == CellType.BOOLEAN)?"" + hssfRow.getCell(c).getBooleanCellValue():
                                                        (hssfRow.getCell(c).getCellType() == CellType.BLANK)?"BLANK":
                                                                (hssfRow.getCell(c).getCellType() == CellType.FORMULA)?"FORMULA":
                                                                        (hssfRow.getCell(c).getCellType() == CellType.ERROR)?"ERROR":"";
                        System.out.print("[Column " + c + ": " + cellValue + "] ");
                    }
                    System.out.println();
                }
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
