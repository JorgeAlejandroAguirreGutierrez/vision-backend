package com.proyecto.vision.servicios.impl.compra;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.cliente.*;
import com.proyecto.vision.modelos.compra.CelularProveedor;
import com.proyecto.vision.modelos.compra.CorreoProveedor;
import com.proyecto.vision.modelos.compra.Proveedor;
import com.proyecto.vision.Util;
import com.proyecto.vision.modelos.compra.TelefonoProveedor;
import com.proyecto.vision.modelos.configuracion.TipoIdentificacion;
import com.proyecto.vision.modelos.configuracion.Ubicacion;
import com.proyecto.vision.repositorios.cliente.IClienteBaseRepository;
import com.proyecto.vision.repositorios.cliente.IContribuyenteRepository;
import com.proyecto.vision.repositorios.cliente.ITipoContribuyenteRepository;
import com.proyecto.vision.repositorios.compra.IProveedorRepository;
import com.proyecto.vision.repositorios.configuracion.ITipoIdentificacionRepository;
import com.proyecto.vision.repositorios.configuracion.IUbicacionRepository;
import com.proyecto.vision.servicios.interf.compra.IProveedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService implements IProveedorService {
    @Autowired
    private IProveedorRepository rep;
    @Autowired
    private IUbicacionRepository repUbicacion;
    @Autowired
    private ITipoContribuyenteRepository repTipoContribuyente;
    @Autowired
    private ITipoIdentificacionRepository repTipoIdentificacion;
    @Autowired
    private IClienteBaseRepository repClienteBase;
    @Autowired
    private IContribuyenteRepository repContribuyente;

    @Override
    public void validar(Proveedor proveedor) {
        if(proveedor.getIdentificacion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.identificacion);
        if(proveedor.getRazonSocial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.razonSocial);
        if(proveedor.getNombreComercial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.nombreComercial);
        if(proveedor.getDireccion().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.direccion);
        if(proveedor.getEspecial().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.especial);
        if(proveedor.getObligadoContabilidad().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.obligadoContabilidad);
        if(proveedor.getFantasma().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.fantasma);
        if(proveedor.getTipoIdentificacion().getId() == Constantes.ceroId) throw new DatoInvalidoException(Constantes.tipo_identificacion);
        if(proveedor.getGrupoProveedor().getId() == Constantes.cero) throw new DatoInvalidoException(Constantes.grupo_proveedor);
        if(proveedor.getFormaPago().getId() == Constantes.cero) throw new DatoInvalidoException(Constantes.forma_pago);
        if(proveedor.getUbicacion().getProvincia().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.provincia);
        if(proveedor.getUbicacion().getCanton().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.canton);
        if(proveedor.getUbicacion().getParroquia().equals(Constantes.vacio)) throw new DatoInvalidoException(Constantes.parroquia);
        if(proveedor.getCorreosProveedor().isEmpty()) throw new DatoInvalidoException(Constantes.correo);
        if(proveedor.getPlazoCredito().getId() == 0 ) proveedor.setPlazoCredito(null);
    }
    
    @Override
    public Proveedor crear(Proveedor proveedor) {
        validar(proveedor);
        Optional<Proveedor> buscarProveedor = rep.obtenerPorEmpresaYIdentificacion(proveedor.getEmpresa().getId(), proveedor.getIdentificacion(), Constantes.estadoActivo);
        if(buscarProveedor.isPresent()) {
            throw new EntidadExistenteException(Constantes.proveedor);
        }
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(Constantes.tabla_proveedor, proveedor.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
        Optional<Ubicacion> ubicacion = repUbicacion.findByProvinciaAndCantonAndParroquia(proveedor.getUbicacion().getProvincia(),proveedor.getUbicacion().getCanton(), proveedor.getUbicacion().getParroquia(), Constantes.estadoActivo);
        if(ubicacion.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.ubicacion);
        }
        proveedor.setUbicacion(ubicacion.get());
    	proveedor.setCodigo(codigo.get());
    	proveedor.setEstado(Constantes.estadoActivo);
    	Proveedor res = rep.save(proveedor);
        res.normalizar();
        return res;
    }

    @Override
    public Proveedor actualizar(Proveedor proveedor) {
    	validar(proveedor);
        Optional<Ubicacion> ubicacion = repUbicacion.findByProvinciaAndCantonAndParroquia(proveedor.getUbicacion().getProvincia(),proveedor.getUbicacion().getCanton(), proveedor.getUbicacion().getParroquia(), Constantes.estadoActivo);
        if(ubicacion.isEmpty()) {
            throw new EntidadNoExistenteException(Constantes.ubicacion);
        }
        proveedor.setUbicacion(ubicacion.get());
        Proveedor res = rep.save(proveedor);
        res.normalizar();
        return res;
    }

    @Override
    public Proveedor activar(Proveedor proveedor) {
        validar(proveedor);
        proveedor.setEstado(Constantes.estadoActivo);
        Proveedor res = rep.save(proveedor);
        res.normalizar();
        return res;
    }

    @Override
    public Proveedor inactivar(Proveedor proveedor) {
        validar(proveedor);
        proveedor.setEstado(Constantes.estadoInactivo);
        Proveedor res = rep.save(proveedor);
        res.normalizar();
        return res;
    }

    @Override
    public Proveedor obtener(long id) {
        Optional<Proveedor> proveedor= rep.findById(id);
        if(proveedor.isPresent()) {
        	Proveedor res = proveedor.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.proveedor);
    }
    @Override
    public Proveedor validarIdentificacionPorEmpresa(long empresaId, String identificacion) {
        if (identificacion!= null) {
            Optional<Proveedor> res = rep.obtenerPorEmpresaYIdentificacion(empresaId, identificacion, Constantes.estadoActivo);
            if(res.isPresent()) {
                throw new EntidadExistenteException(Constantes.proveedor);
            }
            TipoIdentificacion tipoIdentificacion=null;
            TipoContribuyente tipoContribuyente=null;
            if (identificacion.length() == 10 && Integer.parseInt((identificacion.substring(2,3))) != 6 && Integer.parseInt((identificacion.substring(2,3))) != 9) {
                boolean bandera = Util.verificarCedula(identificacion);
                if (bandera) {
                    tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("05").get();
                    tipoContribuyente= repTipoContribuyente.findByTipoAndSubtipo("NATURAL", "NATURAL");
                    Proveedor proveedor=new Proveedor();
                    proveedor.setIdentificacion(identificacion);
                    proveedor.setTipoIdentificacion(tipoIdentificacion);
                    proveedor.setTipoContribuyente(tipoContribuyente);
                    proveedor = buscarClienteBase(proveedor);
                    return proveedor;
                }
                throw new IdentificacionInvalidaException();
            } else if (identificacion.equals(Constantes.identificacion_consumidor_final)) {
                tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("07").get(); //Consumidor Final
                tipoContribuyente=repTipoContribuyente.findByTipoAndSubtipo(Constantes.tipo_contribuyente_natural, Constantes.tipo_contribuyente_natural);
                Proveedor proveedor=new Proveedor();
                proveedor.setIdentificacion(identificacion);
                proveedor.setTipoIdentificacion(tipoIdentificacion);
                proveedor.setTipoContribuyente(tipoContribuyente);
                return proveedor;
            } else if (identificacion.length() == 13 && Integer.parseInt((identificacion.substring(2,3))) == 6) {
                boolean bandera = Util.verificarSociedadesPublicas(identificacion);
                if (bandera) {
                    tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("04").get();
                    tipoContribuyente=repTipoContribuyente.findByTipoAndSubtipo(Constantes.tipo_contribuyente_juridica, Constantes.tipo_contribuyente_publica);
                    Proveedor proveedor=new Proveedor();
                    proveedor.setIdentificacion(identificacion);
                    proveedor.setTipoIdentificacion(tipoIdentificacion);
                    proveedor.setTipoContribuyente(tipoContribuyente);
                    proveedor = buscarContribuyente(proveedor);
                    return proveedor;
                }
                throw new IdentificacionInvalidaException();

            } else if (identificacion.length() == 13 && Integer.parseInt((identificacion.substring(2,3))) == 9) {
                boolean bandera = Util.verificarSociedadesPrivadas(identificacion);
                if (bandera) {
                    tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("04").get();
                    tipoContribuyente=repTipoContribuyente.findByTipoAndSubtipo("JURIDICA","PRIVADA");
                    Proveedor proveedor=new Proveedor();
                    proveedor.setIdentificacion(identificacion);
                    proveedor.setTipoIdentificacion(tipoIdentificacion);
                    proveedor.setTipoContribuyente(tipoContribuyente);
                    proveedor = buscarContribuyente(proveedor);
                    return proveedor;
                }
                throw new IdentificacionInvalidaException();

            } else if (identificacion.length() == 13 && (Integer.parseInt(identificacion.substring(2,3)) != 6 || Integer.parseInt(identificacion.substring(2,3)) != 9)) {
                boolean bandera = Util.verificarCedula(identificacion);
                if (bandera) {
                    tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("04").get();
                    tipoContribuyente=repTipoContribuyente.findByTipoAndSubtipo("NATURAL", "NATURAL");
                    Proveedor proveedor=new Proveedor();
                    proveedor.setIdentificacion(identificacion);
                    proveedor.setTipoIdentificacion(tipoIdentificacion);
                    proveedor.setTipoContribuyente(tipoContribuyente);
                    proveedor = buscarContribuyente(proveedor);
                    return proveedor;
                }
                throw new IdentificacionInvalidaException();

            }else if (identificacion.length() == 13) {
                boolean bandera = Util.verificarPersonaNatural(identificacion);
                if (bandera) {
                    tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("04").get();
                    tipoContribuyente= repTipoContribuyente.findByTipoAndSubtipo("JURIDICA","PUBLICA");
                    Proveedor proveedor=new Proveedor();
                    proveedor.setIdentificacion(identificacion);
                    proveedor.setTipoIdentificacion(tipoIdentificacion);
                    proveedor.setTipoContribuyente(tipoContribuyente);
                    proveedor = buscarContribuyente(proveedor);
                    return proveedor;
                }
                throw new IdentificacionInvalidaException();
            } else if (identificacion.length() >=8) {
                boolean bandera = Util.verificarPasaporte(identificacion);
                if (bandera) {
                    tipoIdentificacion= repTipoIdentificacion.findByCodigoSri("06").get();
                    Proveedor proveedor=new Proveedor();
                    proveedor.setIdentificacion(identificacion);
                    proveedor.setTipoIdentificacion(tipoIdentificacion);
                    proveedor.setTipoContribuyente(tipoContribuyente);
                    return proveedor;
                }
                throw new IdentificacionInvalidaException();
            }
            throw new IdentificacionInvalidaException();
        }
        throw new IdentificacionInvalidaException();
    }

    @Override
    public Proveedor buscarClienteBase(Proveedor proveedor){
        Optional<ClienteBase> clienteBase = repClienteBase.obtenerPorIdentificacion(proveedor.getIdentificacion(), Constantes.estadoActivo);
        if(clienteBase.isPresent()) {
            proveedor.setRazonSocial(clienteBase.get().getApellidos()+Constantes.espacio+clienteBase.get().getNombres());
            if (clienteBase.get().getDireccion()!=null) {
                proveedor.setDireccion(clienteBase.get().getDireccion());
            }
            if (clienteBase.get().getReferencia()!=null) {
                proveedor.setReferencia(clienteBase.get().getReferencia());
            }
            if (clienteBase.get().getUbicacion()!=null) {
                proveedor.setUbicacion(clienteBase.get().getUbicacion());
            }
            if (clienteBase.get().getTelefono()!=null) {
                List<TelefonoProveedor> telefonos = new ArrayList<>();
                telefonos.add(new TelefonoProveedor("", clienteBase.get().getTelefono(), new Proveedor()));
                proveedor.setTelefonosProveedor(telefonos);
            }
            if (clienteBase.get().getCelular()!=null) {
                List<CelularProveedor> celulares = new ArrayList<>();
                celulares.add(new CelularProveedor("", clienteBase.get().getCelular(), new Proveedor()));
                proveedor.setCelularesProveedor(celulares);
            }
            if (clienteBase.get().getCorreo()!=null) {
                List<CorreoProveedor> correos = new ArrayList<>();
                correos.add(new CorreoProveedor("", clienteBase.get().getCorreo(), new Proveedor()));
                proveedor.setCorreosProveedor(correos);
            }

        }
        return proveedor;
    }

    @Override
    public Proveedor buscarContribuyente(Proveedor proveedor){
        Optional<Contribuyente> contribuyente = repContribuyente.obtenerPorIdentificacion(proveedor.getIdentificacion());
        if(contribuyente.isPresent()) {
            proveedor.setRazonSocial(contribuyente.get().getRazonSocial());
            if (contribuyente.get().getNombreComercial()!=null) {
                proveedor.setNombreComercial(contribuyente.get().getNombreComercial());
            }
            if (contribuyente.get().getObligadoContabilidad()!=null) {
                proveedor.setObligadoContabilidad(contribuyente.get().getObligadoContabilidad());
            }
            if (contribuyente.get().getEstado()!=null) {
                proveedor.setEstado(contribuyente.get().getEstado());
            }
            if (contribuyente.get().getUbicacion()!=null) {
                proveedor.setUbicacion(contribuyente.get().getUbicacion());
            }
        }
        return proveedor;
    }

    @Override
    public List<Proveedor> consultar() {
        return rep.consultar();
    }

    @Override
    public List<Proveedor> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    @Override
    public List<Proveedor> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    @Override
    public List<Proveedor> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    @Override
    public Page<Proveedor> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }

    @Override
    public List<Proveedor> buscar(Proveedor proveedor) {
        return  rep.consultarPorRazonSocial(proveedor.getRazonSocial(), Constantes.estadoActivo);
    }
}
