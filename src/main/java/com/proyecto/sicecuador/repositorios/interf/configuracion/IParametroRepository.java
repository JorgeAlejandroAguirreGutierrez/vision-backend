package com.proyecto.sicecuador.repositorios.interf.configuracion;

import com.proyecto.sicecuador.modelos.cliente.TipoContribuyente;
import com.proyecto.sicecuador.modelos.configuracion.Parametro;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IParametroRepository extends JpaRepository<Parametro, Long>, JpaSpecificationExecutor<Parametro> {
    @Query(value = "SELECT count(*) FROM auxiliar", nativeQuery = true)
    long findConteoAuxiliar();
    @Query(value = "SELECT count(*) FROM categoria_cliente", nativeQuery = true)
    long findConteoCategoriaCliente();
    @Query(value = "SELECT count(*) FROM celular", nativeQuery = true)
    long findConteoCelular();
    @Query(value = "SELECT count(*) FROM cliente", nativeQuery = true)
    long findConteoCliente();
    @Query(value = "SELECT count(*) FROM correo", nativeQuery = true)
    long findConteoCorreo();
    @Query(value = "SELECT count(*) FROM direccion", nativeQuery = true)
    long findConteoDireccion();
    @Query(value = "SELECT count(*) FROM estado_civil", nativeQuery = true)
    long findConteoEstadoCivil();
    @Query(value = "SELECT count(*) FROM financiamiento", nativeQuery = true)
    long findConteoFinanciamiento();
    @Query(value = "SELECT count(*) FROM forma_pago", nativeQuery = true)
    long findConteoFormaPago();
    @Query(value = "SELECT count(*) FROM genero", nativeQuery = true)
    long findConteoGenero();
    @Query(value = "SELECT count(*) FROM grupo_cliente", nativeQuery = true)
    long findConteoGrupoCliente();
    @Query(value = "SELECT count(*) FROM origen_ingreso", nativeQuery = true)
    long findConteoOrigenIngreso();
    @Query(value = "SELECT count(*) FROM plazo_credito", nativeQuery = true)
    long findConteoPlazoCredito();
    @Query(value = "SELECT count(*) FROM retencion_cliente", nativeQuery = true)
    long findConteoRetencionCliente();
    @Query(value = "SELECT count(*) FROM telefono", nativeQuery = true)
    long findConteoTelefono();
    @Query(value = "SELECT count(*) FROM tipo_contribuyente", nativeQuery = true)
    long findConteoTipoContribuyente();
    @Query(value = "SELECT count(*) FROM tipo_pago", nativeQuery = true)
    long findConteoTipoPago();
    @Query(value = "SELECT count(*) FROM telefono_auxiliar", nativeQuery = true)
    long findConteoTelefonoAuxiliar();
    @Query(value = "SELECT count(*) FROM celular_auxiliar", nativeQuery = true)
    long findConteoCelularAuxiliar();
    @Query(value = "SELECT count(*) FROM correo_auxiliar", nativeQuery = true)
    long findConteoCorreoAuxiliar();

    @Query(value = "SELECT count(*) FROM egreso", nativeQuery = true)
    long findConteoEgreso();
    @Query(value = "SELECT count(*) FROM factura", nativeQuery = true)
    long findConteoFactura();
    @Query(value = "SELECT count(*) FROM factura_detalle", nativeQuery = true)
    long findConteoFacturaDetalle();
    @Query(value = "SELECT count(*) FROM factura_caracteristica", nativeQuery = true)
    long findConteoFacturaCaracteristica();
    @Query(value = "SELECT count(*) FROM pedido", nativeQuery = true)
    long findConteoPedido();
    @Query(value = "SELECT count(*) FROM proforma", nativeQuery = true)
    long findConteoProforma();
    @Query(value = "SELECT count(*) FROM tipo_comprobante", nativeQuery = true)
    long findConteoTipoComprobante();

    @Query(value = "SELECT count(*) FROM empresa", nativeQuery = true)
    long findConteoEmpresa();
    @Query(value = "SELECT count(*) FROM parametro", nativeQuery = true)
    long findConteoParametro();
    @Query(value = "SELECT count(*) FROM tipo_retencion", nativeQuery = true)
    long findConteoTipoRetencion();
    @Query(value = "SELECT count(*) FROM ubicacion", nativeQuery = true)
    long findConteoUbicacion();

    @Query(value = "SELECT count(*) FROM guia_remision", nativeQuery = true)
    long findConteoGuiaRemision();
    @Query(value = "SELECT count(*) FROM transportista", nativeQuery = true)
    long findConteoTransportista();
    @Query(value = "SELECT count(*) FROM vehiculo_transporte", nativeQuery = true)
    long findConteoVehiculoTransporte();

    @Query(value = "SELECT count(*) FROM bodega", nativeQuery = true)
    long findConteoBodega();
    @Query(value = "SELECT count(*) FROM bodega_producto", nativeQuery = true)
    long findConteoBodegaProducto();
    @Query(value = "SELECT count(*) FROM caracteristica", nativeQuery = true)
    long findConteoCaracteristica();
    @Query(value = "SELECT count(*) FROM grupo_producto", nativeQuery = true)
    long findConteoGrupoProducto();
    @Query(value = "SELECT count(*) FROM impuesto", nativeQuery = true)
    long findConteoImpuesto();
    @Query(value = "SELECT count(*) FROM kardex", nativeQuery = true)
    long findConteoKardex();
    @Query(value = "SELECT count(*) FROM medida", nativeQuery = true)
    long findConteoMedida();
    @Query(value = "SELECT count(*) FROM precio", nativeQuery = true)
    long findConteoPrecio();
    @Query(value = "SELECT count(*) FROM producto", nativeQuery = true)
    long findConteoProducto();
    @Query(value = "SELECT count(*) FROM proveedor", nativeQuery = true)
    long findConteoProveedor();
    @Query(value = "SELECT count(*) FROM tipo_producto", nativeQuery = true)
    long findConteoTipoProducto();

    @Query(value = "SELECT count(*) FROM banco", nativeQuery = true)
    long findConteoBanco();
    @Query(value = "SELECT count(*) FROM cheque", nativeQuery = true)
    long findConteoCheque();
    @Query(value = "SELECT count(*) FROM compensacion", nativeQuery = true)
    long findConteoCompensacion();
    @Query(value = "SELECT count(*) FROM credito_amortizacion", nativeQuery = true)
    long findConteoCreditoAmortizacion();
    @Query(value = "SELECT count(*) FROM credito", nativeQuery = true)
    long findConteoCredito();
    @Query(value = "SELECT count(*) FROM deposito", nativeQuery = true)
    long findConteoDeposito();
    @Query(value = "SELECT count(*) FROM transferencia", nativeQuery = true)
    long findConteoTransferencia();
    @Query(value = "SELECT count(*) FROM efectivo", nativeQuery = true)
    long findConteoEfectivo();
    @Query(value = "SELECT count(*) FROM operador_tarjeta", nativeQuery = true)
    long findConteoOperadorTarjeta();
    @Query(value = "SELECT count(*) FROM recaudacion", nativeQuery = true)
    long findConteoRecaudacion();
    @Query(value = "SELECT count(*) FROM retencion_venta", nativeQuery = true)
    long findConteoRetencionVenta();
    @Query(value = "SELECT count(*) FROM retencion_venta_detalle", nativeQuery = true)
    long findConteoRetencionVentaDetalle();
    @Query(value = "SELECT count(*) FROM tarjeta_credito", nativeQuery = true)
    long findConteoTarjetaCredito();
    @Query(value = "SELECT count(*) FROM tarjeta_debito", nativeQuery = true)
    long findConteoTarjetaDebito();
    @Query(value = "SELECT count(*) FROM tarjeta", nativeQuery = true)
    long findConteoTarjeta();

    @Query(value = "SELECT count(*) FROM establecimiento", nativeQuery = true)
    long findConteoEstablecimiento();
    @Query(value = "SELECT count(*) FROM perfil", nativeQuery = true)
    long findConteoPerfil();
    @Query(value = "SELECT count(*) FROM permiso", nativeQuery = true)
    long findConteoPermiso();
    @Query(value = "SELECT count(*) FROM punto_venta", nativeQuery = true)
    long findConteoPuntoVenta();
    @Query(value = "SELECT count(*) FROM sesion", nativeQuery = true)
    long findConteoSesion();
    @Query(value = "SELECT count(*) FROM usuario", nativeQuery = true)
    long findConteoUsuario();

    @Query(value = "SELECT * FROM parametro u WHERE u.tipo = :tipo", nativeQuery = true)
    Optional<Parametro> findByTipo(@Param("tipo") String tipo);
    @Query(value = "SELECT * FROM parametro u WHERE u.tabla = :tabla and u.tipo = :tipo", nativeQuery = true)
    Optional<Parametro> findByTablaAndTipo(
            @Param("tabla") String tabla,
            @Param("tipo") String tipo);
}
