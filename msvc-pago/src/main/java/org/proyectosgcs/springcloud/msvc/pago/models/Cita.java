package org.proyectosgcs.springcloud.msvc.pago.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @file: Citas
 * @author: EdwarMoya
 * @created: 26/04/2024
 * @HoraCreated: 04:10 p. m.
 */
@Data
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaHora;
    private Long pacienteId;
    private Long medicoId;
    private String motivo;
    private String estado;
    private Long pagoId;
}
