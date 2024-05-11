package org.proyectsgcs.springcloud.msvc.medicamento.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categorias_medicamento")
public class CategoriaMedicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombreCategoria; //analgésicos, antibióticos, vitaminas

}
