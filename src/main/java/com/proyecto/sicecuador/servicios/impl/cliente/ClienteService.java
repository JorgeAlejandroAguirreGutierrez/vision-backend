package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.modelos.cliente.Cliente;
import com.proyecto.sicecuador.modelos.cliente.TipoContribuyente;
import com.proyecto.sicecuador.repositorios.interf.cliente.IClienteRepository;
import com.proyecto.sicecuador.repositorios.interf.cliente.ITipoContribuyenteRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IClienteService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository rep;
    @Autowired
    private ITipoContribuyenteRepository rep_tipo_contribuyente;

    /**
     *
     * @param cliente
     * @return
     */
    @Override
    public List<Cliente> consultarIdentificacion(Cliente cliente) {
        return  rep.findAll(new Specification<Cliente>() {
            @Override
            public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (cliente.getIdentificacion()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("identificacion"), "%"+cliente.getIdentificacion()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public List<Cliente>  consultarRazonSocial(Cliente cliente) {
        return  rep.findAll(new Specification<Cliente>() {
            @Override
            public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (cliente.getRazon_social()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("razon_social"), "%"+cliente.getRazon_social()+"%")));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public Optional<Cliente> obtenerIdentificacion(Cliente cliente) {
        return  rep.findOne(new Specification<Cliente>() {
            @Override
            public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (cliente.getIdentificacion()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("identificacion"), cliente.getIdentificacion())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public Optional<Cliente>  obtenerRazonSocial(Cliente cliente) {
        return  rep.findOne(new Specification<Cliente>() {
            @Override
            public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (cliente.getRazon_social()!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("razon_social"), cliente.getRazon_social())));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    @Override
    public Optional<Cliente> validarIdentificacion(Cliente _cliente) {
        String identificacion = _cliente.getIdentificacion();
        if (identificacion!= null) {
            String tipo = null;
            TipoContribuyente tipo_contribuyente=null;
            if (identificacion.length() == 10 && Integer.parseInt((identificacion.substring(2,3))) != 6 && Integer.parseInt((identificacion.substring(2,3))) != 9) {
                tipo = "C";
                boolean bandera = verificarCedula(identificacion);
                if (bandera) {
                    tipo_contribuyente= rep_tipo_contribuyente.findByTipoAndSubtipo("NATURAL", "NATURAL");
                    Cliente cliente=new Cliente(tipo,tipo_contribuyente);
                    return Optional.of(cliente);
                } else {
                    return Optional.empty();
                }
            } else if (identificacion == "9999999999999") {
                tipo = "CF";
                tipo_contribuyente=rep_tipo_contribuyente.findByTipoAndSubtipo("NATURAL", "");
                Cliente cliente=new Cliente(tipo,tipo_contribuyente);
                return Optional.of(cliente);
            } else if (identificacion.length() == 13 && Integer.parseInt((identificacion.substring(2,3))) == 6) {
                boolean bandera = verificarSociedadesPublicas(identificacion);
                if (bandera) {
                    tipo = "R";
                    tipo_contribuyente=rep_tipo_contribuyente.findByTipoAndSubtipo("JURIDICA", "PUBLICA");
                    Cliente cliente=new Cliente(tipo,tipo_contribuyente);
                    return Optional.of(cliente);
                } else {
                    return Optional.empty();
                }
            } else if (identificacion.length() == 13 && Integer.parseInt((identificacion.substring(2,3))) == 9) {
                boolean bandera = verificarSociedadesPrivadas(identificacion);
                if (bandera) {
                    tipo = "R";
                    tipo_contribuyente=rep_tipo_contribuyente.findByTipoAndSubtipo("JURIDICA","PRIVADA");
                    Cliente cliente=new Cliente(tipo,tipo_contribuyente);
                    return Optional.of(cliente);
                } else {
                    return Optional.empty();
                }
            } else if (identificacion.length() == 13 && (Integer.parseInt(identificacion.substring(2,3)) != 6 || Integer.parseInt(identificacion.substring(2,3)) != 9)) {
                boolean bandera=verificarCedula(identificacion);
                if (bandera) {
                    tipo = "R";
                    tipo_contribuyente=rep_tipo_contribuyente.findByTipoAndSubtipo("NATURAL", "NATURAL");
                    Cliente cliente=new Cliente(tipo,tipo_contribuyente);
                    return Optional.of(cliente);
                } else {
                    return Optional.empty();
                }
            }
            else if (identificacion.length() == 13) {
                boolean bandera = verificarPersonaNatural(identificacion);
                if (bandera) {
                    tipo = "R";
                    tipo_contribuyente= rep_tipo_contribuyente.findByTipoAndSubtipo("JURIDICA","PUBLICA");
                    Cliente cliente=new Cliente(tipo,tipo_contribuyente);
                    return Optional.of(cliente);
                } else {
                    return Optional.empty();
                }
            } else if (identificacion.length() == 7) {
                boolean bandera = verificarPlaca(identificacion);
                if (bandera) {
                    tipo = "PL";
                    Cliente cliente=new Cliente(tipo,null);
                    return Optional.of(cliente);
                } else {
                    return Optional.empty();
                }
            } else if (identificacion.length() == 6) {
                boolean bandera = verificarPlacaMoto(identificacion);
                if (bandera) {
                    tipo = "PL";
                    Cliente cliente=new Cliente(tipo,null);
                    return Optional.of(cliente);
                } else {
                    return Optional.empty();
                }
            }
            else if (identificacion.length() >=8) {
                boolean bandera = verificarPasaporte(identificacion);
                if (bandera) {
                    tipo = "E";
                    Cliente cliente=new Cliente(tipo,null);
                    return Optional.of(cliente);
                } else {
                    return Optional.empty();
                }
            } else if (identificacion=="9999999999999") {
                return Optional.empty();
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean verificarPersonaNatural(String identificacion) {
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

    @Override
    public boolean verificarCedula(String identificacion) {
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

    @Override
    public boolean verificarSociedadesPublicas(String identificacion) {
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

    @Override
    public boolean verificarSociedadesPrivadas(String identificacion) {
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

    @Override
    public boolean verificarPlaca(String identificacion) {
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

    @Override
    public boolean verificarPlacaMoto(String identificacion) {
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

    @Override
    public boolean verificarPasaporte(String identificacion) {
        String [] arreglo_letras = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O","P","Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        List<String> letras = Arrays.asList(arreglo_letras);
        for (int i=0; i<identificacion.length(); i++){
            if (letras.contains(identificacion.substring(i,i+1))!=false){
                return true;
            }
        }
        return false;
    }

    @Override
    public Cliente crear(Cliente cliente) {
        return rep.save(cliente);
    }

    @Override
    public Cliente actualizar(Cliente cliente) {
        return rep.save(cliente);
    }

    @Override
    public Cliente eliminar(Cliente cliente) {
        rep.deleteById(cliente.getId());
        return cliente;
    }

    @Override
    public Optional<Cliente> obtener(Cliente cliente) {
        return rep.findById(cliente.getId());
    }

    @Override
    public List<Cliente> consultar() {
        return rep.findAll();
    }

    @Override
    public List<Cliente> importar(MultipartFile reapExcelDataFile) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);
            for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
                Cliente cliente = new Cliente();
                XSSFRow row = worksheet.getRow(i);
                cliente.setRazon_social(row.getCell(0).getStringCellValue());
                cliente.setTipo_identificacion(row.getCell(1).getStringCellValue());
                cliente.setIdentificacion(row.getCell(1).getStringCellValue());
                cliente.setEspecial(row.getCell(1).getBooleanCellValue());

                cliente.getGenero().setAbreviatura(row.getCell(1).getStringCellValue());
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Cliente> exportar() {
        return null;
    }

}
