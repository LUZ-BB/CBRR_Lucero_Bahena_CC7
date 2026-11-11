package mx.edu.utez.CBRR_Lucero_Bahena_CC7.controller.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrearPedidoDTO {



    private Long id;
    @NotBlank(message = "el nombre es obligatorio")
    private String nombreCliente;

    @Min(value = 1, message = " la cantidad debe ser 1 o mayor a 1")
    private Integer cantidad;

    @Positive(message = "el precio no puede ser negativo")
    @NotBlank(message = "el precio no puede estar vacio")
    private Double precioUnitario;

    private Double total;




}
