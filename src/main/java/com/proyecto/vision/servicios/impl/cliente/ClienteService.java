package com.proyecto.vision.servicios.impl.cliente;

import com.proyecto.vision.Constantes;
import com.proyecto.vision.Util;
import com.proyecto.vision.exception.*;
import com.proyecto.vision.modelos.cliente.*;
import com.proyecto.vision.modelos.configuracion.TipoIdentificacion;
import com.proyecto.vision.modelos.configuracion.Ubicacion;
import com.proyecto.vision.repositorios.cliente.IClienteRepository;
import com.proyecto.vision.repositorios.cliente.IClienteBaseRepository;
import com.proyecto.vision.repositorios.cliente.IContribuyenteRepository;
import com.proyecto.vision.repositorios.cliente.ITipoContribuyenteRepository;
import com.proyecto.vision.repositorios.configuracion.ITipoIdentificacionRepository;
import com.proyecto.vision.repositorios.configuracion.IUbicacionRepository;
import com.proyecto.vision.servicios.interf.cliente.IClienteService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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

    /**
     * Metodo que permite la validacion de las propiedades del objeto
     * @param cliente
     */
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
        if(cliente.getGenero().getId() == Constantes.cero) cliente.setGenero(null);
        if(cliente.getEstadoCivil().getId() == Constantes.cero) cliente.setEstadoCivil(null);
        if(cliente.getCalificacionCliente().getId() == Constantes.cero) cliente.setCalificacionCliente(null);
        if(cliente.getOrigenIngreso().getId() == Constantes.cero) cliente.setOrigenIngreso(null);
        if(cliente.getPlazoCredito().getId() == Constantes.cero ) cliente.setPlazoCredito(null);
    }

    /**
     * Metodo que permite validar si existe un cliente
     * @param cliente
     * @return un si o un no segun existencia del cliente
     */
    @Override
    public String existe(Cliente cliente) {
        Optional<Cliente> resp= rep.obtenerPorEmpresaYIdentificacion(cliente.getEmpresa().getId(), cliente.getIdentificacion(), Constantes.estadoActivo);
        if (resp.isPresent()) {
        	return Constantes.si;
        }
        return Constantes.no;
    }

    /**
     * Metodo que permite obtener un cliente filtrando por razon social, empresa y estado
     * @param razonSocial
     * @param empresaId
     * @param estado
     * @return el objeto
     */
    @Override
    public Cliente obtenerPorRazonSocialYEmpresaYEstado(String razonSocial, long empresaId, String estado) {
        Optional<Cliente> cliente = rep.obtenerPorRazonSocialYEmpresaYEstado(razonSocial, empresaId, estado);
        if(cliente.isPresent()){
        	Cliente res = cliente.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.cliente);
    }

    /**
     * Metodo que permite obtener un cliente filtrando por razon social, empresa y estado
     * @param identificacion
     * @param empresaId
     * @param estado
     * @return el objeto
     */
    @Override
    public Cliente obtenerPorIdentificacionYEmpresaYEstado(String identificacion, long empresaId, String estado) {
        Optional<Cliente> cliente = rep.obtenerPorIdentificacionYEmpresaYEstado(identificacion, empresaId, estado);
        if(cliente.isPresent()){
        	Cliente res = cliente.get();
            res.normalizar();
            return res;
        }
        throw new EntidadNoExistenteException(Constantes.cliente);
    }

    /**
     * Metodo que permite validar la identificacion de un cliente filtrando por la empresa
     * @param empresaId
     * @param identificacion
     * @return el objeto
     */
    @Override
    public Cliente validarIdentificacionPorEmpresa(String identificacion, long empresaId) {
    	if (identificacion != null) {
	    	Optional<Cliente> res = rep.obtenerPorEmpresaYIdentificacion(empresaId, identificacion, Constantes.estadoActivo);
	    	if(res.isPresent()) {
	    		throw new EntidadExistenteException(Constantes.cliente);
	    	}
	    	TipoIdentificacion tipoIdentificacion = null;
	    	TipoContribuyente tipoContribuyente = null;
            if (identificacion.length() == 10 && Integer.parseInt((identificacion.substring(2,3))) != 6 && Integer.parseInt((identificacion.substring(2,3))) != 9) {
                boolean bandera = Util.verificarCedula(identificacion);
                if (bandera) {
                	tipoIdentificacion = repTipoIdentificacion.obtenerPorCodigoSri(Constantes.codigo_cedula_sri).get();
                	tipoContribuyente= repTipoContribuyente.findByTipoAndSubtipo(Constantes.tipo_contribuyente_natural, Constantes.tipo_contribuyente_natural);
                    String razonSocial = Constantes.vacio;
                	try {
                        HttpPost request = new HttpPost(Constantes.url_cedula_consultas_ecuador + identificacion);
                        request.setHeader(Constantes.origin, Constantes.consultas_ecuador);
                        HttpClient httpClient = HttpClients.custom()
                                .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
                                                .loadTrustMaterial(null, new TrustAllStrategy())
                                                .build()
                                        )
                                ).build();
                        HttpResponse response = httpClient.execute(request);
                        String json = EntityUtils.toString(response.getEntity());
                        JSONObject objeto = new JSONObject(json);
                        razonSocial = objeto.getJSONObject("data").getString("nombres");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Cliente cliente = new Cliente();
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                	cliente.setIdentificacion(identificacion);
                    cliente.setRazonSocial(razonSocial);
                    cliente.setTipoContribuyente(tipoContribuyente);
                    cliente = buscarClienteBase(cliente);
                    return cliente;
                }
                throw new IdentificacionInvalidaException();
            } else if (identificacion.equals(Constantes.identificacion_consumidor_final)) {
            	tipoIdentificacion = repTipoIdentificacion.obtenerPorCodigoSri(Constantes.codigo_consumidor_final_sri).get();
            	tipoContribuyente=repTipoContribuyente.findByTipoAndSubtipo(Constantes.tipo_contribuyente_natural, Constantes.tipo_contribuyente_natural);
                Cliente cliente = new Cliente();
                cliente.setIdentificacion(identificacion);
                cliente.setTipoIdentificacion(tipoIdentificacion);
                cliente.setTipoContribuyente(tipoContribuyente);
                return cliente;
            } else if (identificacion.length() == 13 && Integer.parseInt((identificacion.substring(2,3))) == 6) {
                //boolean bandera = Util.verificarSociedadesPublicas(identificacion);
                //if (bandera) {
                	tipoIdentificacion = repTipoIdentificacion.obtenerPorCodigoSri(Constantes.codigo_sociedades_publicas_sri).get();
                	tipoContribuyente = repTipoContribuyente.findByTipoAndSubtipo(Constantes.tipo_contribuyente_juridica, Constantes.tipo_contribuyente_publica);
                    Cliente cliente=new Cliente();
                    cliente.setIdentificacion(identificacion);
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setTipoContribuyente(tipoContribuyente);
                    cliente = buscarContribuyente(cliente);
                    try {
                        HttpGet request = new HttpGet(Constantes.url_ruc_consultas_ecuador + identificacion);
                        HttpClient httpClient = HttpClients.custom()
                                .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
                                                .loadTrustMaterial(null, new TrustAllStrategy())
                                                .build()
                                        )
                                ).build();
                        HttpResponse response = httpClient.execute(request);
                        String html = EntityUtils.toString(response.getEntity());
                        Document documento = Jsoup.parse(html);
                        Elements table = documento.getElementsByClass("formulario");
                        JSONObject objeto = XML.toJSONObject(table.html());
                        String razonSocial = objeto.getJSONObject("tbody").getJSONArray("tr").getJSONObject(0).getString("td");
                        String claseContribuyente = objeto.getJSONObject("tbody").getJSONArray("tr").getJSONObject(9).getString("td");
                        String obligadoContabilidad = objeto.getJSONObject("tbody").getJSONArray("tr").getJSONObject(13).getString("td");
                        cliente.setRazonSocial(razonSocial);
                        cliente.setObligadoContabilidad(obligadoContabilidad);
                        if(claseContribuyente.toUpperCase().equals(Constantes.especial)){
                            cliente.setEspecial(Constantes.si);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return cliente;
                //}
            	//throw new IdentificacionInvalidaException();
            	
            } else if (identificacion.length() == 13 && Integer.parseInt((identificacion.substring(2,3))) == 9) {
                //boolean bandera = Util.verificarSociedadesPrivadas(identificacion);
                //if (bandera) {
                	tipoIdentificacion= repTipoIdentificacion.obtenerPorCodigoSri(Constantes.codigo_sociedades_privadas_sri).get();
                	tipoContribuyente=repTipoContribuyente.findByTipoAndSubtipo(Constantes.tipo_contribuyente_juridica,Constantes.tipo_contribuyente_privada);
                    Cliente cliente=new Cliente();
                    cliente.setIdentificacion(identificacion);
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setTipoContribuyente(tipoContribuyente);
                    cliente = buscarContribuyente(cliente);
                    try {
                        HttpGet request = new HttpGet(Constantes.url_ruc_consultas_ecuador + identificacion);
                        HttpClient httpClient = HttpClients.custom()
                                .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
                                                .loadTrustMaterial(null, new TrustAllStrategy())
                                                .build()
                                        )
                                ).build();
                        HttpResponse response = httpClient.execute(request);
                        String html = EntityUtils.toString(response.getEntity());
                        Document documento = Jsoup.parse(html);
                        Elements table = documento.getElementsByClass("formulario");
                        JSONObject objeto = XML.toJSONObject(table.html());
                        String razonSocial = objeto.getJSONObject("tbody").getJSONArray("tr").getJSONObject(0).getString("td");
                        String claseContribuyente = objeto.getJSONObject("tbody").getJSONArray("tr").getJSONObject(9).getString("td");
                        String obligadoContabilidad = objeto.getJSONObject("tbody").getJSONArray("tr").getJSONObject(13).getString("td");
                        cliente.setRazonSocial(razonSocial);
                        cliente.setObligadoContabilidad(obligadoContabilidad);
                        if(claseContribuyente.toUpperCase().equals(Constantes.especial)){
                            cliente.setEspecial(Constantes.si);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return cliente;
                //}
            	//throw new IdentificacionInvalidaException();
            	
            } else if (identificacion.length() == 13 && (Integer.parseInt(identificacion.substring(2,3)) != 6 || Integer.parseInt(identificacion.substring(2,3)) != 9)) {
                boolean bandera = Util.verificarCedula(identificacion);
                if (bandera) {
                	tipoIdentificacion = repTipoIdentificacion.obtenerPorCodigoSri(Constantes.codigo_ruc_sri).get();
                	tipoContribuyente = repTipoContribuyente.findByTipoAndSubtipo(Constantes.tipo_contribuyente_natural, Constantes.tipo_contribuyente_natural);
                    Cliente cliente = new Cliente();
                    cliente.setIdentificacion(identificacion);
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setTipoContribuyente(tipoContribuyente);
                    cliente = buscarContribuyente(cliente);
                    try {
                        HttpGet request = new HttpGet(Constantes.url_ruc_consultas_ecuador + identificacion);
                        HttpClient httpClient = HttpClients.custom()
                                .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
                                                .loadTrustMaterial(null, new TrustAllStrategy())
                                                .build()
                                        )
                                ).build();
                        HttpResponse response = httpClient.execute(request);
                        String html = EntityUtils.toString(response.getEntity());
                        Document documento = Jsoup.parse(html);
                        Elements table = documento.getElementsByClass("formulario");
                        JSONObject objeto = XML.toJSONObject(table.html());
                        String razonSocial = objeto.getJSONObject("tbody").getJSONArray("tr").getJSONObject(0).getString("td");
                        String claseContribuyente = objeto.getJSONObject("tbody").getJSONArray("tr").getJSONObject(9).getString("td");
                        String obligadoContabilidad = objeto.getJSONObject("tbody").getJSONArray("tr").getJSONObject(13).getString("td");
                        cliente.setRazonSocial(razonSocial);
                        cliente.setObligadoContabilidad(obligadoContabilidad);
                        if(claseContribuyente.toUpperCase().equals(Constantes.especial)){
                            cliente.setEspecial(Constantes.si);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return cliente;
                }
            	throw new IdentificacionInvalidaException();
            	
            }else if (identificacion.length() == 13) {
                boolean bandera = Util.verificarPersonaNatural(identificacion);
                if (bandera) {
                	tipoIdentificacion = repTipoIdentificacion.obtenerPorCodigoSri(Constantes.codigo_ruc_sri).get();
                	tipoContribuyente = repTipoContribuyente.findByTipoAndSubtipo(Constantes.tipo_contribuyente_juridica,Constantes.tipo_contribuyente_publica);
                    Cliente cliente=new Cliente();
                    cliente.setIdentificacion(identificacion);
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setTipoContribuyente(tipoContribuyente);
                    cliente = buscarContribuyente(cliente);
                    return cliente;
                } 
            	throw new IdentificacionInvalidaException();
            	
            } else if (identificacion.length() == 7) {
                boolean bandera = Util.verificarPlaca(identificacion);
                if (bandera) {
                    tipoIdentificacion = repTipoIdentificacion.obtenerPorCodigoSri(Constantes.codigo_placa_sri).get();
                    String razonSocial = Constantes.vacio;
                    try {
                        HttpPost request = new HttpPost(Constantes.url_placa_consultas_ecuador+identificacion);
                        request.setHeader(Constantes.origin, Constantes.consultas_ecuador);
                        HttpClient httpClient = HttpClients.custom()
                                .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
                                                .loadTrustMaterial(null, new TrustAllStrategy())
                                                .build()
                                        )
                                ).build();
                        HttpResponse response = httpClient.execute(request);
                        String json = EntityUtils.toString(response.getEntity());
                        JSONObject objeto = new JSONObject(json);
                        razonSocial = objeto.getJSONObject("data").getString("names");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Cliente cliente = new Cliente();
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setIdentificacion(identificacion);
                    cliente.setRazonSocial(razonSocial);
                    cliente.setTipoContribuyente(null);
                    return cliente;
                }
            	throw new IdentificacionInvalidaException();
            	
            } else if (identificacion.length() == 6) {
                boolean bandera = Util.verificarPlacaMoto(identificacion);
                if (bandera) {
                    tipoIdentificacion = repTipoIdentificacion.obtenerPorCodigoSri(Constantes.codigo_placa_sri).get();
                    Cliente cliente = new Cliente();
                    cliente.setIdentificacion(identificacion);
                    cliente.setTipoIdentificacion(tipoIdentificacion);
                    cliente.setTipoContribuyente(null);
                    return cliente;
                } 
            	throw new IdentificacionInvalidaException();
            }
            else if (identificacion.length() >=8) {
                boolean bandera = Util.verificarPasaporte(identificacion);
                if (bandera) {
                    tipoIdentificacion = repTipoIdentificacion.obtenerPorCodigoSri(Constantes.codigo_pasaporte_sri).get();
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

    /**
     * Metodo que permite buscar un cliente el base historica
     * @param cliente
     * @return el objeto
     */
    @Override
    public Cliente buscarClienteBase(Cliente cliente){
        Optional<ClienteBase> clienteBase = repClienteBase.obtenerPorIdentificacion(cliente.getIdentificacion(), Constantes.estadoActivo);
        if(clienteBase.isPresent()) {
            cliente.setRazonSocial(clienteBase.get().getApellidos()+Constantes.espacio+clienteBase.get().getNombres());
            if (clienteBase.get().getDireccion() != null) {
                cliente.setDireccion(clienteBase.get().getDireccion());
            }
            if (clienteBase.get().getReferencia() != null) {
                cliente.setReferencia(clienteBase.get().getReferencia());
            }
            if (clienteBase.get().getUbicacion() != null) {
                cliente.setUbicacion(clienteBase.get().getUbicacion());
            }
            if (clienteBase.get().getGenero() != null) {
                cliente.setGenero(clienteBase.get().getGenero());
            }
            if (clienteBase.get().getEstadoCivil() != null) {
                cliente.setEstadoCivil(clienteBase.get().getEstadoCivil());
            }
            if (clienteBase.get().getTelefono() != null && !clienteBase.get().getTelefono().equals(Constantes.vacio)) {
                List<Telefono> telefonos = new ArrayList<>();
                telefonos.add(new Telefono(Constantes.vacio, clienteBase.get().getTelefono(), new Cliente()));
                cliente.setTelefonos(telefonos);
            }
            if (clienteBase.get().getCelular() != null && !clienteBase.get().getCelular().equals(Constantes.vacio)) {
                List<Celular> celulares = new ArrayList<>();
                celulares.add(new Celular(Constantes.vacio, clienteBase.get().getCelular(), new Cliente()));
                cliente.setCelulares(celulares);
            }
            if (clienteBase.get().getCorreo() != null && !clienteBase.get().getCorreo().equals(Constantes.vacio)) {
                List<Correo> correos = new ArrayList<>();
                correos.add(new Correo(Constantes.vacio, clienteBase.get().getCorreo(), new Cliente()));
                cliente.setCorreos(correos);
            }

        }
        return cliente;
    }

    /**
     * Metodo que permite buscar el contribuyente del cliente
     * @param cliente
     * @return el objeto
     */
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

    /**
     * Metodo que permite la creacion del objeto
     * @param cliente
     * @return el objeto creado
     */
    @Override
    public Cliente crear(Cliente cliente) {
    	validar(cliente);
    	Optional<Cliente>buscarCliente=rep.obtenerPorEmpresaYIdentificacion(cliente.getEmpresa().getId(), cliente.getIdentificacion(), Constantes.estadoActivo);
    	if(buscarCliente.isPresent()) {
    		throw new EntidadExistenteException(Constantes.cliente);
    	}
    	Optional<String>codigo=Util.generarCodigoPorEmpresa(null, Constantes.tabla_cliente, cliente.getEmpresa().getId());
    	if (codigo.isEmpty()) {
    		throw new CodigoNoExistenteException();
    	}
    	Optional<Ubicacion> ubicacion = repUbicacion.findByProvinciaAndCantonAndParroquia(cliente.getUbicacion().getProvincia(),cliente.getUbicacion().getCanton(), cliente.getUbicacion().getParroquia(), Constantes.estadoActivo);
    	if(ubicacion.isEmpty()) {
    		throw new EntidadNoExistenteException(Constantes.ubicacion);
    	}
        if(cliente.getCorreos().isEmpty()){
            List<Correo> correos = new ArrayList<>();
            correos.add(new Correo("", Constantes.correo_predeterminado, cliente));
            cliente.setCorreos(correos);
        }
    	for(Dependiente dependiente: cliente.getDependientes()) {
    		Optional<Ubicacion> ubicacionDependiente= repUbicacion.findByProvinciaAndCantonAndParroquia(dependiente.getUbicacion().getProvincia(),dependiente.getUbicacion().getCanton(), dependiente.getUbicacion().getParroquia(), Constantes.estadoActivo);
        	if(ubicacionDependiente.isEmpty()) {
        		throw new EntidadNoExistenteException(Constantes.dependiente);
        	}
        	dependiente.setUbicacion(ubicacionDependiente.get());
    	}
    	cliente.setUbicacion(ubicacion.get());
    	cliente.setCodigo(codigo.get());
    	cliente.setEstado(Constantes.estadoActivo);
        Cliente res = rep.save(cliente);
        res.normalizar();
        return res;
    }

    /**
     * Metodo que permite la actualizacion del objeto
     * @param cliente
     * @return el objeto actualizado
     */
    @Override
    public Cliente actualizar(Cliente cliente) {
    	validar(cliente);
        Optional<Ubicacion> ubicacion = repUbicacion.findByProvinciaAndCantonAndParroquia(cliente.getUbicacion().getProvincia(),cliente.getUbicacion().getCanton(), cliente.getUbicacion().getParroquia(), Constantes.estadoActivo);
    	if(ubicacion.isEmpty()) {
    		throw new EntidadNoExistenteException(Constantes.ubicacion);
    	}
        if(cliente.getCorreos().isEmpty()){
            List<Correo> correos = new ArrayList<>();
            correos.add(new Correo("", Constantes.correo_predeterminado, cliente));
            cliente.setCorreos(correos);
        }
        for(Dependiente dependiente: cliente.getDependientes()) {
            Optional<Ubicacion> ubicacionDependiente= repUbicacion.findByProvinciaAndCantonAndParroquia(dependiente.getUbicacion().getProvincia(),dependiente.getUbicacion().getCanton(), dependiente.getUbicacion().getParroquia(), Constantes.estadoActivo);
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

    /**
     * Metodo que permite poner la propiedad estado en activo
     * @param cliente
     * @return el objeto con la propiedad estado en activo
     */
    @Override
    public Cliente activar(Cliente cliente) {
        validar(cliente);
        cliente.setEstado(Constantes.estadoActivo);
        Cliente res = rep.save(cliente);
        res.normalizar();
        return res;
    }

    /**
     * Metodo que permite poner la propiedad estado en inactivo
     * @param cliente
     * @return el objeto con la propiedad estado en inactivo
     */
    @Override
    public Cliente inactivar(Cliente cliente) {
        validar(cliente);
        cliente.setEstado(Constantes.estadoInactivo);
        Cliente res = rep.save(cliente);
        res.normalizar();
        return res;
    }

    /**
     * Metodo que permite obtener un objeto a traves del id
     * @param id
     * @return el objeto
     */
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

    /**
     * Metodo que permite consultar todos los objetos
     * @return lista de todos los objetos
     */
    @Override
    public List<Cliente> consultar() {
        return rep.consultar();
    }

    /**
     * Metodo que permite consultar los objetos filtrando por la empresa
     * @param empresaId
     * @return lista de los objetos consultados filtrando por la empresa
     */
    @Override
    public List<Cliente> consultarPorEmpresa(long empresaId){
        return rep.consultarPorEmpresa(empresaId);
    }

    /**
     * Metodo que permite consultar los objetos filtrando por el estado
     * @param estado
     * @return lista de los objetos consultados filtrando por el estado
     */
    @Override
    public List<Cliente> consultarPorEstado(String estado){
    	return rep.consultarPorEstado(estado);
    }

    /**
     * Metodo que permite consultar los objetos filtrando por la empresa y el estado
     * @param empresaId
     * @param estado
     * @return
     */
    @Override
    public List<Cliente> consultarPorEmpresaYEstado(long empresaId, String estado){
        return rep.consultarPorEmpresaYEstado(empresaId, estado);
    }

    /**
     * Metodo que permite consultar los objetos por pagina
     * @param pageable
     * @return lista de tipo Page con los objetos
     */
    @Override
    public Page<Cliente> consultarPagina(Pageable pageable){
    	return rep.findAll(pageable);
    }
}
