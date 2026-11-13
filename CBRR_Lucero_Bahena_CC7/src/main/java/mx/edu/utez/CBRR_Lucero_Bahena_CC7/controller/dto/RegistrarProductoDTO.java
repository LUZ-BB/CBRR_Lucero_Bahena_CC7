package mx.edu.utez.CBRR_Lucero_Bahena_CC7.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.processing.Generated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarProductoDTO {

    private Long id;

    @NotBlank(message="el nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "la categoria es obligatoria")
    private String categoria;
    @Positive(message = "el precio no puede ser negativo")
    @Min(value=1, message="el precio no puede ser negativo")
    private Double precio;
    @NotNull(message= "el stock es obligatorip")
    @Min(value=0, message="el stock no puede ser negativo")
    private Integer stock;


}
