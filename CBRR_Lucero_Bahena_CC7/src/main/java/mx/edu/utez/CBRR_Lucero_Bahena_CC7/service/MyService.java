package mx.edu.utez.CBRR_Lucero_Bahena_CC7.service;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.controller.dto.CrearPedidoDTO;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.controller.dto.ProductoDTO;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.controller.dto.RegistrarProductoDTO;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.exception.CustomException;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.model.EstadoPedido;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.model.Pedido;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.model.Producto;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.repository.PedidoRepository;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyService {


    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;

    public MyService(ProductoRepository productoRepository, PedidoRepository pedidoRepository) {
        this.productoRepository = productoRepository;
        this.pedidoRepository = pedidoRepository;
    }



    @Transactional
    public Producto registrarProducto(RegistrarProductoDTO payload) {
        Producto producto = new Producto();
        producto.setCategoria(payload.getCategoria());
        producto.setNombre(payload.getNombre());
        producto.setPrecio(payload.getPrecio());
        producto.setStock(payload.getStock());
        producto.setActivo(true);
        return productoRepository.save(producto);


    }

    @Transactional
    public Pedido crearPedido(CrearPedidoDTO payload){

        Producto producto = productoRepository.findById(payload.getId())
                .orElseThrow(() -> new CustomException("el producto no existe"));

        if (!producto.isActivo()) {
            throw new CustomException("el producto no esta activo");
        }

        if (producto.getStock() < payload.getCantidad()) {
            throw new CustomException("stock insuficiente para realizar el pedido");
        }

        Double precioUnitario = producto.getPrecio();
        Double total = precioUnitario * payload.getCantidad();

        producto.setStock(producto.getStock() - payload.getCantidad());
        productoRepository.save(producto);

        Pedido pedido= new Pedido();
        pedido.setId(pedido.getId());
        pedido.setCantidad(payload.getCantidad());
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setPrecioUnitario(precioUnitario);
        pedido.setTotal(total);
        pedido.setProducto(producto);
        return pedidoRepository.save(pedido);

    }


    @Transactional
    public Pedido cancelarPedido(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(()-> new CustomException("el pedidido no existe"));

        if (pedido.getEstadoPedido()  != EstadoPedido.PENDIENTE) {
            throw new CustomException("solo se pueden cancelar pedidos que no esten pendientes");
        }
        Producto producto = pedido.getProducto();
        producto.setStock(producto.getStock() + pedido.getCantidad());
        productoRepository.save(producto);

        pedido.setEstadoPedido(EstadoPedido.CANCELADO);
        return pedidoRepository.save(pedido);

    }


    public Pedido procesarPedido(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                    .orElseThrow(() -> new CustomException("el pedido no existe"));

        if (pedido.getEstadoPedido() != EstadoPedido.PENDIENTE) {
            throw new CustomException("solo se pueden procesar pedidos en estado pendiente");
        }

        pedido.setEstadoPedido(EstadoPedido.PROCESADO);
        return pedidoRepository.save(pedido);
    }

    public Producto desactivarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new CustomException("el producto no existe"));


        if (pedidoRepository.existsByProductoIdAndEstadoPedido(id, EstadoPedido.PENDIENTE)) {
            throw new CustomException("no se puede desactivar un producto con pedidos pendientes");
        }

        producto.setActivo(false);
        return productoRepository.save(producto);
    }


    @Transactional
    public List<Pedido> cancelarPedidosPendientes(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new CustomException("el pedido no exite"));

        List<Pedido> pendientes = pedidoRepository.findByProductoIdAndEstadoPedido(id, EstadoPedido.PENDIENTE);

        for (Pedido pedido : pendientes) {
            pedido.setEstadoPedido(EstadoPedido.CANCELADO);
            producto.setStock(producto.getStock() + pedido.getCantidad());
            pedidoRepository.save(pedido);

        }
        productoRepository.save(producto);
        return pendientes;
    }



    public Pedido consultarDetallePedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new CustomException("el pedido no existe"));
    }

    public List<Producto> consultarProductosPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public List<Pedido> consultarPedidosPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstadoPedido(estado);
    }

    public List<Producto> listarProductosActivos() {
        return productoRepository.findByActivoTrue();
    }

    public List<ProductoDTO> listarTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll();
        List<ProductoDTO> lista = new ArrayList<>();

        for (Producto producto : productos) {
            lista.add(new ProductoDTO(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getCategoria(),
                    producto.getPrecio(),
                    producto.getStock(),
                    producto.isActivo()

            ));
        }
        return lista;
    }




}
