package org.proyectsgcs.springcloud.msvc.medicamento.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table(name = "medicamentos")
public class Medicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre del medicamento no puede estar vacío")
    private String nombre;

    private String descripcion;

    private String presentacion;

    @NotEmpty(message = "El fabricante del medicamento no puede estar vacío")
    private String fabricante;

    @NotNull(message = "La fecha de vencimiento del medicamento es obligatoria")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaVencimiento;

    @Positive(message = "El precio del medicamento debe ser un número positivo")
    private double precio;

    @NotNull(message = "La categoría del medicamento es obligatoria")
    @ManyToOne
    private CategoriaMedicamento categoriaMedicamento;

    @PositiveOrZero(message = "El stock disponible del medicamento debe ser un número positivo o cero")
    private int stockDisponible;
}
