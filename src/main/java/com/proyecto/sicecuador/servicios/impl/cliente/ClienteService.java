package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.CodigoNoExistenteException;
import com.proyecto.sicecuador.exception.IdentificacionInvalidaException;
import com.proyecto.sicecuador.exception.EntidadExistenteException;
import com.proyecto.sicecuador.exception.EntidadNoExistenteException;
import com.proyecto.sicecuador.modelos.cliente.*;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.repositorios.cliente.IClienteRepository;
import com.proyecto.sicecuador.repositorios.cliente.ITipoContribuyenteRepository;
import com.proyecto.sicecuador.repositorios.configuracion.ITipoIdentificacionRepository;
import com.proyecto.sicecuador.repositorios.configuracion.IUbicacionRepository;
import com.proyecto.sicecuador.servicios.interf.cliente.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository rep;
    @Autowired
    private ITipoContribuyenteRepository repTipoContribuyente;
    @Autowired
    private ITipoIdentificacionRepository repTipoIdentificacion;
    @Autowired
    private IUbicacionRepository repUbicacion;

    @PersistenceContext
    private EntityManager adm;

    /**
     *Busca los clientes por razon social e identificacion
     * @param cliente
     * @return
     */
    @Override
    public List<Cliente> buscar(Cliente cliente) {
        return  rep.findAll((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (cliente.getRazonSocial()!=null && !cliente.getRazonSocial().isEmpty()) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("razonSocial"), "%"+cliente.getRazonSocial()+"%")));
		    }
		    if (cliente.getIdentificacion()!=null && !cliente.getIdentificacion().isEmpty()) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("identificacion"), "%"+cliente.getIdentificacion()+"%")));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }

    @Override
    public Optional<Cliente> obtenerIdentificacion(Cliente cliente) {
        Optional<Cliente> respCliente= rep.findOne((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (cliente.getIdentificacion()!=null) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("identificacion"), cliente.getIdentificacion())));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
        if (respCliente.isPresent()) {
        	throw new EntidadExistenteException(Constantes.cliente);
        }
        return respCliente;
    }

    @Override
    public Optional<Cliente>  obtenerRazonSocial(Cliente cliente) {
        return  rep.findOne((root, criteriaQuery, criteriaBuilder) -> {
		    List<Predicate> predicates = new ArrayList<>();
		    if (cliente.getRazonSocial()!=null) {
		        predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("razonSocial"), cliente.getRazonSocial())));
		    }
		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		});
    }

    @Override
    public Optional<Cliente> validarIdentificacion(Cliente _cliente) {
        String identificacion = _cliente.getIdentificacion();
        if (identificacion!= null) {
            String tipo = null;
            TipoContribuyente tipo_contribuyente=null;
            TipoIdentificacion tipo_identificacion=null;
            if (identificacion.length() == 10 && Integer.parseInt((identificacion.substring(2,3))) != 6 && Integer.parseInt((identificacion.substring(2,3))) != 9) {
                tipo = Constantes.cedula_abreviatura;         
                boolean bandera = verificarCedula(identificacion);
                if (bandera) {
                    tipo_contribuyente= repTipoContribuyente.findByTipoAndSubtipo("NATURAL", "NATURAL");
//                    tipo_identificacion= repTipoIdentificacion.findById((long) 1);
                    tipo_identificacion= repTipoIdentificacion.findByCodigoSri("05");
                    Cliente cliente=new Cliente(tipo_identificacion,tipo_contribuyente);
                    return Optional.of(cliente);
                }
                throw new IdentificacionInvalidaException();
            } else if (identificacion.equals(Constantes.identificacion_consumidor_final)) {
                tipo = Constantes.consumidor_final_abreviatura;
                tipo_contribuyente=repTipoContribuyente.findByTipoAndSubtipo(Constantes.tipo_contribuyente_natural, Constantes.tipo_contribuyente_natural);
                tipo_identificacion= repTipoIdentificacion.findByCodigoSri("07");
                Cliente cliente=new Cliente(tipo_identificacion,tipo_contribuyente);
                return Optional.of(cliente);
            } else if (identificacion.length() == 13 && Integer.parseInt((identificacion.substring(2,3))) == 6) {
                boolean bandera = verificarSociedadesPublicas(identificacion);
                if (bandera) {
                    tipo = Constantes.ruc_abreviatura;
                    tipo_contribuyente=repTipoContribuyente.findByTipoAndSubtipo(Constantes.tipo_contribuyente_juridica, Constantes.tipo_contribuyente_publica);
                    tipo_identificacion= repTipoIdentificacion.findByCodigoSri("04");
                    Cliente cliente=new Cliente(tipo_identificacion,tipo_contribuyente);
                    return Optional.of(cliente);
                } 
            	throw new IdentificacionInvalidaException();
            	
            } else if (identificacion.length() == 13 && Integer.parseInt((identificacion.substring(2,3))) == 9) {
                boolean bandera = verificarSociedadesPrivadas(identificacion);
                if (bandera) {
                    tipo = Constantes.ruc_abreviatura;
                    tipo_contribuyente=repTipoContribuyente.findByTipoAndSubtipo("JURIDICA","PRIVADA");
                    tipo_identificacion= repTipoIdentificacion.findByCodigoSri("04");
                    Cliente cliente=new Cliente(tipo_identificacion,tipo_contribuyente);
                    return Optional.of(cliente);
                } 
            	throw new IdentificacionInvalidaException();
            	
            } else if (identificacion.length() == 13 && (Integer.parseInt(identificacion.substring(2,3)) != 6 || Integer.parseInt(identificacion.substring(2,3)) != 9)) {
                boolean bandera=verificarCedula(identificacion);
                if (bandera) {
                    tipo = Constantes.ruc_abreviatura;
                    tipo_contribuyente=repTipoContribuyente.findByTipoAndSubtipo("NATURAL", "NATURAL");
                    tipo_identificacion= repTipoIdentificacion.findByCodigoSri("04");
                    Cliente cliente=new Cliente(tipo_identificacion,tipo_contribuyente);
                    return Optional.of(cliente);
                }
            	throw new IdentificacionInvalidaException();
            	
            }else if (identificacion.length() == 13) {
                boolean bandera = verificarPersonaNatural(identificacion);
                if (bandera) {
                    tipo = Constantes.ruc_abreviatura;
                    tipo_contribuyente= repTipoContribuyente.findByTipoAndSubtipo("JURIDICA","PUBLICA");
                    tipo_identificacion= repTipoIdentificacion.findByCodigoSri("04");
                    Cliente cliente=new Cliente(tipo_identificacion,tipo_contribuyente);
                    return Optional.of(cliente);
                } 
            	throw new IdentificacionInvalidaException();
            	
            } else if (identificacion.length() == 7) {
                boolean bandera = verificarPlaca(identificacion);
                if (bandera) {
                    tipo = Constantes.placa_abreviatura;
                    tipo_identificacion= repTipoIdentificacion.findByCodigoSri("07");
                    Cliente cliente=new Cliente(tipo_identificacion,null);
                    return Optional.of(cliente);
                }
            	throw new IdentificacionInvalidaException();
            	
            } else if (identificacion.length() == 6) {
                boolean bandera = verificarPlacaMoto(identificacion);
                if (bandera) {
                    tipo = Constantes.placa_abreviatura;
                    tipo_identificacion= repTipoIdentificacion.findByCodigoSri("07");
                    Cliente cliente=new Cliente(tipo_identificacion,null);
                    return Optional.of(cliente);
                } 
            	throw new IdentificacionInvalidaException();
            }
            else if (identificacion.length() >=8) {
                boolean bandera = verificarPasaporte(identificacion);
                if (bandera) {
                    tipo = Constantes.pasaporte_abreviatura;
                    tipo_identificacion= repTipoIdentificacion.findByCodigoSri("06");
                    Cliente cliente=new Cliente(tipo_identificacion,null);
                    return Optional.of(cliente);
                }
            	throw new IdentificacionInvalidaException();
            }
        	throw new IdentificacionInvalidaException();
        }
        throw new IdentificacionInvalidaException();
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
    	cliente.normalizar();
    	Optional<Cliente>buscarCliente=rep.obtenerPorIdentificacion(cliente.getIdentificacion(), Constantes.activo);
    	if(buscarCliente.isPresent()) {
    		throw new EntidadExistenteException(Constantes.cliente);
    	}
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_cliente);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	Optional<Ubicacion> ubicacion= repUbicacion.findByProvinciaAndCantonAndParroquia(cliente.getDireccion().getUbicacion().getProvincia(),cliente.getDireccion().getUbicacion().getCanton(), cliente.getDireccion().getUbicacion().getParroquia());
    	if(ubicacion.isEmpty()) {
    		throw new EntidadNoExistenteException(Constantes.ubicacion);
    	}
    	for(Dependiente dependiente: cliente.getDependientes()) {
    		Optional<Ubicacion> ubicacionAuxiliar= repUbicacion.findByProvinciaAndCantonAndParroquia(dependiente.getDireccion().getUbicacion().getProvincia(),dependiente.getDireccion().getUbicacion().getCanton(), dependiente.getDireccion().getUbicacion().getParroquia());
        	if(ubicacionAuxiliar.isEmpty()) {
        		throw new EntidadNoExistenteException(Constantes.ubicacion);
        	}
        	dependiente.getDireccion().setUbicacion(ubicacionAuxiliar.get());
    	}
    	cliente.setCodigo(codigo.get());
    	cliente.setEstado(Constantes.activo);
        return rep.save(cliente);
    }

    
    
    @Override
    public Cliente actualizar(Cliente cliente) {
        return rep.save(cliente);
    }

    @Override
    public Cliente eliminar(Cliente cliente) {
        rep.delete(cliente);
        return cliente;
    }

    @Override
    public Optional<Cliente> eliminarPersonalizado(long id) {
        if(id!=0) {
        	Optional<Cliente> cliente=rep.findById(id);
        	if(cliente.isPresent()) {
        		cliente.get().setEstado(Constantes.inactivo);
            	return Optional.of(rep.save(cliente.get()));
        	}
        }
        throw new EntidadNoExistenteException(Constantes.cliente);
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
    public List<Cliente> consultarPersonalizado() {
        return rep.consultar(Constantes.activo);
    }
    
    @Override
    public Page<Cliente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    @Transactional
    public boolean importar(MultipartFile archivo_temporal) {
        List<Cliente> clientes=new ArrayList<>();
        try {
            List<List<String>>info= Util.leerImportar(archivo_temporal,4);
            for (List<String> datos: info){
                Cliente cliente=new Cliente(datos);
                Direccion direccion=cliente.getDireccion()!= null? adm.merge(cliente.getDireccion()): null;
                cliente.setDireccion(direccion);
                Financiamiento financiamiento=cliente.getFinanciamiento()!=null?adm.merge(cliente.getFinanciamiento()): null;
                cliente.setFinanciamiento(financiamiento);
                Optional<Cliente> cliente_verificado=validarIdentificacion(cliente);
                if(cliente_verificado.isPresent()){
                    clientes.add(cliente);
                } else{
                    System.out.println(cliente.getIdentificacion());
                }
            }
            if(clientes.isEmpty()){
                return false;
            }
            for(int i=0; i<clientes.size(); i++){
                adm.persist(clientes.get(i));
            }

            return true;
        }catch (Exception e){
            return false;
        }
    }
}
