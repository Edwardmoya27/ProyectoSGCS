package org.proyectosgcs.springcloud.msvc.pago.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private Long idPago;
    private LocalDateTime fechaEmision;
    private BigDecimal monto;

}
