package org.proyectosgcs.springcloud.msvc.pago.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @file: Pago
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 05:49 p. m.
 */
@Data
@Entity
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El id del paciente no puede ser nulo")
    private Long pacienteId;

    @NotNull(message = "El id de la cita no puede ser nulo")
    private Long citaId;

    @NotNull(message = "El monto no puede ser nulo")
    @Positive(message = "El monto debe ser positivo")
    private Double monto;

    @NotNull(message = "La fecha y hora del pago no puede ser nula")
    private LocalDateTime fechaHora;

    private String metodoPago;

}
