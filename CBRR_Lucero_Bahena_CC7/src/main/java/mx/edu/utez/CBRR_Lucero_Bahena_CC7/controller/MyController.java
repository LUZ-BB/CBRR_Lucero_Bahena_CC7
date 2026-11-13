package mx.edu.utez.CBRR_Lucero_Bahena_CC7.controller;

import jakarta.validation.Valid;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.controller.dto.ProductoDTO;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.controller.dto.RegistrarProductoDTO;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.model.Pedido;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.model.Producto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.service.MyService;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.controller.dto.CrearPedidoDTO;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.model.EstadoPedido;

import java.util.List;

@RestController
@RequestMapping("/controlador")
@CrossOrigin("*")
public class MyController {

    private final MyService service;

    public MyController(MyService service) {
        this.service = service;
    }

    @PostMapping("/producto")
    public ResponseEntity<Producto> registrarProducto(@RequestBody @Valid RegistrarProductoDTO payload) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.registrarProducto(payload));
    }

    @PostMapping("/pedido")
    public ResponseEntity<Pedido> crearPedido(@RequestBody @Valid CrearPedidoDTO payload) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.crearPedido(payload));
    }

    @PatchMapping("/pedido/{id}/cancelar")
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.cancelarPedido(id));
    }

    @PatchMapping("/pedido/{id}/procesar")
    public ResponseEntity<Pedido> procesarPedido(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.procesarPedido(id));
    }

    @PatchMapping("/producto/{id}/desactivar")
    public ResponseEntity<Producto> desactivarProducto(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.desactivarProducto(id));
    }

    @PatchMapping("/producto/{idProducto}/cancelar-pendientes")
    public ResponseEntity<List<Pedido>> cancelarPedidosPendientes(@PathVariable Long idProducto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.cancelarPedidosPendientes(idProducto));
    }

    @GetMapping("/pedidos/estado/{estado}")
    public ResponseEntity<List<Pedido>> consultarPedidosPorEstado(@PathVariable EstadoPedido estado) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.consultarPedidosPorEstado(estado));
    }

    @GetMapping("/pedido/{id}")
    public ResponseEntity<Pedido> consultarDetallePedido(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.consultarDetallePedido(id));
    }

    @GetMapping("/productos/categoria/{categoria}")
    public ResponseEntity<List<Producto>> consultarProductosPorCategoria(@PathVariable String categoria) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.consultarProductosPorCategoria(categoria));
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> listarTodosLosProductos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.listarTodosLosProductos());
    }

    @GetMapping("/productos/activos")
    public ResponseEntity<List<Producto>> listarProductosActivos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.listarProductosActivos());
    }

}


