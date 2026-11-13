package mx.edu.utez.CBRR_Lucero_Bahena_CC7.repository;

import mx.edu.utez.CBRR_Lucero_Bahena_CC7.model.EstadoPedido;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEstadoPedido(EstadoPedido estadoPedido);
    List<Pedido> findByProductoIdAndEstadoPedido(Long productoId, EstadoPedido estadoPedido);
    boolean existsByProductoIdAndEstadoPedido(Long productoId, EstadoPedido estadoPedido);
}
