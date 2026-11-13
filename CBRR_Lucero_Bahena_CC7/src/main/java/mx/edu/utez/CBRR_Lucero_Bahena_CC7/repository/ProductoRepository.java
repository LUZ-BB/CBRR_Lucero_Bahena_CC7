package mx.edu.utez.CBRR_Lucero_Bahena_CC7.repository;

import mx.edu.utez.CBRR_Lucero_Bahena_CC7.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByActivoTrue();
}
