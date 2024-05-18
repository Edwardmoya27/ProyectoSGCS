package org.proyectosgcs.springcloud.msvc.atencionmedica.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @file: Pago
 * @author: EdwarMoya
 * @created: 08/05/2024
 * @HoraCreated: 06:11 p. m.
 */
@Data
public class Pago {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Long pacienteId;
        private Long citaId;
        private Double monto;
        private LocalDateTime fechaHora;
        private String metodoPago;
}

