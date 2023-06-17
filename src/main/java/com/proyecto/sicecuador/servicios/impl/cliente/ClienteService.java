package com.proyecto.sicecuador.servicios.impl.cliente;

import com.proyecto.sicecuador.Constantes;
import com.proyecto.sicecuador.Util;
import com.proyecto.sicecuador.exception.*;
import com.proyecto.sicecuador.modelos.cliente.*;
import com.proyecto.sicecuador.modelos.configuracion.TipoIdentificacion;
import com.proyecto.sicecuador.modelos.configuracion.Ubicacion;
import com.proyecto.sicecuador.repositorios.cliente.IClienteRepository;
import com.proyecto.sicecuador.repositorios.cliente.IClienteBaseRepository;
import com.proyecto.sicecuador.repositorios.cliente.IContribuyenteRepository;
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
    private IClienteBaseRepository repClienteBase;
    @Autowired
    private IContribuyenteRepository repContribuyente;
    @Autowired
    private ITipoContribuyenteRepository repTipoContribuyente;
    @Autowired
    private ITipoIdentificacionRepository repTipoIdentificacion;
    @Autowired
    private IUbicacionRepository repUbicacion;

    @PersistenceContext
    private EntityManager adm;

    @Override
    public void validar(Cliente cliente) {
        if(cliente.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
        if(cliente.getRazonSocial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.razonSocial);
        if(cliente.getObligadoContabilidad().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.obligadoContabilidad);
        if(cliente.getEspecial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.especial);
        if(cliente.getSegmento().getId() == Constantes.cero) throw new DatoInvalidoException(Constantes.segmento);
        if(cliente.getDireccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.direccion);
        if(cliente.getTipoIdentificacion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.tipo_identificacion);
        if(cliente.getTipoContribuyente().getId() == Constantes.cero) throw new DatoInvalidoException(Constantes.tipo_contribuyente);
        if(cliente.getGrupoCliente().getId() == Constantes.cero) throw new DatoInvalidoException(Constantes.grupo_cliente);
        if(cliente.getFormaPago().getId() == Constantes.cero) throw new DatoInvalidoException(Constantes.forma_pago);
        if(cliente.getUbicacion().getProvincia().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.provincia);
        if(cliente.getUbicacion().getCanton().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.canton);
        if(cliente.getUbicacion().getParroquia().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.parroquia);
        if(cliente.getRetencionesCliente().isEmpty()) throw new DatoInvalidoException(Constantes.retencion_cliente);
        for (RetencionCliente retencionCliente : cliente.getRetencionesCliente()){
            if(retencionCliente.getTipoRetencion().getId() == Constantes.ceroId) retencionCliente.setTipoRetencion(null);
        }
        if(cliente.getGenero().getId() == Constantes.cero) cliente.setGenero(null);//throw new DatoInvalidoException(Constantes.genero);
        if(cliente.getEstadoCivil().getId() == Constantes.cero) cliente.setEstadoCivil(null);
        if(cliente.getCalificacionCliente().getId() == Constantes.cero) cliente.setCalificacionCliente(null);
        if(cliente.getOrigenIngreso().getId() == Constantes.cero) cliente.setOrigenIngreso(null);
        if(cliente.getPlazoCredito().getId() == Constantes.cero ) cliente.setPlazoCredito(null);
    }
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
    public String existe(Cliente cliente) {
        Optional<Cliente> resp= rep.obtenerPorEmpresaYIdentificacion(cliente.getEmpresa().getId(), cliente.getIdentificacion(), Constantes.activo);
        if (resp.isPresent()) {
        	return Constantes.si;
        }
        return Constantes.no;
    }

    @Override
    public Cliente obtenerPorRazonSocial(String razonSocial) {
        Optional<Cliente> cliente = rep.obtenerPorRazonSocial(razonSocial, Constantes.activo);
        if(cliente.isPresent()){
        	Cliente res = cliente.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.cliente);
    }
    @Override
    public Cliente obtenerPorIdentificacion(String identificacion) {
        Optional<Cliente> cliente = rep.obtenerPorRazonSocial(identificacion, Constantes.activo);
        if(cliente.isPresent()){
        	Cliente res = cliente.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.cliente);
    }

    @Override
    public Cliente validarIdentificacionPorEmpresa(long empresaId, String identificacion) {
    	if (identificacion!= null) {
	    	Optional<Cliente> res = rep.obtenerPorEmpresaYIdentificacion(empresaId, identificacion, Constantes.activo);
	    	if(res.isPresent()) {
	    		throw new EntidadExistenteException(Constantes.cliente);
	    	}
	    	TipoIdentificacion tipoIdentificacion=null;
	    	TipoContribuyente tipoContribuyente=null;
            if (identificacion.length() == 10 && Integer.parseInt((identificacion.substring(2,3))) != 6 && Integer.parseInt((identificacion.substring(2,3))) != 9) {
                boolean bandera = verificarCedula(identificacion);
                if (bandera) {
                	tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("05");
                	tipoContribuyente= repTipoContribuyente.findByTipoAndSubtipo("NATURAL", "NATURAL");
                    Cliente cliente=new Cliente();
                    cliente.setIdentificacion(identificacion);
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setTipoContribuyente(tipoContribuyente);
                    cliente = buscarClienteBase(cliente);
                    return cliente;
                }
                throw new IdentificacionInvalidaException();
            } else if (identificacion.equals(Constantes.identificacion_consumidor_final)) {
            	tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("07");
            	tipoContribuyente=repTipoContribuyente.findByTipoAndSubtipo(Constantes.tipo_contribuyente_natural, Constantes.tipo_contribuyente_natural);
                Cliente cliente=new Cliente();
                cliente.setIdentificacion(identificacion);
                cliente.setTipoIdentificacion(tipoIdentificacion);
                cliente.setTipoContribuyente(tipoContribuyente);
                return cliente;
            } else if (identificacion.length() == 13 && Integer.parseInt((identificacion.substring(2,3))) == 6) {
                boolean bandera = verificarSociedadesPublicas(identificacion);
                if (bandera) {
                	tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("04");
                	tipoContribuyente=repTipoContribuyente.findByTipoAndSubtipo(Constantes.tipo_contribuyente_juridica, Constantes.tipo_contribuyente_publica);
                    Cliente cliente=new Cliente();
                    cliente.setIdentificacion(identificacion);
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setTipoContribuyente(tipoContribuyente);
                    cliente = buscarContribuyente(cliente);
                    return cliente;
                } 
            	throw new IdentificacionInvalidaException();
            	
            } else if (identificacion.length() == 13 && Integer.parseInt((identificacion.substring(2,3))) == 9) {
                boolean bandera = verificarSociedadesPrivadas(identificacion);
                if (bandera) {
                	tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("04");
                	tipoContribuyente=repTipoContribuyente.findByTipoAndSubtipo("JURIDICA","PRIVADA");
                    Cliente cliente=new Cliente();
                    cliente.setIdentificacion(identificacion);
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setTipoContribuyente(tipoContribuyente);
                    cliente = buscarContribuyente(cliente);
                    return cliente;
                } 
            	throw new IdentificacionInvalidaException();
            	
            } else if (identificacion.length() == 13 && (Integer.parseInt(identificacion.substring(2,3)) != 6 || Integer.parseInt(identificacion.substring(2,3)) != 9)) {
                boolean bandera=verificarCedula(identificacion);
                if (bandera) {
                	tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("04");
                	tipoContribuyente=repTipoContribuyente.findByTipoAndSubtipo("NATURAL", "NATURAL");
                    Cliente cliente=new Cliente();
                    cliente.setIdentificacion(identificacion);
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setTipoContribuyente(tipoContribuyente);
                    cliente = buscarContribuyente(cliente);
                    return cliente;
                }
            	throw new IdentificacionInvalidaException();
            	
            }else if (identificacion.length() == 13) {
                boolean bandera = verificarPersonaNatural(identificacion);
                if (bandera) {
                	tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("04");
                	tipoContribuyente= repTipoContribuyente.findByTipoAndSubtipo("JURIDICA","PUBLICA");
                    Cliente cliente=new Cliente();
                    cliente.setIdentificacion(identificacion);
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setTipoContribuyente(tipoContribuyente);
                    cliente = buscarContribuyente(cliente);
                    return cliente;
                } 
            	throw new IdentificacionInvalidaException();
            	
            } else if (identificacion.length() == 7) {
                boolean bandera = verificarPlaca(identificacion);
                if (bandera) {
                    tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("07");
                    Cliente cliente=new Cliente();
                    cliente.setIdentificacion(identificacion);
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setTipoContribuyente(null);
                    return cliente;
                }
            	throw new IdentificacionInvalidaException();
            	
            } else if (identificacion.length() == 6) {
                boolean bandera = verificarPlacaMoto(identificacion);
                if (bandera) {
                    tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("07");
                    Cliente cliente=new Cliente();
                    cliente.setIdentificacion(identificacion);
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setTipoContribuyente(null);
                    return cliente;
                } 
            	throw new IdentificacionInvalidaException();
            }
            else if (identificacion.length() >=8) {
                boolean bandera = verificarPasaporte(identificacion);
                if (bandera) {
                    tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("06");
                    Cliente cliente=new Cliente();
                    cliente.setIdentificacion(identificacion);
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setTipoContribuyente(null);
                    return cliente;
                }
            	throw new IdentificacionInvalidaException();
            }
        	throw new IdentificacionInvalidaException();
        }
        throw new IdentificacionInvalidaException();
    }

    @Override
    public Cliente buscarClienteBase(Cliente cliente){
        Optional<ClienteBase> clienteBase = repClienteBase.obtenerPorIdentificacion(cliente.getIdentificacion(), Constantes.activo);
        if(clienteBase.isPresent()) {
            cliente.setRazonSocial(clienteBase.get().getApellidos()+Constantes.espacio+clienteBase.get().getNombres());
            if (clienteBase.get().getDireccion()!=null) {
                cliente.setDireccion(clienteBase.get().getDireccion());
            }
            if (clienteBase.get().getReferencia()!=null) {
                cliente.setReferencia(clienteBase.get().getReferencia());
            }
            if (clienteBase.get().getUbicacion()!=null) {
                cliente.setUbicacion(clienteBase.get().getUbicacion());
            }
            if (clienteBase.get().getGenero()!=null) {
                cliente.setGenero(clienteBase.get().getGenero());
            }
            if (clienteBase.get().getEstadoCivil()!=null) {
                cliente.setEstadoCivil(clienteBase.get().getEstadoCivil());
            }
            if (clienteBase.get().getTelefono()!=null) {
                List<Telefono> telefonos = new ArrayList<>();
                telefonos.add(new Telefono("", clienteBase.get().getTelefono(), new Cliente()));
                cliente.setTelefonos(telefonos);
            }
            if (clienteBase.get().getCelular()!=null) {
                List<Celular> celulares = new ArrayList<>();
                celulares.add(new Celular("", clienteBase.get().getCelular(), new Cliente()));
                cliente.setCelulares(celulares);
            }
            if (clienteBase.get().getCorreo()!=null) {
                List<Correo> correos = new ArrayList<>();
                correos.add(new Correo("", clienteBase.get().getCorreo(), new Cliente()));
                cliente.setCorreos(correos);
            }

        }
        return cliente;
    }

    @Override
    public Cliente buscarContribuyente(Cliente cliente){
        Optional<Contribuyente> contribuyente = repContribuyente.obtenerPorIdentificacion(cliente.getIdentificacion());
        if(contribuyente.isPresent()) {
            cliente.setRazonSocial(contribuyente.get().getRazonSocial());
            if (contribuyente.get().getObligadoContabilidad()!=null) {
                cliente.setObligadoContabilidad(contribuyente.get().getObligadoContabilidad());
            }
            if (contribuyente.get().getEstado()!=null) {
                cliente.setEstado(contribuyente.get().getEstado());
            }
            if (contribuyente.get().getUbicacion()!=null) {
                cliente.setUbicacion(contribuyente.get().getUbicacion());
            }
        }
        return cliente;
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
    	validar(cliente);
    	Optional<Cliente>buscarCliente=rep.obtenerPorEmpresaYIdentificacion(cliente.getEmpresa().getId(), cliente.getIdentificacion(), Constantes.activo);
    	if(buscarCliente.isPresent()) {
    		throw new EntidadExistenteException(Constantes.cliente);
    	}
    	Optional<String>codigo=Util.generarCodigo(Constantes.tabla_cliente);
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	Optional<Ubicacion> ubicacion = repUbicacion.findByProvinciaAndCantonAndParroquia(cliente.getUbicacion().getProvincia(),cliente.getUbicacion().getCanton(), cliente.getUbicacion().getParroquia(), Constantes.activo);
    	if(ubicacion.isEmpty()) {
    		throw new EntidadNoExistenteException(Constantes.ubicacion);
    	}
        if(cliente.getCorreos().isEmpty()){
            List<Correo> correos = new ArrayList<>();
            correos.add(new Correo("", Constantes.correo_predeterminado, cliente));
            cliente.setCorreos(correos);
        }
    	for(Dependiente dependiente: cliente.getDependientes()) {
    		Optional<Ubicacion> ubicacionDependiente= repUbicacion.findByProvinciaAndCantonAndParroquia(dependiente.getUbicacion().getProvincia(),dependiente.getUbicacion().getCanton(), dependiente.getUbicacion().getParroquia(), Constantes.activo);
        	if(ubicacionDependiente.isEmpty()) {
        		throw new EntidadNoExistenteException(Constantes.dependiente);
        	}
        	dependiente.setUbicacion(ubicacionDependiente.get());
    	}
    	cliente.setUbicacion(ubicacion.get());
    	cliente.setCodigo(codigo.get());
    	cliente.setEstado(Constantes.activo);
        Cliente res = rep.save(cliente);
        res.normalizar();
        return res;
    }
    
    @Override
    public Cliente actualizar(Cliente cliente) {
    	validar(cliente);
        Optional<Ubicacion> ubicacion = repUbicacion.findByProvinciaAndCantonAndParroquia(cliente.getUbicacion().getProvincia(),cliente.getUbicacion().getCanton(), cliente.getUbicacion().getParroquia(), Constantes.activo);
    	if(ubicacion.isEmpty()) {
    		throw new EntidadNoExistenteException(Constantes.ubicacion);
    	}
        if(cliente.getCorreos().isEmpty()){
            List<Correo> correos = new ArrayList<>();
            correos.add(new Correo("", Constantes.correo_predeterminado, cliente));
            cliente.setCorreos(correos);
        }
        for(Dependiente dependiente: cliente.getDependientes()) {
            Optional<Ubicacion> ubicacionDependiente= repUbicacion.findByProvinciaAndCantonAndParroquia(dependiente.getUbicacion().getProvincia(),dependiente.getUbicacion().getCanton(), dependiente.getUbicacion().getParroquia(), Constantes.activo);
            if(ubicacionDependiente.isEmpty()) {
                throw new EntidadNoExistenteException(Constantes.dependiente);
            }
            dependiente.setUbicacion(ubicacionDependiente.get());
        }
    	cliente.setUbicacion(ubicacion.get());
        Cliente res = rep.save(cliente);
        res.normalizar();
        return res;
    }

    @Override
    public Cliente activar(Cliente cliente) {
        validar(cliente);
        cliente.setEstado(Constantes.activo);
        Cliente res = rep.save(cliente);
        res.normalizar();
        return res;
    }

    @Override
    public Cliente inactivar(Cliente cliente) {
        validar(cliente);
        cliente.setEstado(Constantes.inactivo);
        Cliente res = rep.save(cliente);
        res.normalizar();
        return res;
    }

    
    @Override
    public Cliente obtener(long id) {
        Optional<Cliente> cliente= rep.findById(id);
        if(cliente.isPresent()) {
        	Cliente res = cliente.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.cliente);
        
    }

    @Override
    public List<Cliente> consultar() {
        return rep.consultar();
    }

    @Override
    public List<Cliente> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<Cliente> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<Cliente> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }
    
    @Override
    public Page<Cliente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
