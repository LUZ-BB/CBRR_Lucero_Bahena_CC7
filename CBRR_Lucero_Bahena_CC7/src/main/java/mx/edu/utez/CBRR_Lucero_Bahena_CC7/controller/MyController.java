package mx.edu.utez.CBRR_Lucero_Bahena_CC7.controller;

import jakarta.validation.Valid;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.controller.dto.RegistrarProductoDTO;
import mx.edu.utez.CBRR_Lucero_Bahena_CC7.model.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/controlador")
@CrossOrigin("*")
public class MyController {


    @PostMapping
    public ResponseEntity<Pedido> metodo(@RequestBody @Valid RegistrarProductoDTO payload) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(servicio.metodo(payload));

    }

}
