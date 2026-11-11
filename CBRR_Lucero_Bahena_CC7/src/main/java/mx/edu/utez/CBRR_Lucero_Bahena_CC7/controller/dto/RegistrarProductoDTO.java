package mx.edu.utez.CBRR_Lucero_Bahena_CC7.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class RegistrarProductoDTO {

    private Long id;

    @NotBlank(message="el nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "la categoria es obligatoria")
    private String categoria;
    @Positive(message = "el precio no puede ser negativo")
    private Double precio;
    private Integer stock;


}
