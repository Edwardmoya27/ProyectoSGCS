package org.proyectosgcs.springcloud.msvc.pago.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @file: Comprobante
 * @author: EdwarMoya
 * @created: 05/05/2024
 * @HoraCreated: 05:49 p. m.
 */
@Data
@Entity
public class Comprobante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pago_id", referencedColumnName = "id")
    @NotNull(message = "El id del pago no puede ser nulo")
    private Pago pago;

    private LocalDateTime fechaHora;

    private String detalles;

}
